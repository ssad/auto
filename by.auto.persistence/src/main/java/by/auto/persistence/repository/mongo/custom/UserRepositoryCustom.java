package by.auto.persistence.repository.mongo.custom;

import by.auto.domain.common.User;

public interface UserRepositoryCustom {
    public User findByAccountId(final String accountId);
}
