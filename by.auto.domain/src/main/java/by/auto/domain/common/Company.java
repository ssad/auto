package by.auto.domain.common;

import by.auto.domain.common.category.Category;
import by.auto.domain.common.embedded.Address;
import by.auto.domain.common.embedded.BaseCompany;
import by.auto.domain.common.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("Company")

@CompoundIndexes({
        @CompoundIndex(name = "companyIndex1", def = "{\"name\":1}")
})
public class Company extends BaseCompany {
    @JsonProperty
    private String name;
    @JsonProperty
    private Category category;
    @JsonSerialize(as = Address.class)
    private Address address;
    @JsonProperty
    private String description;
    @JsonProperty
    private String site;
    @JsonProperty
    private String email;
    @JsonProperty
    @Indexed
    private String permanentLink;
    @JsonProperty
    private String logoId;
    @JsonProperty
    private List<String> pictures;
    @JsonProperty
    private int rate;
    @JsonProperty
    private List<String> tags;
    @JsonProperty
    private Times times;
    private AccountStatus status;

    public Company(final String accountId, final List<String> phoneNumber, final String name, final Category category, final Address address, final String description, final String site, final String email, final String permanentLink, final String logoId, final List<String> pictures, final int rate, final List<String> tags, final Times times) {
        super(accountId, phoneNumber);
        this.name = name;
        this.category = category;
        this.address = address;
        this.description = description;
        this.site = site;
        this.email = email;
        this.permanentLink = permanentLink;
        this.logoId = logoId;
        this.pictures = pictures;
        this.rate = rate;
        this.tags = tags;
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Address getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getSite() {
        return site;
    }

    public String getEmail() {
        return email;
    }

    public String getPermanentLink() {
        return permanentLink;
    }

    public String getLogoId() {
        return logoId;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public int getRate() {
        return rate;
    }

    public List<String> getTags() {
        return tags;
    }

    public Times getTimes() {
        return times;
    }

    public AccountStatus getStatus() {
        return status;
    }
}
