package by.auto.web.validation.constraints;

import by.auto.web.validation.validators.CompanyValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompanyValidator.class)
@Documented
public @interface Company {
    String message() default "{Company}";

    String role();
    String company();
    String category();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
