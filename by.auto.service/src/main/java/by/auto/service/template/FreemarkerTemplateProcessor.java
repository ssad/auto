package by.auto.service.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Service
public class FreemarkerTemplateProcessor {

    @Inject
    private Configuration freemarkerConfig;

    @Inject
    private MessageSource templateMessageSource;

    @Value("${auto.web.app.url}")
    private String webAppUrl;

    @Value("${mail.logoPath}")
    private String pathLogo;

    @Inject @Named("servicesProperties") private Properties servicesProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(FreemarkerTemplateProcessor.class);

    public String getMessage(final String templateName, final Map<String, Object> context, final Locale locale) {
        try {
            final Template template = freemarkerConfig.getTemplate(templateName, locale);
            final Writer writer = new StringWriter();
            final HashMap contextToProcess;
            if (context == null) {
                contextToProcess = new HashMap<String, Object>();
            } else {
                contextToProcess = new HashMap(context);
            }
            final HashMap<String, Object> globalMap = new HashMap<>();
            globalMap.put("webAppUrl", webAppUrl);
            globalMap.put("imageLogo", webAppUrl + pathLogo);
            contextToProcess.put("global", globalMap);
            contextToProcess.put("servicesProperties", servicesProperties);

            if (!contextToProcess.containsKey("messages")) {
                contextToProcess.put("messages", this.templateMessageSource);
            }

            if (!contextToProcess.containsKey("locale")) {
                contextToProcess.put("locale", locale);
            }

            if (!contextToProcess.containsKey("arguments")) {
                contextToProcess.put("arguments", new Object[]{});
            }
            template.process(contextToProcess, writer);
            return writer.toString();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage());
            throw new FreemarkerException(e);
        }
    }
}
