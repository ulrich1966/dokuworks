
package de.juli.docuworks.docuhandle.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;

public class OpenOfficeFileService {
	private Map<String, String> dataMap;
	private static final String FIELD_PATTERN = "${%s}";
	
	/**
	 * Oeffnet das als source angegebenes ODSingleXMLDocument fuellt die in der data Map uebergebenen Daten ein und gibt
	 * das ODSingleXMLDocument zuruek
	 */
	public ODSingleXMLDocument convert(Path source, Map<String, String> data) throws JDOMException, IOException {
		this.dataMap = (HashMap<String, String>) data;
		ODSingleXMLDocument doc = openDoc(source);
		return iterateElements(doc);
	}

	/**
	 * Oeffnet das als source angegebenes ODSingleXMLDocument fuellt die in der data Map uebergebenen Daten ein und
	 * speichert das neue Dolument in der als target angegebnen Datei
	 * das ODSingleXMLDocument zuruek 
	 */
	public ODSingleXMLDocument convert(Path source, Path target, Map<String, String> data) throws JDOMException, IOException {
		ODSingleXMLDocument converted = convert(source, data); 
		return saveDoc(target, converted);
	}

	/**
	 * Oeffnet das als Pfad und Dateiname angegebenes ODSingleXMLDocument fuellt die in der data Map uebergebenen Daten ein und
	 * gibt das ODSingleXMLDocument zuruek 
	 */
	public ODSingleXMLDocument convert(String path, String name, Map<String, String> data) throws JDOMException, IOException {
		this.dataMap = (HashMap<String, String>) data;
		String docFile = String.format("%s/%s", path, name);
		File file = new File(docFile);
		ODSingleXMLDocument doc = openDoc(file);
		return iterateElements(doc);
	}

	/**
	 * Erzeut Fileobjet aus angebeenen Pfad und Namen.
	 * Speichert eine Kopie des uebergebenen ODSingleXMLDocument in angegebene Datei
	 * und gibt neue Datei als ODSingleXMLDocument zuruek
	 */
	public ODSingleXMLDocument saveDoc(String path, String name, ODSingleXMLDocument doc) throws JDOMException, IOException {
		String docFile = String.format("%s/%s", path, name);
		return saveDoc(new File(docFile), doc);
	}


	/**
	 * Erzeut Fileobjet aus angebeenen Path.
	 * Speichert eine Kopie des uebergebenen ODSingleXMLDocument in angegebene Datei
	 * und gibt neue Datei als ODSingleXMLDocument zuruek
	 */
	public ODSingleXMLDocument saveDoc(Path path, ODSingleXMLDocument doc) throws JDOMException, IOException {
		return saveDoc(path.toFile(), doc);
	}

	/**
	 * Speichert eine Kopie des uebergebenen ODSingleXMLDocument in angegebene Datei
	 * und gibt neue Datei als ODSingleXMLDocument zuruek
	 */
	public ODSingleXMLDocument saveDoc(File file, ODSingleXMLDocument doc) throws JDOMException, IOException {
		file.createNewFile();
		doc.saveAs(file);
		return openDoc(file);
	}

	/**
	 * Oeffnet das als Pfad angegebene ODSingleXMLDocument und gibt es zuruek 
	 */
	private ODSingleXMLDocument openDoc(Path path) throws JDOMException, IOException {
		return openDoc(path.resolve(path).toFile());
	}

	/**
	 * Oeffnet das als File angegebene ODSingleXMLDocument und gibt es zuruek 
	 */
	private ODSingleXMLDocument openDoc(File file) throws JDOMException, IOException {
		ODSingleXMLDocument doc = ODSingleXMLDocument.createFromFile(file);
		return doc;
	}

	/**
	 * Itteriert ueber die Liste von Elementen aud dem ODSingleXMLDocument und
	 * schickt jedes Element nach changeValue zu ueberpruefung und ggf.
	 * Austausch des Feldwertes.
	 * 
	 * @param doc
	 */
	private ODSingleXMLDocument iterateElements(ODSingleXMLDocument doc) {
		@SuppressWarnings("unchecked")
		List<Element> list = doc.getBody().getChildren();
		
		if (list.size() <= 0)
			list = null;

		if (list != null) {
			for (Element element : list) {
				changeValue(element);
				if (element.getChildren() != null) {
					element.getChildren();
				}
			}
		}

		list.forEach(e -> System.out.println(e.getValue()));
		
		return doc;
	}

	/**
	 * Itteriert ueber die Map mit auszutauschenden Feldwerten und prueft ob
	 * Felder im Element zu ersezetzen sind.
	 * Ueberprueft die Schreibweise des Feldnamens ind der DataMap. Liegt die 
	 * Schreibweise in geschweiftenklammern vor wir das Feld ersezte, wenn nicht
	 * wir das Feldatribut zu ${xxx} ergaenzt.    
	 * 
	 * @param element
	 */
	private void changeValue(Element element) {
		String tmp = element.getValue();
		String field = "";
		for (Entry<String, String> entry : dataMap.entrySet()) {
			if(!entry.getKey().startsWith("${")) {
				field = String.format(FIELD_PATTERN, entry.getKey());				
			} else {
				field = entry.getKey(); 
			}
			if (tmp.contains(field)) {
				tmp = tmp.replace(field, entry.getValue());
			}
		}

		if (!element.getValue().equals(tmp)) {
			element.setText(tmp);
		}
	}

}