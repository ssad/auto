package by.auto.persistence.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import by.auto.domain.common.GeneratedToken;

public interface GeneratedTokenRepository extends MongoRepository<GeneratedToken, String> {
    public GeneratedToken findByChangeToken(String changeToken);
}
