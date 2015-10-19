package by.auto.service.email;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HandleEmail {
    Class<? extends EmailHandler> className();
}
