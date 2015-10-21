package by.auto.persistence.audit;

import by.auto.domain.exception.ApplicationException;

public class NoAuditableException extends ApplicationException {
    public NoAuditableException(final String message) {
        super(message);
    }
}
