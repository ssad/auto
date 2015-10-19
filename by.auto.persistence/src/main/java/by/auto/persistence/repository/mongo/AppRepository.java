package by.auto.persistence.repository.mongo;

import by.auto.domain.common.App;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppRepository extends MongoRepository<App, String> {
    public App findByApiKey(String apiKey);
}
