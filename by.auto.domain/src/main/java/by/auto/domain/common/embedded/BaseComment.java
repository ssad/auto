package by.auto.domain.common.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseComment {
    @JsonProperty
    private String userId;
    @JsonProperty
    private int rate;
    @JsonProperty
    private String Description;

    public BaseComment(final String userId, final int rate, final String description) {
        this.userId = userId;
        this.rate = rate;
        Description = description;
    }

    public String getUserId() {
        return userId;
    }

    public int getRate() {
        return rate;
    }

    public String getDescription() {
        return Description;
    }
}
