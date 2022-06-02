package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.google.gson.Gson;


/**
 * @author simon
 *
 */
public class TestGetLogs {
	
	private Gson gson = new Gson();

	@Test
	public void validTest_01() {
		Persistency.resetDB();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("limit","3");
		request.setParameter("level", "DEBUG");
		MockHttpServletResponse response = new MockHttpServletResponse() {
			public void setContentType(String s) {}
		};
		LogsServlet service = new LogsServlet();
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
		LogEvent[] resultsArray;
		

		resultsArray = gson.fromJson(result, LogEvent[].class);

		
		assertNotNull(resultsArray);
		assertEquals(1, resultsArray.length);
		
	}
	
	@Test
	public void Invalidtest_01() {
		Persistency.resetDB();

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("limit","3");
		request.setParameter("level", "DEBUG");
		MockHttpServletResponse response = new MockHttpServletResponse() {
			public void setContentType(String s) {}
		};
		LogsServlet service = new LogsServlet();
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
		LogEvent[] resultsArray;
		

		resultsArray = gson.fromJson(result, LogEvent[].class);

		
		assertNotNull(resultsArray);
		assertEquals(1, resultsArray.length);
		
	}
	



}
