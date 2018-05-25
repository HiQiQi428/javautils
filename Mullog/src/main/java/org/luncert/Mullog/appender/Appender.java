package org.luncert.Mullog.appender;

import org.luncert.Mullog.formatter.Formatter;

public interface Appender {

    public void log(int logLevel, String message) throws Exception;

	public Appender setFormatter(Formatter formatter);

}