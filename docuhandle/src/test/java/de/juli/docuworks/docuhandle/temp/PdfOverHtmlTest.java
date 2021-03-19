package de.juli.docuworks.docuhandle.temp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;

import de.juli.docuworks.docuhandle.model.DocumentModel;
import de.juli.docuworks.docuhandle.service.DocumentModelService;
import de.juli.docuworks.docuhandle.service.FilesAndPathes;

public class PdfOverHtmlTest {
	private static final Logger LOG = LoggerFactory.getLogger(PdfOverHtmlTest.class);
	
	private String[][] fields = {
			{"${cmp_name}", "Firmenname"}, 
			{"${cnt_first_name}", "Vorname"}, 
			{"${cnt_last_name}", "Nachname"},
			{"${cmp_street}", "Stra\u00DFe"},
			{"${cmp_zip}", "Plz"},
			{"${cmp_city}", "Stadt"},
			{"${job_title}", "Jobtitel"},
			{"${cnt_sex}", "Herr"},
			{"${cnt_title}", "Titel"},
		};
	
	private List<Element> fillElements() throws Exception {
		HashMap<String, String> map = fillMeUp();
		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.odt"));
		source = source.resolve(Paths.get("test.odt"));
		
		LOG.debug(source.toUri().getPath());
		LOG.debug(target.toUri().getPath());
		
		DocumentModel model = new DocumentModel(source, target, map);
		
		//ODSingleXMLDocument doc = oof.convert(source, target, map);
		DocumentModelService service = new DocumentModelService();

		model = service.convert(model);
		Assert.assertNotNull(model);		
		
		model.getElementList().forEach(e -> System.out.println(e.getText()));
		
		return model.getElementList();
	}

	private HashMap<String, String> fillMeUp() {
		HashMap<String, String> map = new HashMap<String, String>();
		for (String[] field : fields) {
			map.put(field[0], field[1]);
		}
		return map;
	}

	@Test
	public void test() throws Exception {
		List<Element> list = fillElements();
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		
//		list.forEach(e -> {
//			if(e.getText() == null || e.getText().isEmpty()) {
//				e.setText("\u0026nbsp;");
//			}
//		});
		
		Context context = new Context();
		context.setVariable("list", list);

		// Get the plain HTML with the resolved ${name} variable!
		String html = templateEngine.process("templates/template", context);

		try {
			String path$ = FilesAndPathes.getResourcePath("message.pdf");
			OutputStream outputStream = new FileOutputStream(path$);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(outputStream);
			outputStream.close();
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

		LOG.debug("{}", html);
	}
	
	

}
