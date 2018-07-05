package org.luncert.mullog;

import java.io.Serializable;

import org.luncert.mullog.appender.Appender;

public final class Mullog implements Serializable {

    private static final long serialVersionUID = 3437274876191224782L;

    private Mullog() {}

    private static void log(int logLevel, Object... fields) {
        String[] fs = new String[fields.length];
        for (int i = 0, limit = fields.length; i < limit; i++) fs[i] = String.valueOf(fields[i]);

        if (MullogManager.getAppenders().size() == 0) MullogConfig.autoConfig();
        for (Appender appender : MullogManager.getAppenders().values()) {
            try {
                appender.log(logLevel, fs);
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void info(Object... fields) { log(Appender.MULLOG_INFO, fields); }

    public static void warn(Object... fields) { log(Appender.MULLOG_WARN, fields); }
    
    public static void debug(Object... fields) { log(Appender.MULLOG_DEBUG, fields); }
    
    public static void error(Object... fields) { log(Appender.MULLOG_ERROR, fields); }

    public static void fatal(Object... fields) { log(Appender.MULLOG_FATAL, fields); }

}