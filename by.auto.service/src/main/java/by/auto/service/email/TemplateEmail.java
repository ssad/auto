package by.auto.service.email;

import java.util.Locale;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

@HandleEmail(className = TemplateEmailHandler.class)
public class TemplateEmail extends Email {

    private String templateFileName;

    private Map<String, ?> context;

    private Locale locale = new Locale("ru");

    private String subjectKey;

    private Object[] subjectArgs;

    public TemplateEmail() {
        super();
    }

    public Map getContext() {
        return context;
    }

    public void setContext(final Map<String, ?> objectMap) {
        checkArgument(!objectMap.containsKey("messages")
                && !objectMap.containsKey("locale")
                && !objectMap.containsKey("arguments"), "Don't use reserved words for localization:messages, locale, arguments");
        this.context = objectMap;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(final String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public Object[] getSubjectArgs() {
        return subjectArgs;
    }

    public void setSubject(final String key, final Object[] args) {
        this.subjectKey = key;
        this.subjectArgs = args;
    }

    public void setSubject(final String key) {
        setSubject(key, null);
    }
}