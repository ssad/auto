package by.auto.service.email;

import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import by.auto.service.template.FreemarkerTemplateProcessor;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;

@Service
public class HtmlEmailHandler extends EmailHandler<HtmlEmail> {

    @Inject
    private FreemarkerTemplateProcessor templateProcessor;

    @Inject
    private MessageSource templateMessageSource;

    @Override
    public void applyContentAndSubject(final HtmlEmail email, final MimeMessageHelper message) throws MessagingException, IOException {
        final String htmlDocument = email.getGeneratedContent();

        if (email.isSendHtmlAsAttachment()) {
            // Set text part (the HTML portion of this email is sent as
            // an attachment file)
            final MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(templateProcessor.getMessage(
                    email.getTextFileTemplate(), email.getTextFileContext(), email.getLocale()));
            message.getMimeMultipart().addBodyPart(textPart);

            // Set html part as an attachment
            final MimeBodyPart mbp = new MimeBodyPart();

            mbp.setDataHandler(new DataHandler(new ByteArrayDataSource(
                    htmlDocument, "application/octet-stream")));

            mbp.setFileName(email.getTransformedFileName());
            message.getMimeMultipart().addBodyPart(mbp);
        } else {
            // Set HTML part (this email is sent as an HTML email)
            final MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
                    htmlDocument, "text/html")));
            message.getMimeMultipart().addBodyPart(htmlPart);
        }
        message.setSubject(email.getGeneratedSubject());
    }

    @Override
    public void postSent(final HtmlEmail email) {
        //do nothing
    }

    @Override
    protected String generateContent(final HtmlEmail email) {
        return templateProcessor.getMessage(email
                .getTemplateFileName(), email.getContext(), email.getLocale());
    }

    @Override
    protected String generateSubject(final HtmlEmail email) {
        return templateMessageSource.getMessage(email.getSubjectKey(), email.getSubjectArgs(), email.getLocale());
    }

    @Override
    public void logDetails(final HtmlEmail email) {
        //do nothing
    }

}
