package by.auto.domain.common;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.domain.common.embedded.BaseComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("Comment")
@CompoundIndexes({
        @CompoundIndex(name = "commentIndex1", def = "{\"companyId\":1}")
})
public class Comment extends AuditableDomainObject {
    @JsonProperty
    private String companyId;
    @JsonProperty
    private BaseComment comment;
    @JsonProperty
    private String commentId;

    public Comment(final String companyId, final BaseComment comment, final String commentId) {
        this.companyId = companyId;
        this.comment = comment;
        this.commentId = commentId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public BaseComment getComment() {
        return comment;
    }

    public String getCommentId() {
        return commentId;
    }
}
