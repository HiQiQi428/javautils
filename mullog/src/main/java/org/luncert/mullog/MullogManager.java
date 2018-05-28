package org.luncert.mullog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.luncert.mullog.appender.Appender;

public class MullogManager implements Serializable {

    private static final long serialVersionUID = 7606646448285009177L;

	protected static MullogManager getInstance() { return MullogManagerInner.INSTANCE; }

    protected void addAppender(Appender appender) { appenders.add(appender); }

    protected List<Appender> getAppender() { return appenders; }

    private List<Appender> appenders = new ArrayList<>();
    
    private MullogManager() {}

    private static class MullogManagerInner {
        private static final MullogManager INSTANCE = new MullogManager(); 
    }

}