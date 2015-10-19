package by.auto.domain.common.embedded;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class Service implements Serializable {
    @JsonProperty
    private String tag;
    @JsonProperty
    private long lasting;
    @JsonProperty
    private BigDecimal cost;

    public Service(final String tag, final long lasting, final BigDecimal cost) {
        this.tag = tag;
        this.lasting = lasting;
        this.cost = cost;
    }

    public String getTag() {
        return tag;
    }

    public long getLasting() {
        return lasting;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
