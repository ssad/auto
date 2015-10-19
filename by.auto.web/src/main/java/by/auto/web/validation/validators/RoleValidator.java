package by.auto.web.validation.validators;

import by.auto.domain.common.enums.RoleName;
import by.auto.web.validation.constraints.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<Role, RoleName> {
    @Override
    public void initialize(final Role password) {

    }

    @Override
    public boolean isValid(final RoleName roleName, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        if (roleName == RoleName.Company || roleName == RoleName.User) {
            valid = true;
        }
        return valid;
    }
}