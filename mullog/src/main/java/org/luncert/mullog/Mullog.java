package org.luncert.mullog;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Optional;

import org.luncert.mullog.appender.Appender;
import org.luncert.mullog.exception.MullogException;

public final class Mullog implements Serializable {

    private static final long serialVersionUID = 3437274876191224782L;

    private Appender appender;

    public Mullog(String name) {
        appender = MullogManager.getAppender(name);
        if (appender == null)
            throw new MullogException("invalid appender name: " + name);
    }

    public Mullog(Appender appender) {
        this.appender = appender;
    }

    private void log(int logLevel, Object... fields) {
        // cast Object[] to String[]
        String[] fs = new String[fields.length];
        for (int i = 0, limit = fields.length; i < limit; i++) fs[i] = String.valueOf(fields[i]);
        // log
        try {
            appender.log(logLevel, fs);
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void info(Object... fields) { log(LogLevel.INFO, fields); }

    public void warn(Object... fields) { log(LogLevel.WARN, fields); }
    
    public void debug(Object... fields) { log(LogLevel.DEBUG, fields); }
    
    public void error(Object... fields) { log(LogLevel.ERROR, fields); }

    public void fatal(Object... fields) { log(LogLevel.FATAL, fields); }

    public Optional<Mullog> setTmpAppender(String name) {
        Appender appender = MullogManager.getAppender(name);
        Mullog mullog = null;
        if (appender != null)
            mullog = new Mullog(appender);
        return Optional.ofNullable(mullog);
    }

    public void addAppender(String name, Appender appender) {
        MullogManager.addAppender(name, appender);
    }

    public static Path getConfigPath() {
        return MullogManager.getConfigPath();
    }

}