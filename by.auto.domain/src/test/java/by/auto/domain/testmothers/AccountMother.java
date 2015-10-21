package by.auto.domain.testmothers;

import org.springframework.test.util.ReflectionTestUtils;
import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.common.enums.RoleName;

public class AccountMother {
    public static Account createUserAccount() {
        final Account account = new Account("login", "password", RoleName.User, AccountStatus.Active);
        ReflectionTestUtils.setField(account, "id", "528491ef57e8a05ce09db938");
        return account;
    }

    public static Account createUserAccountAdmin() {
        Account account = createUserAccount();
        ReflectionTestUtils.setField(account, "role", RoleName.Admin);
        return account;
    }
}
