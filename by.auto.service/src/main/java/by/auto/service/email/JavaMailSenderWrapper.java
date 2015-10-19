package by.auto.service.email;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.List;

@Service
public class JavaMailSenderWrapper {

    @Inject
    private JavaMailSender mailSender;

    public MimeMessage createMimeMessage() {
        return mailSender.createMimeMessage();
    }

    public MimeMessage createMimeMessage(InputStream inputStream) {
        return mailSender.createMimeMessage(inputStream);
    }

    @Async
    public void sendAsync(final MimeMessage message) throws MailException {
        mailSender.send(message);
    }

    public void sendBulk(final List<MimeMessage> mimeMessages) throws MailException {
        MimeMessage[] messages = new MimeMessage[mimeMessages.size()];
        mailSender.send(mimeMessages.toArray(messages));
    }

    public void send(final MimeMessage message) throws MailException {
        mailSender.send(message);
    }
}
