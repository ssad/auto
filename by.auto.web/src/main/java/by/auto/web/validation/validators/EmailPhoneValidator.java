package by.auto.web.validation.validators;

import by.auto.web.validation.constraints.EmailPhone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailPhoneValidator implements ConstraintValidator<EmailPhone, String> {
    @Override
    public void initialize(final EmailPhone emailPhone) {

    }

    @Override
    public boolean isValid(final String emailPhone, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (StringUtils.isNotBlank(emailPhone)) {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (emailPhone.matches("^(\\d{9})$") || emailValidator.isValid(emailPhone)) {
                valid = true;
            }
        }

        return valid;
    }
}