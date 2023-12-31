package at.fhtw.swen2.tutorial.service;

import at.fhtw.swen2.tutorial.persistence.TourDatabaseInitializer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class PdfGeneratorDemo {

    private String parseThymeleafTemplateHelloWorld() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("to", "Markus");

        return templateEngine.process("thymeleaf/hello_world", context);
    }

    private String parseThymeleafTemplateTourList() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("header", "Touren");
        context.setVariable("Tours", TourDatabaseInitializer.getInitialDemoDataDtos());

        return templateEngine.process("thymeleaf/Tour_list", context);
    }

    private void generatePdfFromHtml(String html) throws Exception {
        String outputFolder = "src/main/resources/thymeleaf/thymeleaf.pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    public void startDemo() throws Exception {
        //generatePdfFromHtml(parseThymeleafTemplateHelloWorld());
        generatePdfFromHtml(parseThymeleafTemplateTourList());
    }

    public static void main(String[] args) throws Exception {
        new PdfGeneratorDemo().startDemo();
    }
}
