package org.luncert.mullog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.luncert.mullog.appender.Appender;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

    private static Map<String, Appender> appenders = new HashMap<>();

    private MullogManager() {}

    public static void addAppender(String name, Appender appender) { appenders.put(name, appender); }

    public static Appender getAppender(String name) { return appenders.get(name); }

    protected static Map<String, Appender> getAppenders() { return appenders; }

}