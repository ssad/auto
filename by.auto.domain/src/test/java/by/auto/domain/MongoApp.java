package by.auto.domain;

import by.auto.domain.common.enums.AccountStatus;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import by.auto.domain.common.Account;
import by.auto.domain.common.enums.RoleName;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoApp {
    public static final Logger LOGGER = LoggerFactory.getLogger(MongoApp.class);

    public static void main(final String[] args) throws Exception {
        final MongoClientOptions options = MongoClientOptions.builder().writeConcern(WriteConcern.SAFE).build();
        final MongoOperations mongoOps = new MongoTemplate(new MongoClient("localhost", options), "auto");

        mongoOps.insert(new Account("test", "password", RoleName.User, AccountStatus.Active));

        LOGGER.info(mongoOps.findOne(new Query(where("login").is("test")), Account.class).toString());
    }
}
