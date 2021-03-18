package de.juli.docuworks.docuhandle.model;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;
import org.jopendocument.dom.ODSingleXMLDocument;

public class DocumentModel {
	
	private ODSingleXMLDocument sourceDocument;
	private Path source; 
	private Path target; 
	private HashMap<String, String> map;
	
	public DocumentModel() {
		super();
	}
	
	public DocumentModel(Path source, Path target, HashMap<String, String> map) {
		this();
		this.source = source;
		this.target = target;
		this.map = map;
	}
	
	public File targetToFile() {
		return target.toFile();
	}

	public File sourceToFile() {
		return source.toFile();
	}

	public ODSingleXMLDocument getSourceDocument() {
		return sourceDocument;
	}

	public void setSourceDocument(ODSingleXMLDocument sourceDocument) {
		this.sourceDocument = sourceDocument;
	}
	
	@SuppressWarnings("unchecked")
	public List<Element> getElementList() {
		return sourceDocument.getBody().getChildren();
		//return elementList;
	}

	public Path getSource() {
		return source;
	}

	public void setSource(Path source) {
		this.source = source;
	}

	public Path getTarget() {
		return target;
	}

	public void setTarget(Path target) {
		this.target = target;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
}
