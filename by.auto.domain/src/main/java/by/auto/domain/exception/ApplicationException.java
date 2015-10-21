package by.auto.domain.exception;

public class ApplicationException extends RuntimeException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final Throwable ex) {
        super(ex);
    }

    public ApplicationException(final String message, final Throwable ex) {
        super(message, ex);
    }
}
