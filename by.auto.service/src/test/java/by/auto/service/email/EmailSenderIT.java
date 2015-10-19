package by.auto.service.email;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;
import by.auto.domain.common.Account;
import by.auto.domain.testmothers.AccountMother;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(locations = {"classpath:/META-INF/config/mongo-test-config.xml", "classpath:/META-INF/config/test/services-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailSenderIT {

    @Inject
    private EmailSender emailSender;

    private Wiser wiser;

    @Value("${mail.user}")
    private String sender;

    @Before
    public void setUp() {
        wiser = new Wiser(2500);
        wiser.start();
        wiser.getMessages().clear();
    }

    @Test
    public void doSendTextEmail() throws Exception {
        final Email email = getTextEmail();

        emailSender.send(email);
        assertThat(wiser.getMessages().size(), is(1));
    }

    @Test
    public void doSendTemplateEmail() throws Exception {
        final Email email = getTemplateEmail("simpleEmail.ftl");

        emailSender.send(email);
        assertThat(wiser.getMessages().size(), is(1));
    }

    @Test(expected = MailSendException.class)
    public void doThrowExceptionUsingWrongTemplate() throws Exception {
        final TemplateEmail email = (TemplateEmail) getTemplateEmail("foo");
        emailSender.send(email);
    }

    @After
    public void after() {
        wiser.stop();
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
        setCommonEmailContext(email);

        return email;
    }

    private Email getTextEmail() {
        final TextEmail email = new TextEmail();
        email.setSubject("Foo Subject");
        email.setTextMessage("Hi All, this is test");
        setCommonEmailContext(email);
        return email;
    }

    private void setCommonEmailContext(final Email email) {
        Account account = AccountMother.createUserAccount();
        email.setReceiver(account);
        email.setReceiverString("test@auto.by");
        email.setSender("admin@tut.by");
    }

    @Test
    public void shouldSendHtmlEmail() throws MessagingException {
        final HtmlEmail email = (HtmlEmail) getTemplateEmail("simpleEmail.ftl");
        emailSender.send(email);
        assertThat(wiser.getMessages().size(), is(1));
    }

    @Test
    public void shouldSendBulk() throws MessagingException {
        for (int i = 0; i < 999; i++ ) {
            final HtmlEmail email = (HtmlEmail) getTemplateEmail("simpleEmail.ftl");
            emailSender.addToEmailBulkAndSend(email);
        }
        assertThat(wiser.getMessages().size(), is(0));

        for (int i = 0; i < 1500; i++ ) {
            final HtmlEmail email = (HtmlEmail) getTemplateEmail("simpleEmail.ftl");
            emailSender.addToEmailBulkAndSend(email);
        }
        assertThat(wiser.getMessages().size(), is(2000));
        emailSender.flushEmailBulk();
        assertThat(wiser.getMessages().size(), is(2499));
    }

}