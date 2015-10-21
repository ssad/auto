package by.auto.persistence.repository.mongo;

import by.auto.persistence.repository.mongo.custom.AccountRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import by.auto.domain.common.Account;

public interface AccountRepository extends MongoRepository<Account, String>, AccountRepositoryCustom {

}
