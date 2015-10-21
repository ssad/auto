package by.auto.service.exception;

public class SignInNotFoundException extends ServiceLayerException {
    private final String username;

    public SignInNotFoundException(final String username) {
        super("Username not found");

        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
