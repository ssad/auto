package by.auto.domain.common;

import by.auto.domain.common.embedded.BaseCompany;
import by.auto.domain.common.embedded.Name;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("User")

public class User extends BaseCompany {
    @JsonProperty
    private Name name;
    @JsonProperty
    private String email;


    public User(final Name name, final List<String> phoneNumber, final String email) {
        super(phoneNumber);
        this.name = name;
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
