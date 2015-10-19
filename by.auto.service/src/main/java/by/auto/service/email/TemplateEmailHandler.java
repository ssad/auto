package by.auto.service.email;

import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import by.auto.service.template.FreemarkerTemplateProcessor;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

@Service
public class TemplateEmailHandler extends EmailHandler<TemplateEmail> {

    @Inject
    private FreemarkerTemplateProcessor templateProcessor;

    @Inject
    private MessageSource templateMessageSource;

    @Override
    public void applyContentAndSubject(final TemplateEmail email, final MimeMessageHelper message) throws MessagingException {
        final MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(email.getGeneratedContent());
        message.getMimeMultipart().addBodyPart(mbp);
        message.setSubject(email.getGeneratedSubject());
    }

    @Override
    protected String generateContent(final TemplateEmail email) {
        return templateProcessor.getMessage(email.getTemplateFileName(), email.getContext(), email.getLocale());
    }

    @Override
    protected String generateSubject(final TemplateEmail email) {
        return templateMessageSource.getMessage(email.getSubjectKey(), email.getSubjectArgs(), email.getLocale());
    }

    @Override
    public void postSent(final TemplateEmail email) {
        // empty method
    }

    @Override
    public void logDetails(final TemplateEmail email) {
        // log details
    }

}

