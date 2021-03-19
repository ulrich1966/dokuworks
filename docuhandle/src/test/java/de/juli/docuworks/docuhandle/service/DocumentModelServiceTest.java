package de.juli.docuworks.docuhandle.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jdom.JDOMException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docuworks.docuhandle.model.DocumentModel;

public class DocumentModelServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentModelServiceTest.class);
	/*
	A_UML("\u00C4"),
	O_UML("\u00D6"),
	U_UML("\u00DC"),
	a_UML("\u00E4"),
	o_UML("\u00F6"),
	u_UML("\u00FC"),
	SZ("\u00DF");
	 */
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
	

	@Test
	public void testConvert() throws JDOMException, IOException {
		HashMap<String, String> map = fillMeUp();
		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.odt"));
		source = source.resolve(Paths.get("test.odt"));
		
		LOG.debug(source.toUri().getPath());
		LOG.debug(target.toUri().getPath());
		
		DocumentModel model = new DocumentModel(source, target, map);
		
		DocumentModelService service = new DocumentModelService();

		model = service.convert(model);
		Assert.assertNotNull(model);		
		
		model.getElementList().forEach(e -> System.out.println(e.getText()));
		
		LOG.debug("done");
	}

	private HashMap<String, String> fillMeUp() {
		HashMap<String, String> map = new HashMap<String, String>();
		for (String[] field : fields) {
			map.put(field[0], field[1]);
		}
		return map;
	}
}
