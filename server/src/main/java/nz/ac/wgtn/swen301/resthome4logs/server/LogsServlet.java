package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author simon
 *
 */
public class LogsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getParameterMap();
		resp.setContentType("text/html");
		PrintWriter output = resp.getWriter();
		output.println("<html>");
		output.println("<body>Hello World</body>");
		output.println("</html>");
	}
	
	
}
