package by.auto.domain.common.embedded;

import by.auto.domain.audit.AuditableDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

public class BaseCompany extends AuditableDomainObject {
    @Indexed
    @JsonProperty
    private String accountId;

    @JsonProperty
    private List<String> phoneNumber;

    public BaseCompany(final String accountId, final List<String> phoneNumber) {
        this.accountId = accountId;
        this.phoneNumber = phoneNumber;
    }

    public BaseCompany(final List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }
}
