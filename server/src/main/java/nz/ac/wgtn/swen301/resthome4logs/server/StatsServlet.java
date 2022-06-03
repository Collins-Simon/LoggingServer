package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatsServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter output = resp.getWriter();
		
		String[] logLevels = {"All", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"};
		
        HashMap<String, HashMap<String, Integer>> loggers = new HashMap<String, HashMap<String, Integer>>();
        for(LogEvent log: Persistency.database) {
        	if(loggers.containsKey(log.getLogger())) {
        		HashMap<String, Integer> logRow = loggers.get(log.getLogger());
        		Integer originalValue = logRow.getOrDefault(log.getLevel(), 0);
        		logRow.put(log.getLevel(), originalValue + 1);
        		
        	}else {
        		HashMap<String, Integer> newMap = new HashMap<String, Integer>();
        		newMap.put(log.getLevel(), 1);
        		loggers.put(log.getLogger(), newMap);
        	}
        }
        //put the top row in place
        output.print("<html>");
        output.print("<body>");
        output.print("<table>");
        output.print("<tr>");
        output.print("<th>logger</th>");
        for(String name: logLevels) {
        	output.print("<th>" + name + "</th>");
		}
        output.print("</tr>");
        
        for (Map.Entry<String, HashMap<String, Integer>> entry : loggers.entrySet()) {
        	output.print("<tr>");
        	output.print("<th>" + entry.getKey() + "</th>");
        	for(String loggername: logLevels) {
        		output.print("<td>" + entry.getValue().getOrDefault(loggername, 0) + "</td>");
        	}
        	output.print("</tr>");
        }
        output.print("</table>");
        output.print("</body>");
        output.print("</html>");
	}
}
