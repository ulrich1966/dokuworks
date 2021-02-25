package de.juli.docuworks.docuhandle;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jopendocument.dom.ODSingleXMLDocument;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import de.juli.docuworks.docuhandle.OpenOfficePdfService.CreationVia;


public class OpenOfficePdfServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficePdfServiceTest.class);
	
	@Before
	public void runOpenOfficeFileTest() throws Exception {
		OpenOfficeFileServiceTest test = new OpenOfficeFileServiceTest();
		test.test();
	}

	@Test
	public void ooPdfCreate() throws Exception {
		OpenOfficePdfService oof = new OpenOfficePdfService(CreationVia.OPEN_OFFICE);
		
		String cmd = "C:\\Program Files (x86)\\LibreOffice 5\\program\\soffice.exe";
		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.pdf"));
		source = source.resolve(Paths.get("newdoc.odt"));
		
		oof.create(source.toString(), target.toString(), cmd);

		LOG.debug("done");
	}

	@Ignore
	@Test
	public void test() throws Exception {
		OpenOfficeFileService oof = new OpenOfficeFileService();

		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.pdf"));

		LOG.debug(source.toUri().getPath());
		LOG.debug(target.toUri().getPath());
		
		ODSingleXMLDocument xmlDoc = ODSingleXMLDocument.createFromFile(source.toFile());
		Assert.assertNotNull(xmlDoc);
		@SuppressWarnings("unchecked")
		List<Element> elements = xmlDoc.getBody().getChildren();
		
		//elements.forEach(e -> System.out.println(e.toString()));

		Document document = new Document(PageSize.A4);
		Assert.assertNotNull(document);

		PdfDocument pdf = new PdfDocument();
		Assert.assertNotNull(pdf);
		
		FileOutputStream fos = new FileOutputStream(target.toFile());
		Assert.assertNotNull(fos);
		
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		Assert.assertNotNull(fos);

		pdf.addWriter(writer);
		document.open();
		
		elements.forEach(e ->  {
			try {
				document.add(e);
			} catch (DocumentException ex) {
				
			}
		});

		writer.flush();
		document.close();
		LOG.debug("done");
	}

}
