package by.auto.web.resolvers;

import by.auto.domain.common.Account;
import by.auto.web.utils.AuthenticationUtil;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AccountHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return Account.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        @SuppressWarnings("UnnecessaryLocalVariable")
        final Account account = AuthenticationUtil.getPrincipal(webRequest);

        if (account == null) {
            throw new AccessDeniedException("Access Denied. Account is null");
        }

        return account;
    }
}
