package by.auto.domain.common;

import by.auto.domain.common.enums.TokenType;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Will be used to store token for forgot password functionality and account registration
 * createdDate will be used as TTL, not supported in spring-data yet
 */
@Document
@TypeAlias("GeneratedToken")
public class GeneratedToken extends DomainObject {
    private String accountId;
    @Indexed(unique = true)
    private String changeToken;
    private TokenType type;
    private DateTime createdDate;

    public GeneratedToken() {
        super();
    }

    public GeneratedToken(final String accountId, final TokenType type) {
        checkArgument(accountId != null, "accountId must not be null");
        checkArgument(type != null, "type must not be null");
        this.accountId = accountId;
        this.changeToken = generateToken();
        this.createdDate = DateTime.now();
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getChangeToken() {
        return changeToken;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public TokenType getType() {
        return type;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
