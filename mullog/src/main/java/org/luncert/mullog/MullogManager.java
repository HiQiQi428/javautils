package org.luncert.mullog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.luncert.mullog.appender.Appender;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

    private Map<String, Appender> appenders = new HashMap<>();

	protected static MullogManager getInstance() { return MullogManagerInner.INSTANCE; }

    protected void addAppender(String name, Appender appender) { appenders.put(name, appender); }

    protected Map<String, Appender> getAppenders() { return appenders; }

    private MullogManager() {}

    private static class MullogManagerInner {
        private static final MullogManager INSTANCE = new MullogManager(); 
    }

    public Appender getAppender(String name) { return appenders.get(name); }

}