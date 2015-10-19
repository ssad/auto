package by.auto.web.validation.validators;

import org.apache.commons.lang3.StringUtils;
import by.auto.web.validation.constraints.Password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public final class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(final Password password) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotBlank(password) && Pattern.matches("^[0-9a-zA-Z_]{7,19}$", password)) {
            valid = true;
        }
        return valid;
    }
}
