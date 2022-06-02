package nz.ac.wgtn.swen301.resthome4logs.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

public class Persistency {
	enum Level {
		ALL(0),
		DEBUG(1),
		INFO(2),
		WARN(3),
		ERROR(4),
		FATAL(5),
		TRACE(6),
		OFF(7);
		
		public final Integer priority;
		private Level(Integer p) {
			this.priority = p;
		}
		public boolean checkLevel(Level tocompare) {
			return tocompare.priority <= this.priority;
			
		}
	}
	private static Gson gson = new Gson();
	
	public static List<LogEvent> database = new ArrayList<LogEvent>();
	static {
		resetDB();
	}
	
	public static List<String> getLogs(int numberOfLogs, Level level){
		int i = 0;
		Gson gson = new Gson();
		List<String> toreturn = new ArrayList<String>();
		while(toreturn.size() < numberOfLogs && i <database.size()) {

			LogEvent asEvent = database.get(i);
			
			if(Persistency.Level.valueOf(asEvent.level).checkLevel(level)) {
				toreturn.add(gson.toJson(asEvent));
			}
			
			i++; 
		}
		return toreturn;
	}
	/** 
	 * @param logToAdd
	 */
	public static void addLog(LogEvent logToAdd) {
		Iterator<LogEvent> dbitr= database.iterator();
		while(dbitr.hasNext()) {
			LogEvent toCompare = dbitr.next();
			if (toCompare.id.equals(logToAdd.id)) {
				throw new IllegalArgumentException();
			}
		}
		database.add(logToAdd);
	}
	public static void clearDB() {
		database.clear();
	}
	public static void resetDB() {
		clearDB();
		database.add(gson.fromJson(" {\n"
				+ "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n"
				+ "    \"message\": \"application started\",\n"
				+ "    \"timestamp\": \"04-05-2021 13:30:45\",\n"
				+ "    \"thread\": \"main\",\n"
				+ "    \"logger\": \"com.example.Foo\",\n"
				+ "    \"level\": \"DEBUG\",\n"
			+ "    \"errorDetails\": \"string\"\n"
				+ "  }", LogEvent.class));
	}
	

}
