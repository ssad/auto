package by.auto.persistence.repository.mongo;

import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.testmothers.AccountMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AccountRepositoryIT extends MongoIntegrationTest<Account> {
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private MongoOperations mongoTemplate;
    private Account account;

    @Before
    public void setUp() {
        account = AccountMother.createUserAccount();
        ReflectionTestUtils.setField(account, "id", "123456789012345678901234");
    }

    @Test
    public void shouldSaveAccount() {
        Account foundAccount = accountRepository.findByLogin(account.getLogin());

        assertThat(foundAccount, is(nullValue()));

        accountRepository.save(account);

        foundAccount = accountRepository.findByLogin(account.getLogin());

        assertThat(foundAccount, is(not(nullValue())));
        assertThat(foundAccount.getLogin(), is(account.getLogin()));
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldThrowExceptionOnDuplicateAccountIdInsertion() {
        accountRepository.save(account);

        final Account duplicateAccount = new Account(account.getLogin(), account.getPassword(), account.getRole(), account.getStatus());
        mongoTemplate.insert(duplicateAccount);
    }

    @Test
    public void shouldReturnNullIfAccountWasNotFound() {
        final Account foundAccount = accountRepository.findByLogin("login1");

        assertThat(foundAccount, is(nullValue()));
    }

    @Test
    public void shouldFindActiveUser() {
        account.setStatus(AccountStatus.Pending);
        accountRepository.save(account);
        Account foundAccount = accountRepository.findByLoginActive(account.getLogin());
        assertThat(foundAccount, is(nullValue()));

        accountRepository.delete(account.getId());

        account.setStatus(AccountStatus.Active);
        accountRepository.save(account);
        foundAccount = accountRepository.findByLoginActive(account.getLogin());
        assertThat(foundAccount, is(notNullValue()));
    }

    @Test
    public void shouldDeleteById() {
        accountRepository.save(account);
        Page<Account> result = accountRepository.findAll(new PageRequest(0, 10));
        assertThat(result.getContent().size(), is(3));
        assertThat(result.getNumberOfElements(), is(3));

        accountRepository.delete(account.getId());

        result = accountRepository.findAll(new PageRequest(0, 10));
        assertThat(result.getContent().size(), is(2));
        assertThat(result.getNumberOfElements(), is(2));
    }
}
