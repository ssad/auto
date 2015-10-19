package by.auto.domain.dependency;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DependencyDomain {
    String dependencyResolver();
}
