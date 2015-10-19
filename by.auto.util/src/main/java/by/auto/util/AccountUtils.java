package by.auto.util;

import by.auto.domain.common.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AccountUtils {
    private static final String ANONYMOUS = "Anonymous@auto.by";

    /**
     * It defines current user from spring security. You must set env variable batch.default.account.email to define
     * user email if security context is not available.
     *
     * @return current authenticated user email or batch.default.account.email if user not authenticated
     */
    public static String getCurrentLogin() {
        final Account account = getCurrentAccount();
        if (account == null) {
            return ANONYMOUS;
        }
        return account.getLogin();
    }

    /**
     * Get the currently authenticated Account principal.
     */
    public static Account getCurrentAccount() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        final Object principal = authentication.getPrincipal();
        return principal instanceof Account ? (Account) principal : null;
    }

    public static String getCurrentAccountId() {
        final Account account = AccountUtils.getCurrentAccount();
        if (account == null) {
            return ANONYMOUS;
        } else {
            return account.getId();
        }
    }
}
