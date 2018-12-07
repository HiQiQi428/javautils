package org.luncert.mullog.appender;

public interface Appender {

    /**
     * @param logLevel
     * @param fields
     */
    public void log(int logLevel, String... fields) throws Exception;

    /**
     * @return logLevel
     */
    public int getLogLevel();

}