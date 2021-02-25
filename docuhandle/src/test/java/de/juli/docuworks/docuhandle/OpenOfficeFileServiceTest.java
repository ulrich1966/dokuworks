package de.juli.docuworks.docuhandle;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jopendocument.dom.ODSingleXMLDocument;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenOfficeFileServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficeFileServiceTest.class);
	
	private String[][] fields = {
			{"${cmp_name}", "Firmenname"}, 
			{"${cnt_first_name}", "Vorname"}, 
			{"${cnt_last_name}", "Nachname"},
			{"${cmp_street}", "Straﬂe"},
			{"${cmp_zip}", "Plz"},
			{"${cmp_city}", "Stadt"},
			{"${job_title}", "Jobtitel"},
			{"${cnt_sex}", "Herr"},
			{"${cnt_title}", "Titel"},
		};
	
	@Test
	public void test() throws Exception {
		HashMap<String, String> map = fillMeUp();
		OpenOfficeFileService oof = new OpenOfficeFileService();
		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.odt"));
		source = source.resolve(Paths.get("test.odt"));
		
		LOG.debug(source.toUri().getPath());
		LOG.debug(target.toUri().getPath());

		ODSingleXMLDocument doc = oof.convert(source, target, map);
		Assert.assertNotNull(doc);		
		
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
