package by.auto.web.controllers.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Message {
    @JsonProperty
    private final MessageType type;

    @JsonProperty("message")
    private final String text;

    @JsonProperty
    private String title;

    /**
     * Creates a new Message of a certain type consisting of the text provided.
     */
    public Message(final MessageType type, final String text) {
        this.type = type;
        this.text = text;
    }

    public Message(final MessageType type, final String text, final String title) {
        this.type = type;
        this.text = text;
        this.title = title;
    }

    /**
     * Factory method for a success messages.
     */
    public static Message success(final String text) {
        return new Message(MessageType.SUCCESS, text);
    }

    public static Message success(final String text, final String title) {
        return new Message(MessageType.SUCCESS, text, title);
    }

    /**
     * Factory method for an info messages.
     */
    public static Message info(final String text) {
        return new Message(MessageType.INFO, text);
    }

    public static Message info(final String text, final String title) {
        return new Message(MessageType.INFO, text, title);
    }

    /**
     * Factory method for a warning messages.
     */
    public static Message warning(final String text) {
        return new Message(MessageType.WARNING, text);
    }

    public static Message warning(final String text, final String title) {
        return new Message(MessageType.WARNING, text, title);
    }

    /**
     * Factory method for an error messages.
     */
    public static Message error(final String text) {
        return new Message(MessageType.ERROR, text);
    }

    public static Message error(final String text, final String title) {
        return new Message(MessageType.ERROR, text, title);
    }

    /**
     * The type of messages; such as info, warning, error, or success.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * The info text.
     */
    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return type + ": " + text;
    }

}
