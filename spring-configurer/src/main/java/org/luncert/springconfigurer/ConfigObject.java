package org.luncert.springconfigurer;

public interface ConfigObject {

    public void setAttribute(String namespace, Object value);

    public Object getAttribute(String namespace);

    public String getString(String namespace);

    public Boolean getBoolean(String namespace);

    public Integer getInteger(String namespace);

    public Long getLong(String namespace);

    public Double getDouble(String namespace);

    public Float getFloat(String namespace);

    public String toString();
    
}