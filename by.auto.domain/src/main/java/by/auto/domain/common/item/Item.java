package by.auto.domain.common.item;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.domain.common.Times;
import by.auto.domain.common.embedded.Service;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("Item")
@CompoundIndexes({
        @CompoundIndex(name = "itemIndex1", def = "{\"companyId\":1}")
})

public class Item extends AuditableDomainObject {
    @JsonProperty
    private String companyId;
    @JsonProperty
    private String description;
    @JsonProperty
    private List<Service> services;
    @JsonProperty
    private Times times;

    public Item(final String companyId, final String description, final List<Service> services, final Times times) {
        this.companyId = companyId;
        this.description = description;
        this.services = services;
        this.times = times;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getDescription() {
        return description;
    }

    public List<Service> getServices() {
        return services;
    }

    public Times getTimes() {
        return times;
    }
}
