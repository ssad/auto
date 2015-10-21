package by.auto.service.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import by.auto.service.template.FreemarkerTemplateProcessor;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Service
public class PdfService {

    @Inject
    private FreemarkerTemplateProcessor templateProcessor;

    @Value("${invoice.logoPath}")
    private String INVOICE_LOGO_PATH;
    @Value("${pdf.fontPath}")
    private String PDF_FONT_PATH;

    private Font font;

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfService.class);

    public ByteArrayOutputStream createPdf(String template, Map<String, Object> context, Locale locale) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            String htmlString = templateProcessor.getMessage(template, context, locale);
            ITextRenderer render = new ITextRenderer();
            render.getSharedContext().setReplacedElementFactory(new MediaReplacedElementFactory(render.getSharedContext().getReplacedElementFactory(), INVOICE_LOGO_PATH));
            ITextFontResolver resolver = render.getFontResolver();
            resolver.addFont(PDF_FONT_PATH,
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            if (htmlString != null) {
                htmlString = htmlString.replaceAll("&nbsp;", "").replaceAll("<br>", "<br/>").replaceAll("&laquo;", "«")
                        .replaceAll("&raquo;", "»").replaceAll("&middot;", "-");
                render.setDocumentFromString(htmlString);
                render.layout();
                render.createPDF(outputStream);
            }
        } catch (final Exception e) {
            //TODO why the hell does it fail?
            LOGGER.error(e.getMessage(), e);
        }
        return outputStream;
    }

    public ByteArrayOutputStream addImgStamp(final byte[] source, final String imgSource, final float x, final float y) throws IOException, DocumentException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfReader reader = new PdfReader(source);
        PdfStamper stamper = new PdfStamper(reader, outputStream);

        Image img = Image.getInstance(imgSource);
        img.setAbsolutePosition(x, y);
        int total = reader.getNumberOfPages() + 1;
        for(int i = 1; i < total; i++) {
            stamper.getOverContent(1).addImage(img);
        }
        stamper.close();
        reader.close();
        return outputStream;
    }
}
