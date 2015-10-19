package by.auto.service.messaging;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

@Service
public class JmsMessageSender {
    @Inject
    private MessageChannel emailOutChannel;

    public boolean sendEmailMessage(MimeMessage mimeMessage) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(outputStream);
            return emailOutChannel.send(MessageBuilder.withPayload(outputStream.toByteArray()).build());

            //emailOutChannel.send(MessageBuilder.withPayload(email).build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
