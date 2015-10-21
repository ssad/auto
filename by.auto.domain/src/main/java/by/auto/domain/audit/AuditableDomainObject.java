package by.auto.domain.audit;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.index.Indexed;
import by.auto.domain.common.DomainObject;

public abstract class AuditableDomainObject extends DomainObject implements Auditable<String, String> {

    @JsonProperty
    private String createdBy;
    @JsonProperty
    private DateTime createdDate;
    @JsonProperty
    private String lastModifiedBy;
    @JsonProperty
    @Indexed
    private DateTime lastModifiedDate;

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public DateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(final DateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(final DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean isNew() {
        return StringUtils.isEmpty(getId());
    }
}
