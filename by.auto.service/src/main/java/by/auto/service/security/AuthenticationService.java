package by.auto.service.security;

import by.auto.domain.common.Account;
import by.auto.persistence.repository.mongo.AccountRepository;
import by.auto.service.exception.InvalidPasswordException;
import by.auto.service.exception.SignInNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AuthenticationService {
    @Inject
    AccountRepository accountRepository;
    /**
     * @param signin   user name
     * @param password user password
     * @return an authenticated user account
     * @throws SignInNotFoundException  thrown if no data available for specified user name
     * @throws InvalidPasswordException thrown if password does not match
     */
    public Account authenticate(final String signin, final String password) throws SignInNotFoundException, InvalidPasswordException {
        final Account account = accountRepository.findByLoginActive(signin);
        if (account == null) {
            throw new SignInNotFoundException(signin);
        }
        if (!account.getPassword().equals(PasswordEncoder.encode(password))) {
            throw new InvalidPasswordException();
        }

        return account;
    }
}
