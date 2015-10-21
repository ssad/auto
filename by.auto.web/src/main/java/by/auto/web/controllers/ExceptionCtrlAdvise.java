package by.auto.web.controllers;

import by.auto.web.api.APIResponse;
import by.auto.web.exception.AccountIsNotActiveException;
import by.auto.web.exception.ResourceNotFoundException;
import by.auto.web.routes.Routes;
import by.auto.web.exception.HandledJsonException;
import by.auto.web.exception.HandledWebLayerException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ExceptionCtrlAdvise {

    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionCtrlAdvise.class);

    @Inject
    private MessageSource messageSource;

    /**
     * This method is automatically executed if MethodArgumentNotValidException
     * occurs in controllers methods with RequestMapping annotation
     * MethodArgumentNotValidException throws automatically if<code>@Valid</code>
     * annotation is used without BindingResult
     *
     * @param errors
     * @return APIResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(Routes.Api.API)
    @ResponseBody
    public APIResponse<?> handleValidExceptions(final MethodArgumentNotValidException errors) {
        final Map<String, String> errorMap = new HashMap<String, String>();
        for (final FieldError error : errors.getBindingResult().getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return APIResponse.error(errorMap);
    }

    @ExceptionHandler(HandledWebLayerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(Routes.Api.API)
    @ResponseBody
    public APIResponse<?> handleHandledWebLayerExceptions(final HandledWebLayerException error) {
        Map<String, Object> data = new HashMap<>();

        if (error.getData() != null) {
            return APIResponse.error(error.getData(), error.getMessage());
        }
        return APIResponse.error(data, error.getMessage());
    }


    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(Routes.Api.API)
    @ResponseBody
    public APIResponse<?> handleTypeMismatchExceptions(final TypeMismatchException exception, final Locale locale) {
        return APIResponse.error(messageSource.getMessage("api.type.mismatch.error.message", new Object[]{exception.getRequiredType().getSimpleName(), exception.getValue()}, locale));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(Routes.Api.API)
    @ResponseBody
    public APIResponse<?> handleJsonMappingExceptions(final HttpMessageNotReadableException error, final Locale locale) {
        final Map<String, String> errorMap = new HashMap<>();
        if (error.getCause() != null && error.getCause() instanceof JsonMappingException) {
            final StringBuilder path = new StringBuilder();
            final List<JsonMappingException.Reference> paths = ((JsonMappingException) error.getCause()).getPath();
            boolean first = true;
            for (final JsonMappingException.Reference field : paths) {
                if (!first) {
                    path.append(".");
                }
                first = false;
                path.append(field.getFieldName());

            }
            String message = "";
            final Throwable throwable = error.getMostSpecificCause();
            if (throwable instanceof HandledJsonException) {
                final HandledJsonException exception = ((HandledJsonException) throwable);
                try {
                    message = messageSource.getMessage(exception.getMessage(), exception.getArguments(), locale);
                } catch (final NoSuchMessageException e) {
                    e.printStackTrace();
                }
            }
            if (throwable instanceof InvalidFormatException) {
                try {
                    message = messageSource.getMessage("invalid.format.exception", null, locale);
                } catch (final NoSuchMessageException e) {
                    e.printStackTrace();
                }
            }
            errorMap.put(path.toString(), message);

        }
        return APIResponse.error(errorMap);
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountIsNotActiveException.class)
    public String handleAccountIsNotActiveException(AccountIsNotActiveException ex) {
        return "error-404";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return "error-404";
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler()
    public String errorInterceptor(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return "error-500";
    }
}
