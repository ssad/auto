package by.auto.web.security;

import by.auto.web.api.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiTimeoutRedirectFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(ApiTimeoutRedirectFilter.class);

    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    private int customSessionExpiredErrorCode = 403;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);

            logger.debug("Chain processed normally");
        } catch (final IOException ex) {
            throw ex;
        } catch (final Exception ex) {
            final Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
            RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);

            if (ase == null) {
                ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (ase != null) {
                if (ase instanceof AuthenticationException) {
                    throw ase;
                } else if (ase instanceof AccessDeniedException) {

                    if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                        logger.info("User session expired or not logged in yet");
                        final String path = ((HttpServletRequest) request).getServletPath();
                        final boolean isApi = path.startsWith("/api/") || path.startsWith("/admin/api/");

                        if (isApi) {

                            final HttpServletResponse resp = (HttpServletResponse) response;
                            resp.setContentType("application/json");
                            resp.setStatus(this.customSessionExpiredErrorCode);
                            resp.getOutputStream().println(objectMapper.writeValueAsString(APIResponse.error("User session expired or not logged in yet or Access denied")));
                            logger.info("API call detected, send {} error code", this.customSessionExpiredErrorCode);
                        } else {
                            logger.info("Redirect to login page");
                            throw ase;
                        }
                    } else {
                        throw ase;
                    }
                }
            }

        }
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        /**
         * @see org.springframework.security.web.util.ThrowableAnalyzer#initExtractorMap()
         */
        @Override
        protected void initExtractorMap() {
            super.initExtractorMap();

            registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
                @Override
                public Throwable extractCause(final Throwable throwable) {
                    ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                    return ((ServletException) throwable).getRootCause();
                }
            });
        }

    }

    public void setCustomSessionExpiredErrorCode(final int customSessionExpiredErrorCode) {
        this.customSessionExpiredErrorCode = customSessionExpiredErrorCode;
    }
}