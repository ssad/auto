package by.auto.persistence.repository.mongo.converters;

import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Date;

@ReadingConverter
public class IntervalReadConverter implements Converter<DBObject, Interval> {
    @Override
    public Interval convert(final DBObject source) {
        final Date start = (Date) source.get("start");
        final Date end = (Date) source.get("end");

        final DateTime startDate = new DateTime(start.getTime());
        final DateTime endDate = new DateTime(end.getTime());

        return new Interval(startDate, endDate);
    }
}
