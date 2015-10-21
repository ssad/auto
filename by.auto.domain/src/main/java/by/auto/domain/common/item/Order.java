package by.auto.domain.common.item;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.domain.common.embedded.BaseComment;
import by.auto.domain.common.enums.ItemStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.Interval;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@TypeAlias("Order")
@CompoundIndexes({
        @CompoundIndex(name = "orderIndex1", def = "{\"userId\":1}")
})
public class Order extends AuditableDomainObject {
    @JsonProperty
    private String userId;
    @JsonProperty
    private String itemId;
    @JsonProperty
    private Interval scheduleTime;
    @JsonProperty
    private ItemStatus status;
    @JsonProperty
    private String description;
    @JsonProperty
    private List<BaseComment> comments;

    public Order(final String userId, final String itemId, final Interval scheduleTime, final ItemStatus status, final String description, final List<BaseComment> comments) {
        this.userId = userId;
        this.itemId = itemId;
        this.scheduleTime = scheduleTime;
        this.status = status;
        this.description = description;
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public Interval getScheduleTime() {
        return scheduleTime;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public List<BaseComment> getComments() {
        return comments;
    }
}
