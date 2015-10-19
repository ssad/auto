package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.service.exception.InvalidPasswordException;
import by.auto.service.exception.SignInNotFoundException;
import by.auto.service.security.AuthenticationService;
import by.auto.web.utils.AccountUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Inject
    private AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        try {
            final Account account = authenticationService.authenticate(token.getName().toLowerCase(), (String) token.getCredentials());

            return authenticatedToken(account, authentication);
        } catch (final SignInNotFoundException ex) {
            throw new UsernameNotFoundException(token.getName(), ex);
        } catch (final InvalidPasswordException ex) {
            throw new BadCredentialsException("Invalid password", ex);
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    // internal helper
    private Authentication authenticatedToken(final Account account, final Authentication original) {
        final List<GrantedAuthority> authorities = AccountUtils.getAuthoritiesFor(account);

        final UsernamePasswordAuthenticationToken authenticated =
                new UsernamePasswordAuthenticationToken(account, null, authorities);
        authenticated.setDetails(original.getDetails());

        return authenticated;
    }
}
