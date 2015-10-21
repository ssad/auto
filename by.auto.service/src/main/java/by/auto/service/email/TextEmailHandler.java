package by.auto.service.email;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

@Service
public class TextEmailHandler extends EmailHandler<TextEmail> {

    @Override
    public void applyContentAndSubject(final TextEmail email, final MimeMessageHelper message) throws MessagingException, IOException {
        final MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(email.getTextMessage());
        message.getMimeMultipart().addBodyPart(mbp);
        message.setSubject(email.getSubject());
    }

    @Override
    public void postSent(final TextEmail email) {
        // Empty method
    }

    @Override
    protected String generateContent(final TextEmail email) {
        return email.getTextMessage();
    }

    @Override
    protected String generateSubject(final TextEmail email) {
        return email.getSubject();
    }

    @Override
    public void logDetails(final TextEmail email) {
        // Empty method
    }
}
