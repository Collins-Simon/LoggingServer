package nz.ac.wgtn.swen301.resthome4logs.server;

public class LogEvent {
	final String id;
	final String message;
	final String timestamp;
	final String thread;
	final String logger;
	final String level;
	final String errorDetails;
	public LogEvent(String id, String message, String timestamp, String thread, String logger, String level,
			String errorDetails) {
		this.id = id;
		this.message = message;
		this.timestamp = timestamp;
		this.thread = thread;
		this.logger = logger;
		this.level = level;
		this.errorDetails = errorDetails;
	}
	public String getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getThread() {
		return thread;
	}
	public String getLogger() {
		return logger;
	}
	public String getLevel() {
		return level;
	}
	public String getErrorDetails() {
		return errorDetails;
	}
	
}
