package de.juli.docuworks.docuhandle;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.jdom.Element;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenOfficeFileTest {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficeFileTest.class);
	
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
	
	private static final Consumer<Element> readElement = new Consumer<Element>() {
		@Override
		public void accept(Element element) {
			if(element.getChildren() != null || element.getChildren().size() > 0) {
				LOG.debug(String.format("%s : %s : %s", element.getName(), element.getValue()));					
				//element.getChildren().forEach(readElement);
			} else {
				LOG.debug(String.format("%s : %s", element.getName(), element.getValue()));					
			}
		}
	};
	
	@Test
	public void test() throws Exception {
		HashMap<String, String> map = fillMeUp();
		OpenOfficeFile oof = new OpenOfficeFile();
		Path source = Paths.get(new File("src/main/resources").getAbsolutePath());
		Path target = source.resolve(Paths.get("newdoc.odt"));
		source = source.resolve(Paths.get("test.odt"));
		
		LOG.debug(source.toUri().getPath());
		LOG.debug(target.toUri().getPath());
				
//		Path locDir = Paths.get(model.getLocalDocDir());
//		Path target = locDir.resolve(Paths.get("anschreiben" + ".odt"));
//		
//		
		ODSingleXMLDocument doc = oof.convert(source, target, map);
		LOG.debug(doc.asString());
		
		
//		Assert.assertNotNull(doc);		
//		LOG.debug(doc.asString());
//
//		//doc.getBody().getChildren().forEach(OpenOfficeFileTest.readElement);
//		
//		oof.convert(doc);
//
//		LOG.debug(doc.asString());
//		
//		oof.saveDoc(new File("src/main/resources").getAbsolutePath(), "newdoc.odt", doc);

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
