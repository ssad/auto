package by.auto.web.validation.constraints;

import by.auto.web.validation.validators.ConfirmValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ConfirmValidator.class)
@Documented
public @interface Confirm {
    String message() default "{Confirm.messages}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The name of the field to be confirmed, e.g. password
     */
    String field();

    String matches() default "";

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Used to specify multiple confirm fields per class.
         *
         * @return array of Confirm object
         */
        Confirm[] value();
    }
}

