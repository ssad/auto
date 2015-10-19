package by.auto.service;

import by.auto.domain.common.Account;
import by.auto.domain.common.GeneratedToken;
import by.auto.domain.common.enums.AccountStatus;
import by.auto.domain.common.enums.TokenType;
import by.auto.persistence.repository.mongo.AccountRepository;
import by.auto.persistence.repository.mongo.GeneratedTokenRepository;
import by.auto.service.email.EmailSender;
import by.auto.service.email.EmailType;
import by.auto.service.email.HtmlEmail;
import by.auto.service.exception.AccountAlreadyExistsException;
import by.auto.service.exception.ForgotAccountException;
import by.auto.service.util.ObjectService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    @Value("${adminEmail}")
    private String ADMIN_EMAIL;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    private final static String REGISTRATION_EMAIL_TEMPLATE = "registrationTemplate.ftl";
    private final static String REGISTRATION_EMAIL_SUBJECT = "email.registration.subject";
    private final static String TOKEN_EMAIL_TEMPLATE = "tokenTemplate.ftl";
    private final static String TOKEN_EMAIL_SUBJECT = "email.token.subject";
    private final static String REGISTRATION_COMPLETE_EMAIL_TEMPLATE = "registrationCompleteTemplate.ftl";
    private final static String REGISTRATION_COMPLETE_EMAIL_SUBJECT = "email.registration.complete.subject";

    @Inject
    AccountRepository accountRepository;

    @Inject
    private GeneratedTokenRepository generatedTokenRepository;

    @Inject
    private EmailSender emailSender;

    @Inject
    private ObjectService objectService;

    /**
     * Retrieves a {@link by.auto.domain.common.Account account} by it id.
     *
     * @param id an account id
     * @return the {@link by.auto.domain.common.Account account} with the given id or null if none found
     */
    public Account findById(final String id) {
        return accountRepository.findOne(id);
    }

    /**
     * Saves a given {@link by.auto.domain.common.Account account}
     *
     * @param account a account instance to save
     * @return the saved {@link by.auto.domain.common.Account account}
     */
    @PreAuthorize("#account.id == principal.id or hasRole('Admin')")
    public Account update(final Account account) {
        final Account saved = accountRepository.save(account);
        return saved;
    }

    public void generatePasswordToken(final String login, TokenType tokenType) throws ForgotAccountException {
        final Account forgottenAccount = findByLogin(login);
        if (forgottenAccount == null || (forgottenAccount != null && forgottenAccount.getStatus() != AccountStatus.Active)) {
            throw new ForgotAccountException("Could not find account with login " + login);
        }
        generatePasswordToken(forgottenAccount, tokenType);
    }

    private void generatePasswordToken(final Account account, TokenType tokenType) throws ForgotAccountException {
        final GeneratedToken generatedToken = new GeneratedToken(account.getId(), tokenType);
        generatedTokenRepository.save(generatedToken);
        String emailTemplate = null;
        String subject = null;
        HashMap<String, Object> context = new HashMap<String, Object>() {{
            put("token", generatedToken.getChangeToken());
//            put("to", account.getName());
        }};
        switch (tokenType) {
            case ForgotPassword:
                emailTemplate = TOKEN_EMAIL_TEMPLATE;
                subject = TOKEN_EMAIL_SUBJECT;
                break;
            case Register:
                emailTemplate = REGISTRATION_EMAIL_TEMPLATE;
                subject = REGISTRATION_EMAIL_SUBJECT;
                break;
        }
        sendEmail(account, subject, null, emailTemplate, context, false, null, EmailSender.Action.Send);
    }

    public void create(Account account) {
        final Account persisted = findByLogin(account.getLogin());
        if (persisted != null && persisted.getStatus() == AccountStatus.Pending) {
            accountRepository.delete(persisted);
        }
        try {
            account.setStatus(AccountStatus.Pending);
            account = accountRepository.save(account);
        } catch (final DuplicateKeyException ex) {
            throw new AccountAlreadyExistsException(ex);
        }

        try {
            generatePasswordToken(account, TokenType.Register);
        } catch (final MailException me) {
            accountRepository.delete(account.getId());
            throw me;
        }
    }

    /**
     * Активирует аккаунт по токену и удаляет токен
     *
     * @param token
     * @return the activated {@link by.auto.domain.common.Account account} by token
     * @throws ForgotAccountException if token expired or non existed account
     */
    public Account activate(final String token) throws ForgotAccountException {
        final Account accountToUpdate = findByGeneratedToken(token);
        final GeneratedToken generatedToken = generatedTokenRepository.findByChangeToken(token);
        if (accountToUpdate != null &&
                generatedToken != null &&
                accountToUpdate.getStatus() == AccountStatus.Pending &&
                generatedToken.getType() == TokenType.Register) {
            return activate(accountToUpdate, generatedToken, true);
        } else {
            throw new ForgotAccountException("Token expired or non existed account in Auto");
        }
    }

    private Account activate(final Account accountToUpdate, final GeneratedToken generatedToken, boolean registration) {
        accountToUpdate.setStatus(AccountStatus.Active);
        update(accountToUpdate);
        generatedTokenRepository.delete(generatedToken);
        if (registration) {
            HashMap<String, Object> context = new HashMap<String, Object>() {{
//                put("to", accountToUpdate.getName());
                put("account", accountToUpdate);
            }};
            sendEmail(accountToUpdate, REGISTRATION_COMPLETE_EMAIL_SUBJECT, null, REGISTRATION_COMPLETE_EMAIL_TEMPLATE, context, false, null, EmailSender.Action.AsyncSend);
        }
        return accountToUpdate;
    }

    /**
     * Возвращает аккаунт по токену
     *
     * @param generatedToken
     * @return a {@link by.auto.domain.common.Account account} by token or null if token not found
     */
    public Account findByGeneratedToken(final String generatedToken) {
        GeneratedToken token = generatedTokenRepository.findByChangeToken(generatedToken);
        return findByGeneratedToken(token);
    }

    /**
     * Возвращает аккаунт по токену
     *
     * @param token
     * @return a {@link by.auto.domain.common.Account account} by token or null if token not found
     */
    protected Account findByGeneratedToken(final GeneratedToken token) {
        if (token != null) {
            return findById(token.getAccountId());
        } else {
            return null;
        }
    }

    private void sendEmail(final Account receiver,
                           final String subjectTemplate,
                           final Object[] subjectTemplateArgs,
                           final String bodyTemplate,
                           final Map<String, Object> context,
                           final boolean unsolicited,
                           final EmailType type,
                           final EmailSender.Action emailAction) {
        final HtmlEmail email = new HtmlEmail();
        email.setReceiver(receiver);
        email.setReceiverString(objectService.getEmail(receiver));
        sendEmail(email, subjectTemplate, subjectTemplateArgs, bodyTemplate, context, unsolicited, type, emailAction);
    }

    private void sendEmail(final HtmlEmail email,
                           final String subjectTemplate,
                           final Object[] subjectTemplateArgs,
                           final String bodyTemplate,
                           final Map<String, Object> context,
                           final boolean unsolicited,
                           final EmailType type,
                           final EmailSender.Action emailAction) {
        email.setTemplateFileName(bodyTemplate);
        email.setSubject(subjectTemplate, subjectTemplateArgs);
        email.setContext(context);
        email.setUnsolicited(unsolicited);
        email.setSender(ADMIN_EMAIL);
        email.setType(type);

        if (emailAction == EmailSender.Action.AsyncSend) {
            emailSender.sendJmsMessage(email);
            LOGGER.info("Put jms message to queue for " + (email.getReceiver() != null ? email.getReceiver() : email.getReceiverString()));
        } else if (emailAction == EmailSender.Action.Send) {
            emailSender.send(email);
            LOGGER.info("Sync Email send to " + (email.getReceiver() != null ? email.getReceiver() : email.getReceiverString()));
        } else if (emailAction == EmailSender.Action.AddToBulk) {
            emailSender.addToEmailBulkAndSend(email);
        }
    }

    public Account findByLogin(final String login) {
        return accountRepository.findByLogin(login);
    }
}