package by.auto.service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import by.auto.service.email.JavaMailSenderWrapper;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;


@MessageEndpoint
public class JmsMessageListener {

    @Inject
    private JavaMailSenderWrapper javaMailSenderWrapper;

    public static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageListener.class);

    @ServiceActivator
    public void onMessage(Message<byte[]> message) {
        try {
            javaMailSenderWrapper.send(javaMailSenderWrapper.createMimeMessage(new ByteArrayInputStream(message.getPayload())));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
