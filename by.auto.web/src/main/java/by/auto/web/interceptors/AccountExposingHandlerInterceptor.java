package by.auto.web.interceptors;

import by.auto.domain.common.Account;
import by.auto.web.utils.AuthenticationUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountExposingHandlerInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        //todo возможно тут надо User или Company
        final Account account = AuthenticationUtil.getPrincipal(request);
        if (account != null) {
            request.setAttribute("account", account);
        }
        return true;
    }
}
