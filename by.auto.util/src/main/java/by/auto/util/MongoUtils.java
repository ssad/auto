package by.auto.util;

import by.auto.domain.common.DomainObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

public final class MongoUtils {

    public static String getCollectionName(final Class<? extends DomainObject> domainType) {
        final Document document = domainType.getAnnotation(Document.class);
        String collectionName = document.collection();
        if (StringUtils.isEmpty(collectionName)) {
            collectionName = StringUtils.uncapitalize(domainType.getSimpleName());
        }

        return collectionName;
    }
}

