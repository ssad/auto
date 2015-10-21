package by.auto.web.controllers.utils;

/**
 * Enumeration of Message types.
 */
public enum MessageType {

    /**
     * The messages is informative in nature, like a note or notice.
     */
    INFO,

    /**
     * The messages indicates that an action initiated by the user was performed successfully.
     */
    SUCCESS,

    /**
     * The messages warns the user something is not quite right.
     * Corrective action is generally recommended but not required.
     */
    WARNING,

    /**
     * The messages reports an error condition that needs to be addressed by the user.
     */
    ERROR;

    private final String cssClass;

    private MessageType() {
        cssClass = name().toLowerCase();
    }

    /**
     * The css class for styling messages of this type.
     */
    public String getCssClass() {
        return cssClass;
    }
}