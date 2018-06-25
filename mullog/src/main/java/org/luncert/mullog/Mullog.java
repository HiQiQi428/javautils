package org.luncert.mullog;

import java.io.Serializable;

import org.luncert.mullog.appender.Appender;

public final class Mullog implements Serializable {

    private static final long serialVersionUID = 3437274876191224782L;

    public static final String[] MULLOG_LEVEL = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"};

    public static final int MULLOG_DEBUG = 0;

    public static final int MULLOG_INFO = 1;

    public static final int MULLOG_WARN = 2;
    
    public static final int MULLOG_ERROR = 3;
    
    public static final int MULLOG_FATAL = 4;

    private Mullog() {}

    private static final MullogManager mullogManager = MullogManager.getInstance();

    public static void info(Object... fields) { log(MULLOG_INFO, fields); }

    public static void warn(Object... fields) { log(MULLOG_WARN, fields); }
    
    public static void debug(Object... fields) { log(MULLOG_DEBUG, fields); }
    
    public static void error(Object... fields) { log(MULLOG_ERROR, fields); }

    public static void fatal(Object... fields) { log(MULLOG_FATAL, fields); }

    public static void log(int logLevel, Object... fields) {
        String[] fs = new String[fields.length];
        for (int i = 0, limit = fields.length; i < limit; i++) fs[i] = String.valueOf(fields[i]);

        if (mullogManager.getAppenders().size() == 0) MullogConfig.autoConfig();
        for (Appender appender : mullogManager.getAppenders().values()) {
            try {
                appender.log(logLevel, fs);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

}