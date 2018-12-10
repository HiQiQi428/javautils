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
            throw new MullogException("invalid appender name - " + name);
    }

    public Mullog(Appender appender) {
        this.appender = appender;
    }

    private void log(int logLevel, Object... fields) {
        // 将Object[]转换为String[]
        String[] fs = new String[fields.length];
        for (int i = 0, limit = fields.length; i < limit; i++) {
            fs[i] = String.valueOf(fields[i]);
        }
        // log
        try {
            appender.log(logLevel, fs);
        } catch(Exception e) {
            throw new MullogException(e);
        }
    }

    public void info(Object... fields) { log(LogLevel.INFO, fields); }
    public void warn(Object... fields) { log(LogLevel.WARN, fields); }
    public void debug(Object... fields) { log(LogLevel.DEBUG, fields); }
    public void error(Object... fields) { log(LogLevel.ERROR, fields); }
    public void fatal(Object... fields) { log(LogLevel.FATAL, fields); }

    /**
     * <li>使Mullog临时切换Appender,在新的Appender上输出日志</li>
     * <li>实现其实是通过创建新的Mullog来使用新的Appender,所以该操作不影响当前Mullog</li>
     * <li>如果name指定的Appender不存在,不会创建新的Mullog,Optional中的操作不会被执行 </li>
     * @param name 目标Appender名字
     * @return Optional&lt;Mullog&gt; 由{@link java.util.Optional#ofNullable}创建
     */
    public Optional<Mullog> setTmpAppender(String name) {
        Appender appender = MullogManager.getAppender(name);
        Mullog mullog = null;
        if (appender != null)
            mullog = new Mullog(appender);
        return Optional.ofNullable(mullog);
    }

    /***
     * 添加自定义的Appender
     * @param name
     * @param appender
     */
    public void addAppender(String name, Appender appender) {
        MullogManager.addAppender(name, appender);
    }

    public Appender getUsingAppender() {
        return appender;
    }

    public static Path getConfigPath() {
        return MullogManager.configPath;
    }

}