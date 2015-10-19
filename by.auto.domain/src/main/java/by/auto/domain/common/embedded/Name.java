package by.auto.domain.common.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Name implements Serializable{
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String secondName;
    @JsonProperty
    private String middleName;

    public Name(final String firstName, final String secondName, final String middleName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String toString() {
        return firstName + ((StringUtils.isNotBlank(middleName))?' ' + middleName:"") + ' ' + secondName;
    }
}
