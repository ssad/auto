package by.auto.web.exception;

public class HandledWebLayerException extends WebLayerException {

    private Object data;

    public HandledWebLayerException(final String message) {
        super(message);
    }

    public HandledWebLayerException(final Object data, final String message) {
        super(message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
