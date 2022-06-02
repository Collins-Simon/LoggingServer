package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * @author simon
 *
 */
public class LogsServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getParameterMap();
		resp.setContentType("application/json");
		PrintWriter output = resp.getWriter();

		Integer limit;
		Persistency.Level level = null; 
		
		try {
			limit = Integer.parseInt(req.getParameter("limit"));
			level = Persistency.Level.valueOf(req.getParameter("level"));
		}catch(NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		output.println(Persistency.getLogs(5, level));

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new Gson();
		resp.setContentType("application/json");
		PrintWriter output = resp.getWriter();
		LogEvent data = null;

		String jsondata = req.getReader().lines().collect(Collectors.joining());
		try {
			data = gson.fromJson(jsondata, LogEvent.class);
		}catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		//output.println(jsondata);
		try {
			Persistency.addLog(data);
		}catch(IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}
		
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Persistency.clearDB();
	}
	
	
}
