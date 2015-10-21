package by.auto.domain.exception;

public class MongodbConvertException extends RuntimeException {
    public MongodbConvertException(final String message) {
        super(message);
    }

    public MongodbConvertException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
