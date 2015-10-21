package by.auto.util;

import by.auto.util.MongoUtils;
import org.junit.Test;
import by.auto.domain.common.Account;
import by.auto.domain.testmothers.AccountMother;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MongoUtilsTest {
    @Test
    public void shouldGetCollectionName() {
        final Account account = AccountMother.createUserAccount();

        final String collectionName = MongoUtils.getCollectionName(account.getClass());

        assertThat(collectionName, is("account"));
    }
}
