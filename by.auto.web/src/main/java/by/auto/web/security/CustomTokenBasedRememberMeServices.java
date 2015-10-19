package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.persistence.repository.mongo.AccountRepository;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class CustomTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    @Inject
    private AccountRepository accountRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Deprecated
    public CustomTokenBasedRememberMeServices() {
    }

    public CustomTokenBasedRememberMeServices(final String key, final UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    @Override
    protected Authentication createSuccessfulAuthentication(final HttpServletRequest request, final UserDetails user) {
        final Account account = accountRepository.findByLogin(user.getUsername());
        final RememberMeAuthenticationToken auth = new RememberMeAuthenticationToken(getKey(), account,
                authoritiesMapper.mapAuthorities(user.getAuthorities()));
        auth.setDetails(getAuthenticationDetailsSource().buildDetails(request));
        return auth;
    }
}