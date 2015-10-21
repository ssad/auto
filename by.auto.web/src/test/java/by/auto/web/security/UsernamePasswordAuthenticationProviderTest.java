package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.domain.testmothers.AccountMother;
import by.auto.service.exception.InvalidPasswordException;
import by.auto.service.exception.SignInNotFoundException;
import by.auto.service.security.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UsernamePasswordAuthenticationProviderTest {
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UsernamePasswordAuthenticationToken authentication;

    private Account account;

    @Before
    public void setUp() {
        account = AccountMother.createUserAccount();

        usernamePasswordAuthenticationProvider = new UsernamePasswordAuthenticationProvider();
        given(authentication.getName()).willReturn(account.getLogin());
        given(authentication.getCredentials()).willReturn(account.getPassword());

        ReflectionTestUtils.setField(usernamePasswordAuthenticationProvider, "authenticationService", authenticationService);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUsernameNotFoundException() {
        given(authenticationService.authenticate(account.getLogin(), account.getPassword())).
                willThrow(new SignInNotFoundException(account.getLogin()));

        usernamePasswordAuthenticationProvider.authenticate(authentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void shouldThrowBadCredentialsException() {
        given(authenticationService.authenticate(account.getLogin(), account.getPassword())).
                willThrow(new InvalidPasswordException());

        usernamePasswordAuthenticationProvider.authenticate(authentication);
    }

    @Test
    public void shouldAuthenticate() {
        given(authenticationService.authenticate(account.getLogin(), account.getPassword())).
                willReturn(account);

        final Authentication authenticatedToken = usernamePasswordAuthenticationProvider.authenticate(authentication);

        assertThat(authenticatedToken, is(instanceOf(UsernamePasswordAuthenticationToken.class)));
        assertThat((Account) authenticatedToken.getPrincipal(), is(sameInstance(account)));
        assertThat(authenticatedToken.getCredentials(), is(nullValue()));
        assertThat(authenticatedToken.getAuthorities().size(), is(1));
        final SimpleGrantedAuthority authority = (SimpleGrantedAuthority) authenticatedToken.getAuthorities().toArray()[0];
        assertThat(authority.getAuthority(), is(account.getRole().name()));
    }
}
