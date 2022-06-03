package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class StatsXLSServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		OutputStream output = resp.getOutputStream();
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Java Books");

		//Make the title
		Row toprow = sheet.createRow(0);
		String[] logLevels = {"All", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"};
		
		toprow.createCell(0).setCellValue("logger");
		int pos = 1;
		for(String name: logLevels) {
			toprow.createCell(pos++).setCellValue(name);
		}
 
		//get the data from the database in a hashmap for expandability which also needs to be added above
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
        
     
        
        int rowCount = 1;
        for (Map.Entry<String, HashMap<String, Integer>> entry : loggers.entrySet()) {
        	Row row = sheet.createRow(rowCount++);
        	row.createCell(0).setCellValue(entry.getKey());
        	int cellNumber = 1;
        	for(String loggername: logLevels) {
        		Cell cell = row.createCell(cellNumber++);
        		cell.setCellValue(entry.getValue().getOrDefault(loggername, 0));
        	}

        }

		workbook.write(output);
	}
	
}
