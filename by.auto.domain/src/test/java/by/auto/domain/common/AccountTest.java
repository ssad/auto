package by.auto.domain.common;

import by.auto.domain.common.enums.AccountStatus;
import org.junit.Test;
import by.auto.domain.common.enums.RoleName;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {
    @Test
    public void shouldBeAdmin() {
        final Account admin = new Account("", "", RoleName.Admin, AccountStatus.Active);
        assertThat(admin.isAdmin(), is(true));
    }

    @Test
    public void shouldNotBeAdmin() {
        final Account user = new Account("", "", RoleName.User, AccountStatus.Active);
        assertThat(user.isAdmin(), is(false));
    }
}
