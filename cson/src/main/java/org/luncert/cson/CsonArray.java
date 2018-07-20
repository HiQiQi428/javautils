package org.luncert.cson;

import java.util.ArrayList;
import java.util.List;

public final class CsonArray {

    List<Object> lst = new ArrayList<>();

    public void add(Object value) {
        if (value instanceof String) lst.add(value);
        else lst.add(value.toString());
    }

    public void remove(int index) { lst.remove(index); }

    public boolean beEmpty() { return lst.size() == 0; }

    public int getInt(int index) {
        try {
            Object obj = lst.get(index);
            return Integer.valueOf((String)obj);
        } catch (Exception e) { throw new CsonException(e); }
    }

    public int getInt(int index, int defaultValue) {
        try {
            Object obj = lst.get(index);
            return Integer.valueOf((String)obj);
        } catch (Exception e) { return defaultValue; }
    }

    public long getLong(int index) {
        try {
            Object obj = lst.get(index);
            return Long.valueOf((String)obj);
        } catch (Exception e) { throw new CsonException(e); }
    }

    public long getLong(int index, long defaultValue) {
        try {
            Object obj = lst.get(index);
            return Long.valueOf((String)obj);
        } catch (Exception e) { return defaultValue; }
    }

    public float getFloat(int index) {
        try {
            Object obj = lst.get(index);
            return Float.valueOf((String)obj);
        } catch (Exception e) { throw new CsonException(e); }

    }

    public float getFloat(int index, float defaultValue) {
        try {
            Object obj = lst.get(index);
            return Float.valueOf((String)obj);
        } catch (Exception e) { return defaultValue; }
    }

    public double getDouble(int index) {
        try {
            Object obj = lst.get(index);
            return Double.valueOf((String)obj);
        } catch (Exception e) { throw new CsonException(e); }
    }

    public double getDouble(int index, double defaultValue) {
        try {
            Object obj = lst.get(index);
            return Double.valueOf((String)obj);
        } catch (Exception e) { return defaultValue; }

    }

    public boolean getBoolean(int index) {
        try {
            Object obj = lst.get(index);
            return Boolean.valueOf((String)obj);
        } catch (Exception e) { throw new CsonException(e); }
    }

    public boolean getBoolean(int index, boolean defaultValue) {
        try {
            Object obj = lst.get(index);
            return Boolean.valueOf((String)obj);
        } catch (Exception e) { return defaultValue; }
    }

    public String getString(int index) {
        try {
            Object obj = lst.get(index);
            return (String)obj;
        } catch (Exception e) { throw new CsonException(e); }
    }

    public String getString(int index, String defaultValue) {
        try {
            Object obj = lst.get(index);
            return (String)obj;
        } catch (Exception e) { return defaultValue; }
    }

    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        indent = indent.substring(0, indent.length() - 1);
        StringBuilder builder = new StringBuilder();
        builder.append('[').append('\n');
        for (Object obj : lst) {
            builder.append(indent + '\t');
            String value = (String)obj;
            if (Util.beNumber(value) || Util.beBool(value)) builder.append(value);
            else builder.append('"').append(value).append('"');
            builder.append('\n');
        }
        builder.append(indent).append(']');
        return builder.toString();
    }

}