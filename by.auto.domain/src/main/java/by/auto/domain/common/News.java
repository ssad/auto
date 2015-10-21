package by.auto.domain.common;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.domain.common.embedded.BaseComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("News")
@CompoundIndexes({
        @CompoundIndex(name = "newsIndex", def = "{\"releaseDate\":1}")
})
public class News extends AuditableDomainObject {
    @JsonProperty
    private String companyId;
    @JsonProperty
    private DateTime releaseDate;
    @JsonProperty
    private String description;
    @JsonProperty
    private List<BaseComment> comments;

    public News(final String companyId, final DateTime releaseDate, final String description, final List<BaseComment> comments) {
        this.companyId = companyId;
        this.releaseDate = releaseDate;
        this.description = description;
        this.comments = comments;
    }

    public String getCompanyId() {
        return companyId;
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public List<BaseComment> getComments() {
        return comments;
    }
}
