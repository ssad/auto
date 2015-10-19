package by.auto.service.dependency;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import by.auto.domain.dependency.DependencyDomain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DependencyExistTest {

    @Test
    public void shouldExist() {

        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DependencyDomain.class));

        for (final BeanDefinition bd : scanner.findCandidateComponents("by.auto.domain.common")) {
            final Class cls = ClassUtils.resolveClassName(bd.getBeanClassName(),
                    ClassUtils.getDefaultClassLoader());
            final DependencyDomain dependencyDomain = (DependencyDomain) cls.getAnnotation(DependencyDomain.class);

            assertThat(isExist(dependencyDomain.dependencyResolver()), is(true));
        }

    }

    private boolean isExist(final String resolverName) {
        boolean isExist = false;

        final ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AssignableTypeFilter(Service.class));
        for (final BeanDefinition bdService : scanner.findCandidateComponents("by.auto.service.dependency")) {
            final Class clazz = ClassUtils.resolveClassName(bdService.getBeanClassName(),
                    ClassUtils.getDefaultClassLoader());
            final Service service = (Service) clazz.getAnnotation(Service.class);
            if (service.value().equals(resolverName)) {
                isExist = true;
            }
        }

        return isExist;
    }
}
