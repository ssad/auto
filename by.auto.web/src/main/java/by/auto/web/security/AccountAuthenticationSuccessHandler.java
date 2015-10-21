package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.web.routes.Routes;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            final Account account = (Account) authentication.getPrincipal();
        }

        final String refUrl = request.getHeader("Referer");
        if (refUrl.contains(Routes.SIGNIN) || refUrl.contains(Routes.Signup.SIGNUP)) {
            super.onAuthenticationSuccess(request, response, authentication);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            getRedirectStrategy().sendRedirect(request,response, refUrl);
        }
    }
}