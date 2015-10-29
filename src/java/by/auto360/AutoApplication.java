package by.auto360;

import by.auto360.config.AutoConfiguration;
import by.auto360.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class AutoApplication extends Application<AutoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AutoApplication().run(args);
    }

    @Override
    public void run(final AutoConfiguration configuration, final Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI dbi = factory.build(environment, configuration.getDatabase(), "database");
//        environment.jersey().register(new CarResource(jdbi.onDemand(CarRepository.class)));
        environment.jersey().register(new PersonResource(dbi));
    }

    @Override
    public void initialize(final Bootstrap<AutoConfiguration> bootstrap) {
        // serve static files
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }
}
