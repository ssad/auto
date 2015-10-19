package by.auto.persistence.repository.mongo;

import by.auto.domain.common.Company;
import by.auto.persistence.repository.mongo.custom.CompanyRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository  extends MongoRepository<Company, String>, CompanyRepositoryCustom {

}
