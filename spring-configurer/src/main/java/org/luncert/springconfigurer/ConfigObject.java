package org.luncert.springconfigurer;

public interface ConfigObject {

    public void setAttribute(String namespace, Object value);

    public Object getAttribute(String namespace);
    
}