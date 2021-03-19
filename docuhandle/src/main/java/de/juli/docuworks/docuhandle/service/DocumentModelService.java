package de.juli.docuworks.docuhandle.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jopendocument.dom.ODSingleXMLDocument;

import de.juli.docuworks.docuhandle.model.DocumentModel;

public class DocumentModelService {
	private static final String FIELD_PATTERN = "${%s}";
	private Map<String, String> currentDataMap;

	/**
	 * Setzt die aktuelle Hashmap, die den Inhalt der auszutauschenden Felder beinhaltet.
	 * Erzeugt einen String mit dem Dateipfad fuer Das Zieldokument und setzt dieses in 
	 * das uebergebene DocumentModel. Ruft die Methode zum durchiterrieren der Dokument-Elemente auf 
	 */
	public DocumentModel convert(DocumentModel model) throws JDOMException, IOException {
		currentDataMap = model.getMap();
		ODSingleXMLDocument sourece = ODSingleXMLDocument.createFromFile(model.sourceToFile());
		model.setSourceDocument(sourece);
		model = iterateElements(model);
		model = saveDoc(model);
		return model;
	}
	

	/**
	 * Itteriert ueber die Liste von Elementen aud dem ODSingleXMLDocument des
	 * DocumentModel und schickt jedes Element nach changeValue zu ueberpruefung und ggf.
	 * Austausch des Feldwertes. Setzt am Ende die Liste der Elemente in denen sich 
	 * der Text befindet in das DocumentModel
	 */
	private DocumentModel iterateElements(DocumentModel model) {
		@SuppressWarnings("unchecked")
		List<Element> list = model.getSourceDocument().getBody().getChildren();
		
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
		return model;
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
		for (Entry<String, String> entry : currentDataMap.entrySet()) {
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
	
	/**
	 * Speichert eine Kopie des uebergebenen ODSingleXMLDocument in angegebene Datei
	 * und gibt neue Datei als ODSingleXMLDocument zuruek
	 */
	private DocumentModel saveDoc(DocumentModel model) throws JDOMException, IOException {
		File file = model.targetToFile();
		file.createNewFile();
		ODSingleXMLDocument document = model.getSourceDocument();
		document.saveAs(file);
		return model;
	}

}
