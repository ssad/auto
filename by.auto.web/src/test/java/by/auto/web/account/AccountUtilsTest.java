package by.auto.web.account;

import by.auto.domain.common.enums.RoleName;
import by.auto.domain.testmothers.AccountMother;
import by.auto.web.utils.AccountUtils;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountUtilsTest {
    @Test
    public void shouldGetAuthoritiesForUser() {
        final List<GrantedAuthority> authorities = AccountUtils.getAuthoritiesFor(AccountMother.createUserAccount());

        assertThat(authorities.size(), is(1));

        assertThat(authorities.get(0).getAuthority(), is(RoleName.User.name()));
    }
}
