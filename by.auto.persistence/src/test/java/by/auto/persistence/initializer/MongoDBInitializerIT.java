package by.auto.persistence.initializer;

import by.auto.persistence.initializer.MongoDBInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/config/mongo-test-config.xml")
public class MongoDBInitializerIT {
    @Inject
    private MongoDBInitializer mongoDBInitializer;

    @Test
    public void shouldExecuteScripts() {
    }
}
