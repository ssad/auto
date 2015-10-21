package by.auto.web.utils;

import by.auto.domain.common.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public final class AccountUtils {

    public static final Object NO_PASSWORD = null;

    /**
     * Programmatically sign-in the member holding the provided Account.
     * Sets the Account in the SecurityContext, which will associate this Account with the user Session.
     */
    public static void signin(final Account account) {
        SecurityContextHolder.getContext().setAuthentication(authenticationTokenFor(account));
    }

    /**
     * Construct a Spring Security Authentication token from an Account object.
     * Useful for treating the Account as a Principal in Spring Security.
     */
    public static Authentication authenticationTokenFor(final Account account) {
        return new UsernamePasswordAuthenticationToken(account, NO_PASSWORD, getAuthoritiesFor(account));
    }


    public static List<GrantedAuthority> getAuthoritiesFor(final Account account) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().name()));

        return authorities.isEmpty() ? null : authorities;
    }

    public static void logout() {
        SecurityContextHolder.clearContext();
    }

    private AccountUtils() {
    }
}
