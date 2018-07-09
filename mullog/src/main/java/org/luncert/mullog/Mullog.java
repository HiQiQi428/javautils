package org.luncert.mullog;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.luncert.mullog.annotation.BindAppender;
import org.luncert.mullog.appender.Appender;

public final class Mullog implements Serializable {

    private static final long serialVersionUID = 3437274876191224782L;

    private Appender appender;

    /**
     * by this way, no appender is specified
     */
    public Mullog() {}

    public Mullog(Object object) {
        this(object.getClass());
    }

    /**
     * @param object the object reference which creates this logger
     */
    public Mullog(Class<?> clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation instanceof BindAppender) {
                String name = ((BindAppender)annotation).name();
                appender = MullogManager.getAppender(name);
            }
        }
    }

    private void log(int logLevel, Object... fields) {
        // cast Object[] to String[]
        String[] fs = new String[fields.length];
        for (int i = 0, limit = fields.length; i < limit; i++) fs[i] = String.valueOf(fields[i]);
        // output
        try {
            if (appender != null) appender.log(logLevel, fs);
            else {
                for (Appender appender : MullogManager.getAppenders().values()) {
                    appender.log(logLevel, fs);
                }
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void info(Object... fields) { log(Appender.MULLOG_INFO, fields); }

    public void warn(Object... fields) { log(Appender.MULLOG_WARN, fields); }
    
    public void debug(Object... fields) { log(Appender.MULLOG_DEBUG, fields); }
    
    public void error(Object... fields) { log(Appender.MULLOG_ERROR, fields); }

    public void fatal(Object... fields) { log(Appender.MULLOG_FATAL, fields); }

}