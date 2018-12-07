package org.luncert.mullog.appender;

import java.util.Properties;

import org.luncert.mullog.Formatter;
import org.luncert.mullog.LogLevel;
import org.luncert.mullog.exception.MullogException;

public abstract class StandardAppender implements Appender {
    
    protected Formatter formatter;

    protected Integer logLevel;

    public StandardAppender(Properties props) {
        String level = props.getProperty("level");
        logLevel = LogLevel.convertInteger(level);
        if (logLevel == null)
            throw new MullogException("invalid log level: " + level);
        formatter = new Formatter(props.getProperty("format"));
    }

    public boolean isDebugAllowed() { return logLevel <= LogLevel.DEBUG; }

    public boolean isInfoAllowed() { return logLevel <= LogLevel.INFO; }

    public boolean isWarnAllowed() { return logLevel <= LogLevel.WARN; }

    public boolean isErrorAllowed() { return logLevel <= LogLevel.ERROR; }

    protected abstract void output(String data) throws Exception;

	@Override
	public void log(int logLevel, String... fields) throws Exception {
        if (this.logLevel <= logLevel)
            output(formatter.format(logLevel, fields));
    }

    @Override
    public int getLogLevel() {
        return logLevel;
    }

}