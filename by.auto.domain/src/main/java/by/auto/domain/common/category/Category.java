package by.auto.domain.common.category;

import by.auto.domain.audit.AuditableDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("Category")
public class Category extends AuditableDomainObject {
    @JsonProperty
    protected String name;

    @JsonProperty
    private List<String> tags;

    @JsonProperty
    private String description;

    public Category(String name, List<String> tags, String description) {
        this.name = name;
        this.tags = tags;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }
}
