package by.auto360.resources;

import by.auto360.domain.Person;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {
    private final SimpleDateFormat sdf = new SimpleDateFormat();
    private final DBI dbi;

    public PersonResource(DBI dbi) {
        this.dbi = dbi;
    }

    @POST
    public UUID create(@Valid final Person person) {
        int count;
        final UUID uuid = UUID.randomUUID();
        try (final Handle h = dbi.open()) {
            count = h.insert(
                    "insert into person (id, first, last, birth) values (?, ?, ?, ?)",
                    uuid, person.getFirst(), person.getLast(), person.getBirth());
        }

        return count == 1 ? uuid : null;
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") final UUID id) {
        try (final Handle h = dbi.open()) {
            final Map<String, Object> person = h.createQuery("select id, first, last, birth from person where id = :id").bind("id", id).first();

            return new Person(
                    (String) person.get("first"),
                    (String) person.get("last"),
                    sdf.format((Date) person.get("birth")));
        }
    }
}
