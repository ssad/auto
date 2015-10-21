package by.auto.web.utils;

import by.auto.domain.common.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationUtil {
    public static Account getPrincipal(final NativeWebRequest webRequest) {
        return getPrincipal(webRequest.getNativeRequest(HttpServletRequest.class));
    }

    public static Account getPrincipal(final HttpServletRequest request) {
        final Authentication auth = (Authentication) request.getUserPrincipal();

        final Object account = auth != null && auth.getPrincipal() instanceof Account ? auth.getPrincipal() : null;

        return (Account) account;
    }

    public static String getRemoteIp(final HttpServletRequest request) {
        String forwardedIp = request.getHeader("HTTP_X_FORWARDED_FOR"); //IP-адрес клиента при подключении через неанонимный прокси

        if (StringUtils.isBlank(forwardedIp)) {
            forwardedIp = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isNotBlank(forwardedIp)) {
            return forwardedIp;
        } else {
            return request.getRemoteAddr(); //Удаленный IP-адрес клиента
        }
    }
}