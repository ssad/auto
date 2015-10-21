package by.auto.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Account is not active")
public class AccountIsNotActiveException extends RuntimeException {
}
