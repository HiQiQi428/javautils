package org.luncert.cson;

import java.util.HashMap;
import java.util.Map;

public final class CsonObject {

    private Map<String, Object> map = new HashMap<>();

    public boolean hasKey(String key) { return map.containsKey(key); }

    public void put(String key, Object value) {
        if (value == null) throw new CsonException("value connot be null");
        else if (value instanceof CsonObject || value instanceof CsonArray || value instanceof String) map.put(key, value);
        else map.put(key, value.toString());
    }

    public void remove(String key) { map.remove(key); }

    public boolean beEmpty() { return map.size() == 0; }

    public int getInt(String key) {
        Object obj = map.get(key);
        try { return Integer.valueOf((String)obj); }
        catch (Exception e) { throw new CsonException(e); }
    }

    public int getInt(String key, int defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return Integer.valueOf((String)obj); }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public long getLong(String key) {
        Object obj = map.get(key);
        try { return Long.valueOf((String)obj); }
        catch(Exception e) { throw new CsonException(e); }
    }

    public long getLong(String key, long defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return Long.valueOf((String)obj); }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public float getFloat(String key) {
        Object obj = map.get(key);
        try { return Float.valueOf((String)obj); }
        catch(Exception e) { throw new CsonException(e); }
    }

    public float getFloat(String key, float defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return Float.valueOf((String)obj); }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public double getDouble(String key) {
        Object obj = map.get(key);
        try { return Double.valueOf((String)obj); }
        catch(Exception e) { throw new CsonException(e); }
    }

    public double getDouble(String key, double defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return Double.valueOf((String)obj); }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public boolean getBoolean(String key) {
        Object obj = map.get(key);
        try { return Boolean.valueOf((String)obj); }
        catch(Exception e) { throw new CsonException(e); }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return Boolean.valueOf((String)obj); }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public String getString(String key) {
        Object obj = map.get(key);
        try { return (String)obj; }
        catch(Exception e) { throw new CsonException(e); }
    }

    public String getString(String key, String defaultValue) {
        Object obj = map.get(key);
        if (obj != null && obj instanceof String) {
            try { return (String)obj; }
            catch (Exception e) { return defaultValue; }
        } else return defaultValue;
    }

    public CsonObject getCsonObject(String key) {
        Object obj = map.get(key);
        try { return (CsonObject)obj; }
        catch(Exception e) { throw new CsonException(e); }
    }

    public CsonArray getCsonArray(String key) {
        Object obj = map.get(key);
        try { return (CsonArray)obj; }
        catch(Exception e) { throw new CsonException(e); }
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
                CsonArray csonArray = (CsonArray)obj;
                if (csonArray.beEmpty()) builder.append("[]");
                else builder.append(csonArray.toString(indent + '\t'));
            }
            else if (obj instanceof CsonObject) {
                CsonObject csonObject = (CsonObject)obj;
                if (csonObject.beEmpty()) builder.append("{}");
                else builder.append('\n').append(csonObject.toString(indent + '\t'));
            }
            else {
                String value = (String)obj;
                if (Util.beNumber(value) || Util.beBool(value)) builder.append(value);
                else builder.append('"').append(value).append('"');
            }
            builder.append('\n');
        }
        return builder.substring(0, builder.length() - 1);
    }

}