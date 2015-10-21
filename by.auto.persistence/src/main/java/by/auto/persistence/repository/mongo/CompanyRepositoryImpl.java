package by.auto.persistence.repository.mongo;

import by.auto.domain.common.Company;
import by.auto.persistence.repository.mongo.custom.CompanyRepositoryCustom;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.inject.Inject;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom {
    @Inject
    private MongoOperations mongoTemplate;

    @Override
    public Company findByAccountId(final String accountId) {
        return mongoTemplate.findOne(query(where("accountId").is(accountId)), Company.class);
    }
}
