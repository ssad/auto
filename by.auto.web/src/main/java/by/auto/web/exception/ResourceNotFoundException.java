package by.auto.web.exception;

public class ResourceNotFoundException extends RuntimeException {
    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
