package by.auto.service.messaging;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import by.auto.domain.common.Account;
import by.auto.domain.testmothers.AccountMother;
import by.auto.service.email.Email;
import by.auto.service.email.HtmlEmail;
import by.auto.service.email.TemplateEmail;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ContextConfiguration(locations = {"classpath:/META-INF/config/mongo-test-config.xml", "classpath:/META-INF/config/test/services-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class JmsMessagingIT {
    @Inject
    private ApplicationContext applicationContext;

    @Test
    public void doSendAndReceive() {
        final MessageChannel stdinToJmsOutChannel = applicationContext.getBean("emailOutChannel", MessageChannel.class);

        final Email email = getTemplateEmail("simpleEmail.ftl");
        stdinToJmsOutChannel.send(MessageBuilder.withPayload(email).build());

        final QueueChannel queueChannel = applicationContext.getBean("queueChannel", QueueChannel.class);

        @SuppressWarnings("unchecked")
        Message<Email> reply = (Message<Email>) queueChannel.receive(20000);
        Assert.assertNotNull(reply);
        TemplateEmail out = (TemplateEmail) reply.getPayload();
        Assert.assertEquals("simpleEmail.ftl", out.getTemplateFileName());
        ((AbstractApplicationContext) applicationContext).close();
    }

    private Email getTemplateEmail(final String templateName) {
        final HtmlEmail email = new HtmlEmail();
        email.setTemplateFileName(templateName);
        final Map<String, Object> context = new HashMap<>();
        context.put("to", "Serg");
        context.put("body", "test body");
        context.put("from", "Admin");
        context.put("args", new Object[]{1});
        email.setContext(context);
        email.setLocale(new Locale("en"));
        email.setSubject("email.test.subject");
        Account account = AccountMother.createUserAccount();
        email.setReceiver(account);
        email.setReceiverString("test@auto.by");
        email.setSender("admin@tut.by");

        return email;
    }

}
