package de.juli.docuworks.docuhandle.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

public class WebTemplatePdfService {
	final static Logger LOG = LoggerFactory.getLogger(WebTemplatePdfService.class);
	
	public Path generatePdf(Map<String, String> map, Path target, Path templateLocation) {
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable("map", map);
		
		String html = null;
		try {
			html = templateEngine.process("templates/anschreiben", context);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		try {
			OutputStream outputStream = new FileOutputStream(target.toFile());
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.close();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		return target;
	}
}
