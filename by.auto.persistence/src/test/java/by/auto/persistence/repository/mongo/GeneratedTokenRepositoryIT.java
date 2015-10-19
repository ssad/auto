package by.auto.persistence.repository.mongo;

import by.auto.domain.common.GeneratedToken;
import by.auto.domain.common.enums.TokenType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class GeneratedTokenRepositoryIT extends MongoIntegrationTest<GeneratedToken> {
    @Inject
    private GeneratedTokenRepository repository;

    @Test
    public void shouldFindByToken() {
        final GeneratedToken token = repository.findByChangeToken("32980073561258662929564197851150");

        assertThat(token.getAccountId(), is("acc1"));
        assertThat(token.getCreatedDate(), notNullValue());
    }

    @Test
    public void shouldFindByChangeToken() {
        final GeneratedToken saved = repository.save(new GeneratedToken("acc4", TokenType.ForgotPassword));
        final GeneratedToken token = repository.findByChangeToken(saved.getChangeToken());

        assertThat(token, notNullValue());
        assertThat(token.getId(), is(saved.getId()));
    }

    @Test
    public void shouldSave() {
        List<GeneratedToken> all = repository.findAll();
        assertThat(all.size(), is(3));

        final GeneratedToken toSave = new GeneratedToken("acc4", TokenType.ForgotPassword);
        final GeneratedToken saved = repository.save(toSave);

        assertThat(toSave.getAccountId(), is(saved.getAccountId()));
        assertThat(toSave.getType(), is(saved.getType()));

        all = repository.findAll();
        assertThat(all.size(), is(4));
    }

    @Test
    public void shouldDelete() {
        List<GeneratedToken> all = repository.findAll();

        final int was = all.size();
        repository.delete("1");
        all = repository.findAll();

        assertThat(all.size(), is(was - 1));
   }
}