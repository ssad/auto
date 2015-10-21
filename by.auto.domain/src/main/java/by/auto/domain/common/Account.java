package by.auto.domain.common;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.common.enums.RoleName;
import by.auto.domain.dependency.DependencyDomain;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("Account")
@DependencyDomain(dependencyResolver = "accountDependency")

public class Account extends AuditableDomainObject {
    @Indexed(unique = true)
    private String login;

    private String password;

    private RoleName role;

    private AccountStatus status;

    public Account(final String login, final String password, final RoleName role, final AccountStatus status) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public RoleName getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return getRole() == RoleName.Admin;
    }

    public AccountStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setStatus(final AccountStatus status) {
        this.status = status;
    }
}