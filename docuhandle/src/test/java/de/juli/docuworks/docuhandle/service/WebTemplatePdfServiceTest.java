package de.juli.docuworks.docuhandle.service;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WebTemplatePdfServiceTest {

	@Test
	public void testGeneratePdf() {
			Map<String, String> txtMap = new HashMap<String, String>();
		    txtMap.put("sender_name", "Hein Mück");
			txtMap.put("sender_street", "Sebalsdsbrück. 36");
			txtMap.put("sender_city", "28203 Bremen");
			txtMap.put("sender_phone", "Telefon: 01111 22334455");
			txtMap.put("sender_email", "E-Mail: hein.mück@gmx.de");
			txtMap.put("company_name", "Unternehen des Jobs 0");
			txtMap.put("company_contact", "Herr Vorname 0 Nachname 0");
			txtMap.put("company_street", "Straße der Firma 0");
			txtMap.put("company_city", "1230 Stadt der Firma0");
			txtMap.put("letter_address", "Sehr geehrter Herr Nachname 0");
			txtMap.put("title", "Toller Job 0");
			txtMap.put("date", "Bremen 18.03.21");
			txtMap.put("txt", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \r\n"
					+ "\r\n"
					+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \r\n"
					+ "\r\n"
					+ "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse");
			
			WebTemplatePdfService service = new WebTemplatePdfService();
			service.generatePdf(txtMap, Paths.get(FilesAndPathes.getResourcePath("anschreiben.pdf")), null);
	
	}

}
