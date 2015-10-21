package by.auto.persistence.repository.mongo.custom;

import by.auto.domain.common.Account;

public interface AccountRepositoryCustom {
    public Account findByLogin(final String login);

    public Account findByLoginActive(final String login);
}