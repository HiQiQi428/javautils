package org.luncert.Mullog;

import java.io.Serializable;

import org.luncert.Mullog.appender.Appender;

public final class Mullog implements Serializable {

    private static final long serialVersionUID = 3437274876191224782L;

    protected static final int MULLOG_INFO = 0;

    protected static final int MULLOG_WARN = 1;

    protected static final int MULLOG_DEBUG = 2;

    protected static final int MULLOG_ERROR = 3;
    
    protected static final int MULLOG_FATAL = 4;

    private Mullog() {}

    private static final MullogManager mullogManager = MullogManager.getInstance();

    public static void info(String message) { log(MULLOG_INFO, message); }

    public static void warn(String message) { log(MULLOG_WARN, message); }

    public static void debug(String message) { log(MULLOG_DEBUG, message); }

    public static void error(String message) { log(MULLOG_ERROR, message); }

    public static void fatal(String message) { log(MULLOG_FATAL, message); }

    private static void log(int logLevel, String message) {
        if (mullogManager.getAppender().size() == 0) MullogConfig.autoConfig();
        for (Appender appender : mullogManager.getAppender()) {
            try {
                appender.log(logLevel, message);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

}