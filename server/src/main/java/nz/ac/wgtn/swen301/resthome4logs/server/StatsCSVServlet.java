package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class StatsCSVServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/csv");
		PrintWriter output = resp.getWriter();
		
		String[] logLevels = {"All", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"};
		
        HashMap<String, HashMap<String, Integer>> loggers = new HashMap<String, HashMap<String, Integer>>();
        for(LogEvent log: Persistency.database) {
        	if(loggers.containsKey(log.getLogger())) {
        		HashMap<String, Integer> logRow = loggers.get(log.getLogger());
        		Integer originalValue = logRow.getOrDefault(loggers, 0);
        		logRow.put(log.getLevel(), originalValue + 1);
        		
        	}else {
        		HashMap<String, Integer> newMap = new HashMap<String, Integer>();
        		newMap.put(log.getLevel(), 1);
        		loggers.put(log.getLogger(), newMap);
        	}
        }
        //put the top row in place
        output.print("logger");
        for(String name: logLevels) {
        	output.print("," + name);
		}
        
        for (Map.Entry<String, HashMap<String, Integer>> entry : loggers.entrySet()) {
        	output.print("\n");
        	output.print(entry.getKey());
        	for(String loggername: logLevels) {
        		output.print("," + entry.getValue().getOrDefault(loggername, 0));
        	}

        }
	}

}
