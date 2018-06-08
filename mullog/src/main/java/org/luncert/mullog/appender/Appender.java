package org.luncert.mullog.appender;

public interface Appender {

    /**
     * @param logLevel
     * @param fields
     */
    public void log(int logLevel, String... fields) throws Exception;

    /**
     * @param formatString
     */
	public void setFormatString(String formatString);

}