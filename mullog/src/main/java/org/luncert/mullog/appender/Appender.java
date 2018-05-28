package org.luncert.mullog.appender;

import org.luncert.mullog.formatter.Formatter;

public interface Appender {

    public void log(int logLevel, String message) throws Exception;

	public Appender setFormatter(Formatter formatter);

}