package by.auto360;

import by.auto360.config.AutoConfiguration;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AutoApplication extends Application<AutoConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AutoApplication().run(args);
    }

    @Override
    public void run(final AutoConfiguration configuration, final Environment environment) throws Exception {
        environment.jersey().packages("by.auto360.resources");
    }

    @Override
    public void initialize(final Bootstrap<AutoConfiguration> bootstrap) {
        // serve static files
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }
}
