package by.auto.persistence.repository.mongo.converters;

import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.common.enums.RoleName;
import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.*;

/**
 * Created by Siarhei_Sadouski on 6/24/2015.
 */
@ReadingConverter
public class OAuth2AuthenticationReadConverter implements Converter<DBObject, OAuth2Authentication> {

    @Override
    public OAuth2Authentication convert(DBObject source) {
        DBObject storedRequest = (DBObject) source.get("storedRequest");
        OAuth2Request oAuth2Request = new OAuth2Request((Map<String, String>) storedRequest.get("requestParameters"),
                (String) storedRequest.get("clientId"), null, true, new HashSet((List) storedRequest.get("scope")),
                null, null, null, null);
        DBObject userAuthorization = (DBObject) source.get("userAuthentication");
        Object principal = getPrincipalObject(userAuthorization.get("principal"));
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(principal,
                userAuthorization.get("credentials"), getAuthorities((List) userAuthorization.get("authorities")));
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request,
                userAuthentication);
        return authentication;
    }

    private Object getPrincipalObject(Object principal) {
        if (principal instanceof DBObject) {
            DBObject principalDBObject = (DBObject) principal;
            Account account = new Account((String) principalDBObject.get("login"), (String) principalDBObject.get("password"), RoleName.valueOf((String) principalDBObject.get("role")), AccountStatus.valueOf((String) principalDBObject.get("status")));
            account.setId((String) principalDBObject.get("_id"));
            account.setLastModifiedBy((String) principalDBObject.get("lastModifiedBy"));
            account.setCreatedBy((String) principalDBObject.get("createdBy"));
            account.setLastModifiedDate(DateTime.parse((String) principalDBObject.get("lastModifiedDate")));
            account.setCreatedDate(DateTime.parse((String) principalDBObject.get("createdDate")));
            return account;
        } else {
            return principal;
        }
    }

    private Collection<GrantedAuthority> getAuthorities(List<Map<String, String>> authorities) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet(authorities.size());
        for (Map<String, String> authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("role")));
        }
        return grantedAuthorities;
    }

}