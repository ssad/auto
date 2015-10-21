package by.auto.domain.common.item;

import by.auto.domain.audit.AuditableDomainObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@TypeAlias("OrderHistory")
public class OrderHistory extends AuditableDomainObject {
    @JsonProperty
    private Order order;

    public OrderHistory(final Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
