package nz.ac.wgtn.swen301.resthome4logs.server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class TestDeleteLogs {

	@Test
	public void Test() {
		Persistency.resetDB();

		MockHttpServletRequest request = new MockHttpServletRequest();

		request.setParameter("limit","3");
		request.setParameter("level", "DEBUG");
		MockHttpServletResponse response = new MockHttpServletResponse();
		LogsServlet service = new LogsServlet();
		try {
			service.doDelete(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, Persistency.database.size());
		assertEquals(200, response.getStatus());
		

		
	}
}
