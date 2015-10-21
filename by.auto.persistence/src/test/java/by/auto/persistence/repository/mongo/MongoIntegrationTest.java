package by.auto.persistence.repository.mongo;

import com.googlecode.flyway.core.util.ClassPathResource;
import com.googlecode.flyway.core.util.scanner.ClassPathScanner;
import com.mongodb.CommandResult;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import by.auto.domain.common.DomainObject;
import by.auto.domain.exception.ApplicationException;
import by.auto.util.MongoUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@ContextConfiguration(locations = "classpath:/META-INF/config/mongo-test-config.xml")
@ActiveProfiles("test")
public abstract class MongoIntegrationTest<T extends DomainObject> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoIntegrationTest.class);

    private final Class<T> clazz;

    private final List<String> dataAlias = new ArrayList<>();

    @Inject
    protected ApplicationContext context;


    @SuppressWarnings("unchecked")
    protected MongoIntegrationTest() {
        this.clazz = (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), MongoIntegrationTest.class);

        dataAlias.add("core");
        dataAlias.addAll(asList(dependsOnData()));
        if (hasDocumentAnnotation()) {
            dataAlias.add(MongoUtils.getCollectionName(clazz));
        }
    }

    @Inject
    protected MongoTemplate mongoTemplate;

    /**
     * Can be used to configure test data this test depends on.
     * "core" is always included, you don't need to specify it.
     * Sample implementation can be:
     * <pre>
     * protected String[] dependsOnData() {
     *     return new String[] {"country"};
     * }
     *
     * @return data alias, ex.: new String[] {"productCategory", "country"}.
     * </pre>
     * @see "META-INF/test-data"
     */
    protected String[] dependsOnData() {
        return new String[]{};
    }

    @Before
    public void _setUp() throws Exception {
        processTestData(dataAlias, TestPhase.SetUp);
    }

    @After
    public void _tearDown() throws Exception {
        processTestData(dataAlias, TestPhase.TearDown);
    }

    private void processTestData(final List<String> dataAlias, final TestPhase testPhase) throws Exception {
        final ClassPathResource[] resources =
                new ClassPathScanner().scanForResources("META-INF/test-data", "", testPhase.name().toLowerCase() + ".js");
        for (final String alias : dataAlias) {
            for (final ClassPathResource resource : resources) {
                if (resource.getFilename().startsWith(alias)) {
                    execute(resource);
                }
            }
        }
    }

    private void execute(final ClassPathResource resource) {
        final String commands = resource.loadAsString("UTF-8");
        final CommandResult result = mongoTemplate.getDb().doEval(commands);
        if (!result.ok()) {
            throw new ApplicationException("Failed to eval the following commands: " + commands + " from " + resource.getFilename() + ". Error message: " + result.getErrorMessage(), result.getException());
        }
    }

    private boolean hasDocumentAnnotation() {
        final boolean present = clazz.isAnnotationPresent(Document.class);
        if (!present) {
            LOGGER.warn("You are trying to execute IT on the entity which is not annotated with @Document.");
        }
        return present;
    }

    private static enum TestPhase {
        SetUp, TearDown
    }
}
