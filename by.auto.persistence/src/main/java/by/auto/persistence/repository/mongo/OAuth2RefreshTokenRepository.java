package by.auto.persistence.repository.mongo;

import by.auto.domain.common.OAuth2AuthenticationRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Siarhei_Sadouski on 6/24/2015.
 */
public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuth2AuthenticationRefreshToken, String> {
    public OAuth2AuthenticationRefreshToken findByTokenId(String tokenId);
}
