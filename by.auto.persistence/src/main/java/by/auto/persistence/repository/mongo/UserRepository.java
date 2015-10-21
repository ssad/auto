package by.auto.persistence.repository.mongo;

import by.auto.domain.common.User;
import by.auto.persistence.repository.mongo.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<User, String>, UserRepositoryCustom {

}
