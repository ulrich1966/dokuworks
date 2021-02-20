package de.juli.docuworks.docuhandle;

import java.io.File;
import java.util.function.Consumer;

import org.jdom.Element;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenOfficeFileTest {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficeFileTest.class);
	
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
		OpenOfficeFile oof = new OpenOfficeFile();
		ODSingleXMLDocument doc = oof.openDoc(new File("src/main/resources").getAbsolutePath(), "test.odt");
		Assert.assertNotNull(doc);		
		LOG.debug(doc.asString());

		//doc.getBody().getChildren().forEach(OpenOfficeFileTest.readElement);
		
		oof.iterateElements(doc.getBody().getChildren());

		LOG.debug(doc.asString());
		
		oof.saveDoc(new File("src/main/resources").getAbsolutePath(), "newdoc.odt", doc);

		LOG.debug("done");
	}

}
