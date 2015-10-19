package by.auto.web.exception;


import by.auto.domain.exception.ApplicationException;

public class WebLayerException extends ApplicationException {
    public WebLayerException(final String message) {
        super(message);
    }
}
