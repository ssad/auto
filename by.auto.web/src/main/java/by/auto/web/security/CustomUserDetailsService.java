package by.auto.web.security;

import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.persistence.repository.mongo.AccountRepository;
import by.auto.web.utils.AccountUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Inject
    private AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
        final Account account = accountRepository.findByLogin(userName);

        final boolean enabled = (account.getStatus() == AccountStatus.Active);
        final boolean accountNonExpired = (account.getStatus() == AccountStatus.Active);
        final boolean credentialsNonExpired = (account.getStatus() == AccountStatus.Active);
        final boolean accountNonLocked = (account.getStatus() == AccountStatus.Active);

        return new User(account.getLogin(),
                account.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                AccountUtils.getAuthoritiesFor(account));
    }
}
