package by.auto.service.dependency;

import by.auto.domain.common.Account;
import by.auto.domain.dependency.DependencyDomainHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("accountDependency")
public class AccountDependency extends DependencyDomainHandler<Account> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDependency.class);

    @Override
    public void resolveSaveOrUpdate(final Account source, final Account before) {
        if (before != null) {

        }
    }

    /**
     * @param id    an account id
     * @param clazz a account instance
     */
    @Override
    public void resolveDelete(final String id, final Class<Account> clazz) {

        super.resolveDelete(id, clazz);
    }
}