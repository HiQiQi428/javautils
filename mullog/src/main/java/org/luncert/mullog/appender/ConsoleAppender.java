package org.luncert.mullog.appender;

import java.util.Properties;

public class ConsoleAppender extends StandardAppender {

	public ConsoleAppender(Properties props) {
		super(props);
	}

	@Override
	protected void output(String data) throws Exception {
		System.out.println(data);
	}

}