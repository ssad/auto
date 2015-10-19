package by.auto.service;

import by.auto.domain.common.Account;
import by.auto.domain.common.GeneratedToken;
import by.auto.domain.common.enums.TokenType;
import by.auto.domain.testmothers.AccountMother;
import by.auto.persistence.repository.mongo.AccountRepository;
import by.auto.persistence.repository.mongo.GeneratedTokenRepository;
import by.auto.service.AccountService;
import by.auto.service.email.EmailSender;
import by.auto.service.exception.ForgotAccountException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private GeneratedTokenRepository generatedTokenRepository;

    private final AccountService accountService = new AccountService();

    private Account account;

    private static final String login = "login";

    private GeneratedToken token;

    @Before
    public void setUp() {
        account = AccountMother.createUserAccount();
        ReflectionTestUtils.setField(accountService, "accountRepository", accountRepository);
        ReflectionTestUtils.setField(accountService, "generatedTokenRepository", generatedTokenRepository);
        ReflectionTestUtils.setField(accountService, "emailSender", emailSender);

        ReflectionTestUtils.setField(account, "id", "accId");

        token = new GeneratedToken("accId", TokenType.ForgotPassword);
    }

    @Test(expected = ForgotAccountException.class)
    public void shouldThrowException() {
        given(accountRepository.findByLogin(login)).willReturn(null);
        accountService.generatePasswordToken(login, TokenType.Register);
    }

    @Test
    public void shouldGetAccountByToken() {
        given(generatedTokenRepository.findByChangeToken(token.getChangeToken())).willReturn(token);
        given(accountRepository.findOne(token.getAccountId())).willReturn(account);
        final Account accountByGeneratedToken = accountService.findByGeneratedToken(token.getChangeToken());
        assertThat(accountByGeneratedToken.getLogin(), is(account.getLogin()));
    }

    @Test
    public void shouldGetNullAccountByToken() {
        given(generatedTokenRepository.findByChangeToken(token.getChangeToken())).willReturn(null);
        final Account accountByGeneratedToken = accountService.findByGeneratedToken(token.getChangeToken());
        assertThat(accountByGeneratedToken, nullValue());
    }
}
