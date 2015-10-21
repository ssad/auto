package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.util.AccountUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class AutoPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(final Authentication authentication, final Object targetDomainObject, final Object permission) {
        boolean hasPermission = false;

        final Account account = AccountUtils.getCurrentAccount();

        if (account != null && account.isAdmin()) {
            hasPermission = true;
        }

        return hasPermission;
    }

    @Override
    public boolean hasPermission(final Authentication authentication, final Serializable targetId, final String targetType, final Object permission) {
        throw new RuntimeException("Id and Class permissions are not supported by " + this.getClass().toString());
    }

}
