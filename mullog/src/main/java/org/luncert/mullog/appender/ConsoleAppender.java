package org.luncert.mullog.appender;

import java.util.Properties;

public class ConsoleAppender extends StandardAppender {

	public ConsoleAppender(Properties props) {}

	@Override
	public void log(int logLevel, String ... fields) throws Exception {
		System.out.println(format(logLevel, fields));
	}

}