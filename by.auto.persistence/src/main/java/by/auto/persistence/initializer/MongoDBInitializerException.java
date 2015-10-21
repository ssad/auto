package by.auto.persistence.initializer;

import by.auto.domain.exception.ApplicationException;

public class MongoDBInitializerException extends ApplicationException {
    public MongoDBInitializerException(final String message) {
        super(message);
    }

    public MongoDBInitializerException(final String message, final Exception ex) {
        super(message, ex);
    }
}
