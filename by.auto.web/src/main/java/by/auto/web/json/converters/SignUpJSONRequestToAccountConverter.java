package by.auto.web.json.converters;

import by.auto.domain.common.enums.AccountStatus;
import org.springframework.core.convert.converter.Converter;
import by.auto.domain.common.Account;
import by.auto.service.security.PasswordEncoder;
import by.auto.web.api.json.JSONRequests.SignUpJSONRequest;

public class SignUpJSONRequestToAccountConverter implements Converter<SignUpJSONRequest, Account> {
    @Override
    public Account convert(final SignUpJSONRequest accountRequest) {
        return new Account(accountRequest.getLogin().toLowerCase(), PasswordEncoder.encode(accountRequest.getPassword()),
                accountRequest.getRoleName(), AccountStatus.Pending);
    }
}