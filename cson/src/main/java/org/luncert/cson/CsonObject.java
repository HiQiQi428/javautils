package org.luncert.cson;

import java.util.HashMap;
import java.util.Map;

public final class CsonObject {

    private Map<String, Object> map = new HashMap<>();

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public void put(String key, Object value) {
        if (value == null)
            throw new CsonException("value connot be null");
        else if (value instanceof CsonObject || value instanceof CsonArray || value instanceof String)
            map.put(key, value);
        else
            map.put(key, value.toString());
    }

    public void remove(String key) {
        map.remove(key);
    }

    public boolean beEmpty() {
        return map.size() == 0;
    }

    public Integer getInt(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return Integer.valueOf((String) obj);
        else
            return null;
    }

    public Integer getInt(String key, int defaultValue) {
        Integer value = getInt(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Long getLong(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return Long.valueOf((String) obj);
        else
            return null;
    }

    public Long getLong(String key, long defaultValue) {
        Long value = getLong(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Float getFloat(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return Float.valueOf((String) obj);
        else
            return null;
    }

    public Float getFloat(String key, float defaultValue) {
        Float value = getFloat(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Double getDouble(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return Double.valueOf((String) obj);
        else
            return null;
    }

    public Double getDouble(String key, double defaultValue) {
        Double value = getDouble(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Boolean getBoolean(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return Boolean.valueOf((String) obj);
        else
            return null;
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        Boolean value = getBoolean(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public String getString(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String)
            return (String) obj;
        else
            return null;
    }

    public String getString(String key, String defaultValue) {
        String value = getString(key);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public CsonObject getCsonObject(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof CsonObject)
            return (CsonObject) obj;
        else
            return null;
    }

    public CsonArray getCsonArray(String key) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof CsonArray)
            return (CsonArray) obj;
        else
            return null;
    }

    /**
     * if value is number, then return String instance
     */
    public Object getObject(String key) {
        Object obj = map.get(key);
        if (obj != null) {
            if (obj instanceof String) {
                String value = (String)obj;
                if (CsonUtil.beBool(value)) return Boolean.valueOf(value);
                // else if (CsonUtil.beNumber(value)) return value;
                else return value;
            }
            else return obj;
        }
        else return null;
    }

    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.append(indent).append(entry.getKey()).append(": ");
            Object obj = entry.getValue();
            if (obj instanceof CsonArray) {
                CsonArray csonArray = (CsonArray) obj;
                if (csonArray.beEmpty())
                    builder.append("[]");
                else
                    builder.append(csonArray.toString(indent + '\t'));
            } else if (obj instanceof CsonObject) {
                CsonObject csonObject = (CsonObject) obj;
                if (csonObject.beEmpty())
                    builder.append("{}");
                else
                    builder.append('\n').append(csonObject.toString(indent + '\t'));
            } else {
                String value = (String) obj;
                if (CsonUtil.beNumber(value) || CsonUtil.beBool(value))
                    builder.append(value);
                else
                    builder.append('"').append(value).append('"');
            }
            builder.append('\n');
        }
        return builder.substring(0, builder.length() - 1);
    }

}