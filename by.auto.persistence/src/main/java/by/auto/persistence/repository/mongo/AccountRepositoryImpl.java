package by.auto.persistence.repository.mongo;

import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.persistence.repository.mongo.custom.AccountRepositoryCustom;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.inject.Inject;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class AccountRepositoryImpl implements AccountRepositoryCustom {
    @Inject
    private MongoOperations mongoTemplate;

    @Override
    public Account findByLogin(final String login) {
        return mongoTemplate.findOne(query(where("login").is(login)), Account.class);
    }

    @Override
    public Account findByLoginActive(final String login) {
        return mongoTemplate.findOne(query(where("login").is(login).and("status").is(AccountStatus.Active)), Account.class);
    }
}