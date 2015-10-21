package by.auto.service.exception;

public class AccountAlreadyExistsException extends ServiceLayerException {
    public AccountAlreadyExistsException(final String message) {
        super(message);
    }

    public AccountAlreadyExistsException(final Exception ex) {
        super(ex);
    }
}
