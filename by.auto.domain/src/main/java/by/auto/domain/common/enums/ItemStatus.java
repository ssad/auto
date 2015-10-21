package by.auto.domain.common.enums;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public enum ItemStatus {
    Draft, Pending, Approved, InProcess, Done, Closed
}
