package org.luncert.mullog.appender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.luncert.mullog.Mullog;

public abstract class StandardAppender implements Appender {
    
    private static final String RE_FORMAT_STRING = "%[T|L|M|C|S]";

    protected String formatString;

    protected int logLevel;

    public StandardAppender(Properties props) {
        String level = props.getProperty("level");
        for (int i = 0; i < Mullog.MULLOG_LEVEL.length; i++) {
            if (level.toUpperCase().compareTo(Mullog.MULLOG_LEVEL[i]) == 0)
                this.logLevel = i;
        }
    }

    public boolean isDebugAllowed() { return logLevel <= Mullog.MULLOG_DEBUG; }

    public boolean isInfoAllowed() { return logLevel <= Mullog.MULLOG_INFO; }

    public boolean isWarnAllowed() { return logLevel <= Mullog.MULLOG_WARN; }

    public boolean isErrorAllowed() { return logLevel <= Mullog.MULLOG_ERROR; }

    /**
     * 我实现的format方法，完成配置文件formatString到log参数的映射
     */
    protected String format(int logLevel, String ... fields) {
        Pattern r = Pattern.compile(RE_FORMAT_STRING);
        Matcher matcher = r.matcher(formatString);

        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[4];
        StringBuilder ret = new StringBuilder();
        
        for (int i = 0, lastMatch = 0, limit = fields.length; matcher.find();) {
            String part = matcher.group(0);
            if (lastMatch != matcher.start()) ret.append(formatString.subSequence(lastMatch, matcher.start()));
            if (part.charAt(1) == 'T') ret.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            else if (part.charAt(1) == 'L') ret.append(Mullog.MULLOG_LEVEL[logLevel]);
            else if (part.charAt(1) == 'M') ret.append(stackTraceElement.getMethodName());
            else if (part.charAt(1) == 'C') ret.append(stackTraceElement.getClassName());
            else if (part.charAt(1) == 'S' && i < limit) {
                ret.append(fields[i]);
                i++;
            }
            lastMatch = matcher.end();
        }
        return ret.toString();
    }

    protected abstract void output(String data) throws Exception;

	@Override
	public void log(int logLevel, String... fields) throws Exception {
        if (this.logLevel <= logLevel) output(format(logLevel, fields));
    }

	@Override
    public void setFormatString(String formatString) { this.formatString = formatString; }
    
    @Override
    public void setLogLevel(int logLevel) {
        if (logLevel < Mullog.MULLOG_DEBUG || logLevel > Mullog.MULLOG_FATAL) throw new RuntimeException("invalid log level: " + logLevel);
        else this.logLevel = logLevel;
    }

    @Override
    public int getLogLevel() { return logLevel; }

}