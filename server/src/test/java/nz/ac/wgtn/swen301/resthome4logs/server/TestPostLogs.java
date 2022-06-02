package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.gson.Gson;

public class TestPostLogs {
	
	private Gson gson = new Gson();
	/**
	 * A normal test with normal parameters
	 */
	@Test
	public void validTest_01() {
		Persistency.resetDB();
		MockHttpServletResponse response = sendPost("A random id", "DEBUG");
		assertEquals(200, response.getStatus());		
	}
	/**
	 * Sending a message with an already existing id should respond a 409
	 */
	@Test
	public void invalidTest_02() {
		Persistency.resetDB();
		MockHttpServletResponse response = sendPost("A random id", "DEBUG");
		assertEquals(200, response.getStatus());	
		
		MockHttpServletResponse response2 = sendPost("A random id", "DEBUG");
		assertEquals(409, response2.getStatus());	
	}
	/**
	 * invalid log level code
	 */
	@Test
	public void invalidTest_03() {
		Persistency.resetDB();
		MockHttpServletResponse response = sendPost("A random id", "INVALID LEVEL");
		assertEquals(400, response.getStatus());	
		
	}
	/**
	 * An invalid level and id
	 */
	@Test
	public void invalidTest_04() {
		Persistency.resetDB();
		MockHttpServletResponse response = sendPost("A random id", "DEBUG");	
		
		MockHttpServletResponse response2 = sendPost("A random id", "INVALID LEVEL");
		assertEquals(200, response.getStatus());// first one should work	
		assertEquals(400, response2.getStatus());//duplicate id should also be broken
	}
	/**
	 * An invalid level and id
	 */
	@Test
	public void invalidTest_05() {
		Persistency.resetDB();
		MockHttpServletResponse response = sendPost("234234234 \" \"golf\" : \"I should'nt work\"", "DEBUG");	
		assertEquals(400, response.getStatus());
		
	}
	
	
	private MockHttpServletResponse sendPost(String id, String level) {
		String content = "{\n"
				+ "\"id\": \""+ id + "\",\n"
				+ "\"message\": \"application started\",\n"
				+ "\"timestamp\": \"04-05-2021 13:30:45\",\n"
				+ "\"thread\": \"main\",\n"
				+ "\"logger\": \"com.example.Foo\",\n"
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
