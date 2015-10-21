package by.auto.persistence.initializer;

import com.googlecode.flyway.core.util.ClassPathResource;
import com.googlecode.flyway.core.util.scanner.ClassPathScanner;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

public class MongoDBInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBInitializer.class);

    private static final String SCHEMA_VERSION_COLLECTION_NAME = "schema_version";

    private static final String VERSION_FIELD = "version";

    @Inject
    private MongoTemplate mongoTemplate;

    private List<String> locations;

    private String collection;

    private String initialVersion;

    public MongoDBInitializer() {
        collection = SCHEMA_VERSION_COLLECTION_NAME;
    }

    public void setLocations(final List<String> locations) {
        this.locations = locations;
    }

    public void setCollection(final String collection) {
        this.collection = collection;
    }

    public void setInitialVersion(final String initialVersion) {
        this.initialVersion = initialVersion;
    }

    @PostConstruct
    protected void initialize() throws MongoDBInitializerException {
        LOGGER.info("Initialize MongoDB:");

        for (final String location : locations) {
            try {
                final com.googlecode.flyway.core.util.ClassPathResource[] resources =
                        new ClassPathScanner().scanForResources(location, "", ".js");

                execute(resources);

                if (resources.length != 0) {
                    updateVersion(getScriptVersion(resources[resources.length - 1]));
                }
            } catch (final IOException ex) {
                throw new MongoDBInitializerException("Unable to read the content of " + location + ".");
            }
        }
    }

    private void updateVersion(final String lastVersion) {
        LOGGER.info("Update {} collection, set {} into {}.", collection, VERSION_FIELD, lastVersion);

        final DBCollection versionCollection = mongoTemplate.getDb().getCollection(collection);
        versionCollection.update(new BasicDBObject(), new BasicDBObject(VERSION_FIELD, lastVersion), true, false);
    }

    private void execute(final ClassPathResource[] scripts) throws MongoDBInitializerException {
        final String currentVersion = getCurrentVersion();
        for (final ClassPathResource script : scripts) {
            if (isNewScript(script, currentVersion)) {
                LOGGER.info("Running {}", script.getFilename());
                final String commands = script.loadAsString("UTF-8");
                final CommandResult result = mongoTemplate.getDb().doEval(commands);
                if (!result.ok()) {
                    throw new MongoDBInitializerException("Failed to eval the following commands: " + commands + ". Error messages: " + result.getErrorMessage(), result.getException());
                }
            }
        }
        LOGGER.info("All scripts are processed.");
    }

    private String getCurrentVersion() {
        final DBCollection versionCollection = mongoTemplate.getDb().getCollection(collection);
        final DBObject currentVersion = versionCollection.findOne();
        if (currentVersion == null) {
            return initialVersion;
        } else {
            return (String) currentVersion.get(VERSION_FIELD);
        }
    }

    private boolean isNewScript(final ClassPathResource script, final String currentVersion) {
        final String scriptVersion = getScriptVersion(script);

        return StringUtils.isEmpty(currentVersion) || scriptVersion.compareTo(currentVersion) > 0;
    }

    private String getScriptVersion(final ClassPathResource resource) {
        return StringUtils.substringBefore(resource.getFilename(), "__");
    }
}
