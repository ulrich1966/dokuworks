package de.juli.docuworks.docuhandle;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OpenOfficePdfService {
	private static final Logger LOG = LoggerFactory.getLogger(OpenOfficePdfService.class);
	public enum CreationVia {
		OPEN_OFFICE(),
		XML_PARSER();
	}
	private CreationVia via;
	private String source;
	private String target;
	private String cmd;

	/**
	 * Konstruktor der Klasse, der eine Metohde zur Erzeugung des PDFs konsumiert. 
	 */
	public OpenOfficePdfService(CreationVia via) {
		this.via = via;
	}

	/**
	 * Setzt die zur Erzeugung nocwendigen Parameter und ruft die erzeugende Methode auf. 
	 */
	public boolean create(String source) throws Exception {
		this.source = source;
		return this.create();
	}

	/**
	 * Setzt die zur Erzeugung nocwendigen Parameter und ruft die erzeugende Methode auf. 
	 */
	public boolean create(String source, String target) throws Exception {
		this.source = source;
		this.target = target;
		return this.create();
	}

	/**
	 * Setzt die zur Erzeugung nocwendigen Parameter und ruft die erzeugende Methode auf. 
	 */
	public boolean create(String source, String target, String cmd) throws Exception {
		this.source = source;
		this.target = target;
		this.cmd = cmd;
		return this.create();
	}

	/**
	 * Leitet die den Konvertierungsprozess zu der im Konstruktro angebenen Methode um    
	 */
	private boolean create() throws Exception {
		boolean success = false;
		switch (this.via) {
		case OPEN_OFFICE:
			success = createViaOpenOfficeInstallation();
			break;
		case XML_PARSER:
			success = true;
		default:
			break;
		}
		return success;
	}
	
	/**
	 * Estellt von der angegebenen Quelle eine PDF - Datei mit dem auf dem
	 * Rechner installierten Liebre Office dessen Pad im 'cmd' Argument
	 * angegeben werden muss und speichert das neue PDF in der als target
	 * angebebenen Datei, die im Anschluss zurueckgegeben wird.
	 */
	private boolean createViaOpenOfficeInstallation() throws Exception {
		if (getSource() == null) {
			throw new RuntimeException("Es gibt kein Quelldokument!");
		}
		if (getTarget() == null) {
			throw new RuntimeException("Es gibt kein Zieldokument!");
		}
		if (getCmd() == null) {
			throw new RuntimeException("Es gibt keien Pfad zu Libreoffice!");
		}
		LOG.debug("\n{}\n{}\n{}", this.source, this.target, this.cmd);

		ProcessBuilder pb = null;
		Process process = null;
		try {
			pb = new ProcessBuilder(cmd, "-headless", "-accept=\"socket,host=127.0.0.1,port=8100;urp;\"", "-nofirststartwizard");
			process = pb.start();
			LOG.debug("Prozess gestartet: {} {}", process.isAlive());
		} catch (IOException e) {
			LOG.error("Es gab einen Fehler beim Starten des Process");
		}

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		connection.connect();

		LOG.debug("OpenOfficeConnection: {}", connection.isConnected());

		File inputFile = new File(source);
		File outputFile = new File(target);

		if (connection.isConnected()) {
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);
			connection.disconnect();
		}

		process.destroy();
		pb = null;

		return true;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
