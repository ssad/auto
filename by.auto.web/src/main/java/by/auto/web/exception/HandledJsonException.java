package by.auto.web.exception;

import by.auto.domain.exception.ApplicationException;

public class HandledJsonException extends ApplicationException {

    private Object[] arguments;

    public HandledJsonException(final String messageCode) {
        super(messageCode);
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(final Object[] arguments) {
        this.arguments = arguments;
    }
}
