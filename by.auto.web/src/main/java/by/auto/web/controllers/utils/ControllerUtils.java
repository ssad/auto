package by.auto.web.controllers.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

public class ControllerUtils {
    public static final String MESSAGE_ATTR_NAME = "messages";

    public static String redirectTo(final String path) {
        return "redirect:" + path;
    }

    public static void addSuccessMessage(final WebRequest request, final String message) {
        request.setAttribute(MESSAGE_ATTR_NAME, Message.success(message), RequestAttributes.SCOPE_REQUEST);
    }

    public static void addErrorMessage(final WebRequest request, final String message) {
        request.setAttribute(MESSAGE_ATTR_NAME, Message.error(message), RequestAttributes.SCOPE_REQUEST);
    }

    public static void addInfoMessage(final WebRequest request, final String message) {
        request.setAttribute(MESSAGE_ATTR_NAME, Message.info(message), RequestAttributes.SCOPE_REQUEST);
    }

    public static void addWarningMessage(final WebRequest request, final String message) {
        request.setAttribute(MESSAGE_ATTR_NAME, Message.warning(message), RequestAttributes.SCOPE_REQUEST);
    }
}
