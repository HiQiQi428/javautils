package org.luncert.mullog.appender;

import java.util.Properties;

import org.luncert.mullog.formatter.Formatter;

public class ConsoleAppender implements Appender {

	private Formatter formatter;
	
	public ConsoleAppender(Properties props) {}

	@Override
	public void log(int logLevel, String message) throws Exception {
		System.out.println(formatter.format(logLevel, message));
	}

	@Override
	public Appender setFormatter(Formatter formatter) {
		this.formatter = formatter;
		return this;
	}

}