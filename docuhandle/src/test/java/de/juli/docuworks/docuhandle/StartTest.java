package de.juli.docuworks.docuhandle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docuworks.docuhandle.service.FilesAndPathes;

public class StartTest {
	private static final Logger LOG = LoggerFactory.getLogger(StartTest.class);
	static final Start start = new Start();

	@Test
	public void test() {
		String txt = StartTest.start.getMoin();
		LOG.debug(txt);
	}

	@Test
	public void testPath() {
		FilesAndPathes fap = new FilesAndPathes();
		String txt = fap.getResourcePath();
		LOG.debug(txt);
	}

}
