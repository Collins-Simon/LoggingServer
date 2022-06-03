package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestStatsHTML {

	@Test
	public void test_01() {
		Persistency.resetDB();
		sendPost("WeirdId", "foo.bar.logger", "DEBUG");
		sendPost("Anotherdebuginfoobar", "foo.bar.logger", "DEBUG");
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		StatsServlet service = new StatsServlet();
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
		Document soup = Jsoup.parse(result);
		Node table = soup.childNode(0).//html
				childNode(1).//skipping head and going into body
				childNode(0).//table
				childNode(0).//tbody
				childNode(1).//The Second row of the table
				childNode(3).//third element is the DEBUG counter
				childNode(0);//removing the td tags
		assertEquals("2", table.toString());
		System.out.println(soup.toString());
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


