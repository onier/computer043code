package test.demo;

import java.io.Serializable;

public class LogMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8010585881612531764L;

	private String message = "";

	public LogMessage(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
