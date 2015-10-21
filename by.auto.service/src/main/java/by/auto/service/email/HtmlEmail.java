package by.auto.service.email;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@HandleEmail(className = HtmlEmailHandler.class)
public class HtmlEmail extends TemplateEmail {

    private String transformedFileName;

    private boolean sendHtmlAsAttachment = false;

    private String textFileTemplate;

    private final Map<String, Object> textFileContext = new HashMap();

    public HtmlEmail() {
        super();
    }

    public boolean isSendHtmlAsAttachment() {
        return sendHtmlAsAttachment;
    }

    public void setSendHtmlAsAttachment(final boolean sendHtmlAsAttachment) {
        this.sendHtmlAsAttachment = sendHtmlAsAttachment;
    }

    public String getTextFileTemplate() {
        return textFileTemplate;
    }

    public void setTextFileTemplate(final String texFileName) {
        this.textFileTemplate = texFileName;
    }

    public String getTransformedFileName() {
        return transformedFileName;
    }

    public void setTransformedFileName(final String transformedFileName) {
        this.transformedFileName = transformedFileName;
    }

    public Map getTextFileContext() {
        return textFileContext;
    }

    public void clearTextFileContext() {
        textFileContext.clear();
    }

    public void addToTextFileContext(final String key, final Object value) {
        checkArgument(key.equals("messages")
                || key.equals("locale")
                || key.equals("arguments"), "Don't use reserved words for localization:messages, locale, arguments");
        textFileContext.put(key, value);
    }

}
