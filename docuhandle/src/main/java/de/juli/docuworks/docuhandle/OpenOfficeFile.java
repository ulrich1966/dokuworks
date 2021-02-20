
package de.juli.docuworks.docuhandle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.jopendocument.dom.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//${cmp_name}
//${cnt_first_name} 
//${cnt_last_name}
//${cmp_street}
//${cmp_zip} 
//${cmp_city}
//${job_title}
//${cnt_sex} 
//${cnt_title} 
//${cnt_last_name}

public class OpenOfficeFile {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficeFile.class);
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

	public ODSingleXMLDocument openDoc(String path, String name) throws JDOMException, IOException {
		String docFile = String.format("%s/%s", path, name);
		File file = new File(docFile);
		ODSingleXMLDocument doc = ODSingleXMLDocument.createFromPackage(file);

		return doc;
	}

	public ODSingleXMLDocument saveDoc(String path, String name, ODSingleXMLDocument doc) throws JDOMException, IOException {
		String docFile = String.format("%s/%s", path, name);
		File file = new File(docFile);
		file.createNewFile();
		doc.saveToPackageAs(file);
		return doc;
	}

	public void iterateElements(List<Element> list) {
		String tmp = "";
		if (list.size() <= 0)
			list = null;

		if (list != null) {
			for (Element element : list) {
				// tmp = String.format("Name : %s\nValue: %s",
				// element.getName(), element.getValue());
				// LOG.debug(tmp);
				changeValue(element);
				if (element.getChildren() != null) {
					element.getChildren();
				} else {
				}
			}
		} else {

		}
	}

	private void changeValue(Element element) {
		String tmp = element.getValue();
		for (String[] field : fields) {
			if (tmp.contains(field[0])) {
				tmp = tmp.replace(field[0], field[1]);
			}
		}
		if (!element.getValue().equals(tmp)) {
			element.setText(tmp);
		}
		LOG.debug(element.getValue());
	}
}