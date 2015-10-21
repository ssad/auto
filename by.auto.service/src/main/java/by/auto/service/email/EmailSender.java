package by.auto.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import by.auto.service.messaging.JmsMessageSender;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
/**
 * @author Siarhei Sadouski (contact him to make changes here)

 */
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Value("${mail.test.receiver}")
    private String TEST_EMAIL_ADDRESS;

    @Value("${mail.from.support.email}")
    private String FROM_EMAIL;

    @Value("${mail.from.support.name}")
    private String FROM_NAME;

    @Value("${mail.bulk.size}")
    private long bulkSize;

    @Inject
    private JmsMessageSender jmsMessageSender;

    @Inject
    private JavaMailSenderWrapper mailSenderWrapper;

    @Value("${mail.user}")
    private String senderAddress;

    @Inject
    private EmailSenderInterceptor interceptor;

    @Inject
    private ApplicationContext appContext;

    @Inject
    private Environment environment;

    private List<MimeMessage> mimeMessages = new ArrayList<>();

    public enum Action {
        Send, AsyncSend, AddToBulk, AddToQueue
    }

    public Email sendAsync(final Email email) {
        return send(email, Action.AsyncSend);
    }

    public Email sendJmsMessage(final Email email) {
        return send(email, Action.AddToQueue);
    }

    public Email send(final Email email) throws MailException {
        return send(email, Action.Send);
    }

    public void addToEmailBulkAndSend(final Email email) {
        send(email, Action.AddToBulk);
        if (mimeMessages.size() == bulkSize) {
            LOGGER.info("Try to send " + mimeMessages.size() + " emails");
            mailSenderWrapper.sendBulk(mimeMessages);
            mimeMessages = new ArrayList<>();
        }
    }

    public void flushEmailBulk() {
        LOGGER.info("Try to send " + mimeMessages.size() + " emails");
        mailSenderWrapper.sendBulk(mimeMessages);
        mimeMessages = new ArrayList<>();
    }

    private Email send(final Email email, final Action action) throws MailException {
        final EmailHandler<Email> emailHandler = getEmailHandler(email);
        try {
            final MimeMessageHelper message = createMimeMessage(email);
            email.setGeneratedSubject(emailHandler.generateSubject(email));
            email.setGeneratedContent(emailHandler.generateContent(email));
            emailHandler.applyContentAndSubject(email, message);

            // Determine if email should be sent out or discarded
            if (interceptor.proceed(email)) {
                addAttachmentsToMessage(email, message);
                message.setFrom(FROM_EMAIL, FROM_NAME);
                if (!isProduction()) {
                    message.setSubject("Original receiver: " + email.getReceiver() + " --- Original subject: " + message.getMimeMessage().getSubject());
                }
                if (action == Action.AsyncSend) {
                    mailSenderWrapper.sendAsync(message.getMimeMessage());
                } else if (action == Action.AddToQueue) {
                    jmsMessageSender.sendEmailMessage(message.getMimeMessage());
                }
                else if (action == Action.Send) {
                    try {
                        mailSenderWrapper.send(message.getMimeMessage());
                        LOGGER.info("Email was successfully sent to SMTP server.");
                    } catch (final MailException me) {
                        LOGGER.error("Email cannot be sent out. Cannot process email object received.");
                        throw me;
                    }
                } else if (action == Action.AddToBulk) {
                    mimeMessages.add(message.getMimeMessage());
                }
            }
            // Method postSent() should be called only after a
            // successfull call to sendMessage() or if message is NOT to be sent
            // out
            emailHandler.postSent(email);
            return email;
        } catch (final Exception e) {
            emailHandler.logError(email, e);
            if (e instanceof MailException) {
                throw (MailException) e;
            } else {
                throw new MailSendException(
                        "EmailSender failed sending email.", e);
            }
        }

    }

    private MimeMessageHelper createMimeMessage(final Email email)
            throws MessagingException {

        final MimeMessageHelper message = new MimeMessageHelper(mailSenderWrapper
                .createMimeMessage(), true);
        final String receiverAddress;
        if (!StringUtils.isEmpty(email.getReceiverString())) {
            receiverAddress = email.getReceiverString();
        } else {
            throw new MailSendException("Email has no receiver");
        }
        if (isProduction()) {
            addCcListToMessage(email, message);
            message.setTo(receiverAddress);
        } else {
            message.setTo(TEST_EMAIL_ADDRESS);
        }
        message.setFrom(senderAddress);
        return message;
    }

    private boolean isProduction() {
        return environment != null && environment.acceptsProfiles("prod");
    }

    private EmailHandler<Email> getEmailHandler(final Email email) {
        try {
            final Class<EmailHandler<Email>> cls = (Class<EmailHandler<Email>>) email.getClass().getAnnotation(HandleEmail.class).className();
            return appContext.getBean(cls);
        } catch (final Exception e) {
            throw new MailSendException("Could not find mail handler", e);
        }

    }

    private void addCcListToMessage(final Email email, final MimeMessageHelper message)
            throws MessagingException {
        final Collection<String> ccList = email.getCcAddresses();
        if (ccList != null) {
            for (final String address : ccList) {
                message.addCc(address);
            }
        }
    }

    private void addAttachmentsToMessage(final Email email, final MimeMessageHelper message)
            throws MessagingException {

        final List<AttachmentFile> attachmentFiles = email.getAttachmentFiles();
        if (attachmentFiles != null) {
            for (final AttachmentFile attachmentFile : attachmentFiles) {
                if (attachmentFile != null) {
                    if (attachmentFile.getContent() != null) {
                        message.addAttachment(attachmentFile.getName(),
                                new ByteArrayDataSource(attachmentFile
                                        .getContent(),
                                        "application/octet-stream"));
                    } else if (attachmentFile.getFileName() != null) {
                        final File file = new File(attachmentFile.getFileName());
                        message.addAttachment(attachmentFile.getName(), file);
                    } else {
                        LOGGER.warn("AttachmentFile object did not have content or a file name.");
                    }
                }

            }
        }
    }

    private String parseCCAddrs(final Email email) {

        final Collection<String> ccList = email.getCcAddresses();
        String ccAddrs = null;

        if (ccList != null && !ccList.isEmpty()) {
            ccAddrs = StringUtils.collectionToDelimitedString(ccList, ",");
        }
        return ccAddrs;
    }


}
