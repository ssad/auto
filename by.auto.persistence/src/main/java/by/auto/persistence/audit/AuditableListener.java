package by.auto.persistence.audit;

import by.auto.domain.audit.AuditableDomainObject;
import by.auto.util.AccountUtils;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import by.auto.domain.common.Account;

/**
 * This class is used to set auditable information to find out who has changed document in mongo.
 */
public class AuditableListener extends AbstractMongoEventListener<AuditableDomainObject> {

    private String defaultLogin;

    @Override
    public void onBeforeConvert(final AuditableDomainObject auditable) {
        final DateTime auditableDate = DateTime.now();

        String accountLogin;

        accountLogin = (defaultLogin != null) ? defaultLogin : AccountUtils.getCurrentLogin();

        if (auditable.isNew()) {
            if (auditable instanceof Account) {
                accountLogin = ((Account) auditable).getLogin();
            }
            auditable.setCreatedBy(accountLogin);
            auditable.setCreatedDate(auditableDate);
        }

        // always set last modified attributes, even when the object is new
        // it simplifies the query to get the latest updated objects, do not need to distinguish between
        // createdDate and lastModifiedDate, always can sort or query for lastModifiedDate
        auditable.setLastModifiedBy(accountLogin);
        auditable.setLastModifiedDate(auditableDate);

        super.onBeforeConvert(auditable);
    }

    public void setDefaultLogin(final String defaultLogin) {
        this.defaultLogin = defaultLogin;
    }

}
