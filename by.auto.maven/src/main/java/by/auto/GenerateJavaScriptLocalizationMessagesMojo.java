package by.auto;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mojo(name = "generate-javascript-localization-messages", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class GenerateJavaScriptLocalizationMessagesMojo extends AbstractMojo {

    private static final String BUNDLE_CHARSET = "ISO-8859-1";

    /**
     * Name of the javascript variable which will hold the localized messages.
     * If <code>initCode</code> is used, then this value should correspond to the <code>initCode</code>,
     * otherwise <code>"var"</code> keyword will be used to declare the variable to store the messages.
     * <p>Default: messages</p>
     */
    @Parameter(defaultValue = "messages")
    private String messagesName;

    /**
     * Custom initialization code which will be executed before assigning messages to <code>messagesName</code>.
     */
    @Parameter
    private String initCode;

    /**
     * Bundles to read messages from (directory where bundles are located).
     */
    @Parameter(required = true)
    private List<File> bundles;

    /**
     * Where to save generated JavaScript files.
     */
    @Parameter(required = true)
    private String output;

    /**
     * Whether minimize (actually do not put new line) or not (everything in one line).
     * <p>Default: true</p>
     */
    @Parameter(defaultValue = "true")
    private boolean minimize;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        @SuppressWarnings("unchecked") final
        Map<String, List<String>> messageSource =
                (Map<String, List<String>>) LazyMap.decorate(new HashMap<String, List<String>>(), new Factory() {
                    @Override
                    public Object create() {
                        return new ArrayList<String>();
                    }
                });

        for (final File bundle : bundles) {
            final File[] messages = bundle.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(final File dir, final String name) {
                    return name.endsWith("properties") && !name.startsWith("log4j");
                }
            });
            if (messages != null) {
                for (final File message : messages) {
                    if (message.isFile()) {
                        getLog().info("Processing " + message.getAbsolutePath());
                        try {
                            final String locale = resolveLocale(message.getName());
                            final List<String> lines = FileUtils.readLines(message, BUNDLE_CHARSET);
                            for (final String line : lines) {
                                if (isMessage(line)) {
                                    messageSource.get(locale).add(line);
                                }
                            }
                        } catch (final IOException e) {
                            throw new MojoExecutionException(String.format("Unable to read the content of the file %s using %s charset!", message.getAbsolutePath(), BUNDLE_CHARSET));
                        }
                    }
                }
            }
        }

        try {
            FileUtils.forceMkdir(new File(output));
            for (final String locale : messageSource.keySet()) {
                final List<String> messages = messageSource.get(locale);

                final PrintWriter generated = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output + File.separator + "messages_" + locale + ".js"), "UTF-8"));

                if (StringUtils.isNotEmpty(initCode)) {
                    generated.print(initCode + (minimize ? "" : "\n"));
                    generated.print(messagesName + "={" + (minimize ? "" : "\n"));
                } else {
                    generated.print("var " + messagesName + "={" + (minimize ? "" : "\n"));
                }

                for (int i = 0; i < messages.size(); i++) {
                    final String message = messages.get(i);
                    final String[] keyValue = message.split("=");
                    final String key = keyValue[0].trim();
                    final String value = StringEscapeUtils.unescapeJava(keyValue[1].trim());

                    generated.print(((minimize ? "" : "\t")) + quote(key) + ":" + quote(escapeQuote(value)) + (messages.size() != (i + 1) ? "," : "") + (minimize ? "" : "\n"));
                }

                generated.print("};" + (minimize ? "" : "\n"));

                IOUtils.closeQuietly(generated);
            }
        } catch (final IOException ex) {
            throw new MojoExecutionException("Unable to generate JavaScript localization messages!", ex);
        }
    }

    private String escapeQuote(final String value) {
        return StringUtils.replace(value, "\"", "\\\"");
    }

    private String quote(final String text) {
        return "\"" + text + "\"";
    }

    private boolean isMessage(final String line) {
        final String trimmed = line.trim();
        return !trimmed.startsWith("#") && !trimmed.isEmpty();
    }

    private String resolveLocale(final String messageName) {
        String locale = "en";
        if (messageName.contains("_")) {
            locale = StringUtils.substringBetween(messageName, "_", ".");
        }

        return locale;
    }

}


