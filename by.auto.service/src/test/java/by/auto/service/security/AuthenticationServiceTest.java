package by.auto.service.security;

import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.common.enums.RoleName;
import by.auto.persistence.repository.mongo.AccountRepository;
import by.auto.service.exception.InvalidPasswordException;
import by.auto.service.exception.SignInNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    private Account account;

    @Mock
    private AccountRepository accountRepository;

    @Before
    public void setUp() {
        authenticationService = new AuthenticationService();
        account = new Account("damon.salvatore@gmail.com", PasswordEncoder.encode("password"), RoleName.User, AccountStatus.Active);

        ReflectionTestUtils.setField(authenticationService, "accountRepository", accountRepository);
    }

    @Test(expected = SignInNotFoundException.class)
    public void shouldThrowSignInNotFoundException() {
        authenticationService.authenticate("unknown user", null);
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldThrowInvalidPasswordException() {
        given(accountRepository.findByLoginActive(account.getLogin())).willReturn(account);

        authenticationService.authenticate(account.getLogin(), "passw0rd");
    }

    @Test
    public void shouldAuthenticate() {
        given(accountRepository.findByLoginActive(account.getLogin())).willReturn(account);
        final Account authenticatedAccount =
                authenticationService.authenticate(account.getLogin(), "password");

        assertThat(authenticatedAccount, is(sameInstance(account)));
    }
}
