package by.auto.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import java.io.IOException;

public abstract class EmailHandler<T extends Email> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailHandler.class);

    public abstract void applyContentAndSubject(T email, MimeMessageHelper message)
            throws MessagingException, IOException;

    protected void logError(final T email, final Exception e) {
        final StringBuffer errorMsg = new StringBuffer();
        errorMsg.append("Email could not be sent out to: ");
        errorMsg.append(email.getReceiver());
        errorMsg.append("\nType of email is: ");
        errorMsg.append(email.getClass().getName());

        LOGGER.error(errorMsg.toString(), e);
        logDetails(email);
    }

    public abstract void postSent(T email);

    protected abstract String generateContent(T email);

    protected abstract String generateSubject(T email);

    public abstract void logDetails(T email);
}
