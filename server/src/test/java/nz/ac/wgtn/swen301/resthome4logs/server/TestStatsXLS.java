package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsXLS {

	@Test
	public void test() {
		Persistency.resetDB();
		sendPost("WeirdId", "foo.bar.logger", "DEBUG");
		sendPost("Anotherdebuginfoobar", "foo.bar.logger", "DEBUG");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		StatsXLSServlet service = new StatsXLSServlet();
		try {
			service.doGet(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = null;
		try {
			result = response.getContentAsString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		assertNotNull(result);
		assertEquals(200, response.getStatus());
	}
	private MockHttpServletResponse sendPost(String id,String logger, String level) {
		String content = "{\n"
				+ "\"id\": \""+ id + "\",\n"
				+ "\"message\": \"application started\",\n"
				+ "\"timestamp\": \"04-05-2021 13:30:45\",\n"
				+ "\"thread\": \"main\",\n"
				+ "\"logger\": \""+ logger + "\",\n"
				+ "\"level\": \""+ level + "\",\n"
				+ "\"errorDetails\": \"string\"\n"
				+ "}";
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setMethod("POST");
		request.setContent(content.getBytes());

		MockHttpServletResponse response = new MockHttpServletResponse() {
			public void setContentType(String s) {}
		};
		LogsServlet service = new LogsServlet();
		try {
			service.doPost(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response; 
	}

}
