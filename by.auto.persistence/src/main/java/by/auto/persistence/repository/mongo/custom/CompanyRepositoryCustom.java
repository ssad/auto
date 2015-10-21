package by.auto.persistence.repository.mongo.custom;

import by.auto.domain.common.Company;

public interface CompanyRepositoryCustom {
    public Company findByAccountId(final String accountId);
}
