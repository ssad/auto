package by.auto.persistence.repository.gridfs;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;
import by.auto.domain.common.enums.ConfirmedType;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.UUID;

@Repository
public class GridFsStorageRepository {

    public static final String COLLECTION_FROM_META_FIELD = "collectionFrom";
    public static final String ATTACHMENT_TYPE_META_FIELD = "attachmentType";
    public static final String FILE_NAME_META_FIELD = "fileName";
    public static final String IMAGE_TYPE_META_FIELD = "imageType";
    public static final String CONFIRMED_META_FIELD = "confirmed";
    public static final String ITEM_ID_META_FIELD = "itemId";

    @Inject
    private GridFsOperations operations;

    public String save(final InputStream inputStream, final String collectionFrom, final String filename, String contentType) {
        final DBObject metaData = new BasicDBObject();
        metaData.put(COLLECTION_FROM_META_FIELD, collectionFrom);
        metaData.put(FILE_NAME_META_FIELD, filename);
        metaData.put(CONFIRMED_META_FIELD, ConfirmedType.NOT_CONFIRMED.name());

        final GridFSFile file = operations.store(inputStream, String.valueOf(UUID.randomUUID()),contentType, metaData);

        return file.getId().toString();
    }

    public String save(final InputStream inputStream, final String filename, final String contentType) {
        final DBObject metaData = new BasicDBObject();
        metaData.put(FILE_NAME_META_FIELD, filename);

        final GridFSFile file = operations.store(inputStream, String.valueOf(UUID.randomUUID()), contentType, metaData);

        return file.getId().toString();
    }

    public GridFSDBFile get(final String id) {
        return operations.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
    }

    public void delete(final String id) {
        operations.delete(new Query(Criteria.where("_id").is(new ObjectId(id))));
    }

    public void deleteNotConfirmed() {
        operations.delete(new Query(Criteria.where("metadata." + CONFIRMED_META_FIELD).is(ConfirmedType.NOT_CONFIRMED.name())
                .and("uploadDate").lt((new DateTime()).minusDays(1).toDate())));
    }

    public void confirm(final String attachmentId, final String itemId) {
        GridFSFile file = get(attachmentId);
        DBObject metaData = (DBObject) file.get("metadata");
        if (StringUtils.isNotEmpty(itemId)) {
            metaData.put(ITEM_ID_META_FIELD, itemId);
        }
        if (metaData.containsField(CONFIRMED_META_FIELD)) {
            metaData.removeField(CONFIRMED_META_FIELD);
        }
        file.save();
    }
}
