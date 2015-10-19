package by.auto.persistence.repository.mongo.converters;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.joda.time.Interval;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class IntervalWriteConverter implements Converter<Interval, DBObject> {
    @Override
    public DBObject convert(final Interval source) {
        final DBObject ob = new BasicDBObject();

        ob.put("start", source.getStart().toDate());
        ob.put("end", source.getEnd().toDate());

        return ob;
    }

}
