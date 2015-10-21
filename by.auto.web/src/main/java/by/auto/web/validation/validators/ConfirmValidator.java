package by.auto.web.validation.validators;

import by.auto.web.validation.ValidationOrder;
import by.auto.web.validation.constraints.Confirm;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public final class ConfirmValidator implements ConstraintValidator<Confirm, Object> {
    private String field;

    private String matches;

    private String message;

    private boolean optional = false;

    @Override
    public void initialize(final Confirm constraintAnnotation) {
        field = constraintAnnotation.field();
        matches = constraintAnnotation.matches();
        if (matches == null || matches.length() == 0) {
            matches = "confirm" + StringUtils.capitalize(field);
        }
        if (Arrays.asList(constraintAnnotation.groups()).contains(ValidationOrder.Optional.class)) {
            optional = true;
        }
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        final Object fieldValue = beanWrapper.getPropertyValue(field);
        if (optional && (fieldValue == null || org.apache.commons.lang3.StringUtils.isBlank((String) fieldValue))) {
            //skip validation if password is blank and group is optional
            return true;
        }
        final Object matchesValue = beanWrapper.getPropertyValue(matches);
        final boolean matched = ObjectUtils.nullSafeEquals(fieldValue, matchesValue);
        if (matched) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addNode(matches).addConstraintViolation();
            return false;
        }
    }
}
