package by.auto.service.util;

import by.auto.domain.common.Account;
import by.auto.domain.common.Company;
import by.auto.domain.common.User;
import by.auto.persistence.repository.mongo.CompanyRepository;
import by.auto.persistence.repository.mongo.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ObjectService {
    @Inject
    CompanyRepository companyRepository;

    @Inject
    UserRepository userRepository;

    public String getEmail(final Account account) {
        String emailString = "";
        switch (account.getRole()) {
            case Company:
                Company company = companyRepository.findByAccountId(account.getId());
                if (company != null) {
                    emailString = company.getEmail();
                }
                break;
            case User:
                User user = userRepository.findByAccountId(account.getId());
                if (user != null) {
                    emailString = user.getEmail();
                }
                break;
        }
        return emailString;
    }
}