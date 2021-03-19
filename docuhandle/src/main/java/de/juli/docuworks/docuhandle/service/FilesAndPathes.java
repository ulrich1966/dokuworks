package de.juli.docuworks.docuhandle.service;

import java.io.File;

public class FilesAndPathes {
	
	public static String getResourcePath() {
		File f = new File("src/main/resources");
		return f.getAbsolutePath();	
	}

	public static String getResourcePath(String fileName) {
		String path$ = String.format("src/main/resources/%s", fileName);
		File f = new File(path$);
		return f.getAbsolutePath();	
	}
}
