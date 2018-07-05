package org.luncert.mullog.appender;

public interface Appender {

    static final String[] MULLOG_LEVEL = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"};

    static final int MULLOG_DEBUG = 0;

    static final int MULLOG_INFO = 1;

    static final int MULLOG_WARN = 2;
    
    static final int MULLOG_ERROR = 3;
    
    static final int MULLOG_FATAL = 4;

    /**
     * @param logLevel
     * @param fields
     */
    public void log(int logLevel, String... fields) throws Exception;

    /**
     * @param formatString
     */
    public void setFormatString(String formatString);
    
    /**
     * @param logLevel
     */
    public void setLogLevel(int logLevel);

    /**
     * @return logLevel
     */
    public int getLogLevel();

}