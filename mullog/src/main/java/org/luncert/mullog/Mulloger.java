package org.luncert.mullog;

public class Mulloger {

    public void info(Object... fields) { Mullog.info(fields); }
    
    public void warn(Object... fields) { Mullog.warn(fields); }
    
    public void debug(Object... fields) { Mullog.debug(fields); }
    
    public void error(Object... fields) { Mullog.error(fields); }

    public void fatal(Object... fields) { Mullog.fatal(fields); }

}