package by.auto.web.validation.constraints;

import by.auto.web.validation.validators.PasswordValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static by.auto.web.validation.ValidationConstraints.PASSWORD_MIN_LENGTH;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Size(message = "{Size.password}")
@NotEmpty
public @interface Password {
    String message() default "{Password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @OverridesAttribute(constraint = Size.class, name = "min") int min() default PASSWORD_MIN_LENGTH;
}
