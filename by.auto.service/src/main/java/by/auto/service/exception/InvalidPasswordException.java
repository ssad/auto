package by.auto.service.exception;

public class InvalidPasswordException extends ServiceLayerException {
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
