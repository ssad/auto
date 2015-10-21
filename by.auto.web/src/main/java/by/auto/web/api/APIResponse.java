package by.auto.web.api;

import by.auto.web.controllers.utils.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    public Message getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    @JsonProperty
    @JsonUnwrapped
    private final Message message;

    @JsonProperty
    private T data;

    private APIResponse(final Message message, final T data) {
        this.message = message;
        this.data = data;
    }

    private APIResponse(final Message message) {
        this.message = message;
    }

    public static <T> APIResponse<T> error(final String message) {
        return new APIResponse<>(Message.error(message));
    }

    public static <T> APIResponse<T> error(final T data) {
        return new APIResponse<>(Message.error(""), data);
    }

    public static <T> APIResponse<T> error(final T data, final String message) {
        return new APIResponse<>(Message.error(message), data);
    }

    public static <T> APIResponse<T> success(final T data) {
        return new APIResponse<>(Message.success(""), data);
    }

    public static <T> APIResponse<T> success(final String message) {
        return new APIResponse<>(Message.success(message));
    }

    public static <T> APIResponse<T> success(final T data, final String message) {
        return new APIResponse<>(Message.success(message), data);
    }

}
