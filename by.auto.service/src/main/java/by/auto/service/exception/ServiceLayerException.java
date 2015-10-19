package by.auto.service.exception;

import by.auto.domain.exception.ApplicationException;

public class ServiceLayerException extends ApplicationException {
    public ServiceLayerException(final String message) {
        super(message);
    }

    public ServiceLayerException(final Throwable ex) {
        super(ex);
    }

    public ServiceLayerException(final String message, final Throwable ex) {
        super(message, ex);
    }
}
