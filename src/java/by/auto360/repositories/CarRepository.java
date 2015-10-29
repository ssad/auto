package by.auto360.repositories;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.UUID;

public interface CarRepository {
    @SqlUpdate("insert into car (id, model, year, modification, owner) values (:id, :model, :year, :modification, :owner)")
    public void add(
            @Bind("id") UUID id,
            @Bind("model") String model,
            @Bind("year") int year,
            @Bind("modification") String modification,
            @Bind("owner") UUID owner);


}
