package by.auto.web.validation.validators;

import by.auto.service.AccountService;
import by.auto.web.validation.constraints.Login;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<Login, String> {
    private static final String USERNAME_PATTERN = "^[_a-zA-Z0-9а-яА-ЯёЁ@]+$";

    @Inject
    AccountService accountService;

    @Override
    public void initialize(final Login login) {

    }

    @Override
    public boolean isValid(final String login, final ConstraintValidatorContext context) {
        boolean valid = false;
        if (StringUtils.isNotBlank(login)) {
            if (login.matches(USERNAME_PATTERN)) {
                valid = true;
            }
            if (accountService.findByLogin(login) != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("{Login.exist}").addConstraintViolation();
                valid = false;
            }
        }

        return valid;
    }
}