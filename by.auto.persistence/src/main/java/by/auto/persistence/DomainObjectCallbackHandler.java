package by.auto.persistence;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoOperations;
import by.auto.domain.common.DomainObject;

/**
 * mongoTemplate should be initialized before usage
 *
 * @param <T> Object class to process
 */
public abstract class DomainObjectCallbackHandler<T extends DomainObject> implements DocumentCallbackHandler {

    private MongoOperations mongoTemplate;

    public abstract void processDocument(T domain);

    @Override
    public void processDocument(final DBObject dbObject) throws MongoException, DataAccessException {
        final Class<? extends T> itemClass = (Class<? extends T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), DomainObjectCallbackHandler.class);
        processDocument(mongoTemplate.getConverter().read(itemClass, dbObject));
    }

    public void setMongoTemplate(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}