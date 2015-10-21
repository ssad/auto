package by.auto.web.validation.validators;

import by.auto.domain.common.category.Category;
import by.auto.domain.common.enums.RoleName;
import by.auto.web.validation.constraints.Company;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class CompanyValidator implements ConstraintValidator<Company, Object> {
    private String role;
    private String company;
    private String category;

    @Override
    public void initialize(final Company constraintAnnotation) {
        role = constraintAnnotation.role();
        company = constraintAnnotation.company();
        category = constraintAnnotation.category();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        final BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        final RoleName roleName = (RoleName) beanWrapper.getPropertyValue(role);
        final String companyName = (String) beanWrapper.getPropertyValue(company);

        if (roleName == RoleName.User) {
            return true;
        } else {
            if (roleName == RoleName.Company) {
                if (StringUtils.isNotBlank(companyName)) {
                    valid = true;
                } else {
                    return false;
                }
            }
        }

        return valid;
    }
}