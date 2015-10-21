package by.auto.service.email;

@HandleEmail(className = TextEmailHandler.class)
public class TextEmail extends Email {

    private String textMessage;
    private String subject;

    /**
     * Constructor for TextEmail.
     */
    public TextEmail() {
        super();
    }

    /**
     * Returns the textMessage.
     *
     * @return String
     */
    public String getTextMessage() {
        return textMessage;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * Sets the textMessage.
     *
     * @param textMessage The textMessage to set
     */
    public void setTextMessage(final String textMessage) {
        this.textMessage = textMessage;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }
}
