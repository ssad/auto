package by.auto.service.email;

import org.springframework.stereotype.Service;
import by.auto.domain.common.Account;
import by.auto.domain.common.enums.AccountStatus;

@Service
public class EmailSenderInterceptor {
    /**
     * Determines if the invoking object should continue and send the incoming
     * email. The current behavior for this class is to first determine if the
     * incoming email is solicited or not. If so, this method returns true.
     * Otherwise, this method will then execute logic to check is allowed to
     * receive email communications from system.
     *
     * @param email
     * @return True if it is OK to send out the email to the receiver. False,
     * otherwise.
     */
    public boolean proceed(final Email email) {
        if (email.isUnsolicited()) {
            if (email.getReceiver() != null) {
                final Account account = email.getReceiver();
                if (account.getStatus() != AccountStatus.Active) {
                    return false;
                }
                if (email.getType() != null) {
                    switch (email.getType()) {
                        case Message:
                            return true; //todo здесь необходимо ли отправлять письмо
                    }
                }
            }
            return true;
        } else {
            return true;
        }
    }

}
