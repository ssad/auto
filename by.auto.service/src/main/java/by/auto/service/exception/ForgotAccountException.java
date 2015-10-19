package by.auto.service.exception;

public class ForgotAccountException extends ServiceLayerException {
    public ForgotAccountException(final String message) {
        super(message);
    }

    public ForgotAccountException(final String message, final Exception ex) {
        super(message, ex);
    }
}
