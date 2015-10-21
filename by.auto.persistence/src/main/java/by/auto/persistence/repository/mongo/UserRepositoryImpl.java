package by.auto.persistence.repository.mongo;

import by.auto.domain.common.User;
import by.auto.persistence.repository.mongo.custom.UserRepositoryCustom;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.inject.Inject;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class UserRepositoryImpl implements UserRepositoryCustom {
    @Inject
    private MongoOperations mongoTemplate;

    @Override
    public User findByAccountId(final String accountId) {
        return mongoTemplate.findOne(query(where("accountId").is(accountId)), User.class);
    }
}