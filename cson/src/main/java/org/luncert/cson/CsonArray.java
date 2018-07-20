package org.luncert.cson;

import java.util.ArrayList;
import java.util.List;

public final class CsonArray {

    List<Object> lst = new ArrayList<>();

    public void add(Object value) {
        if (value instanceof String)
            lst.add(value);
        else
            lst.add(value.toString());
    }

    public void remove(int index) {
        lst.remove(index);
    }

    public boolean beEmpty() {
        return lst.size() == 0;
    }

    public Integer getInt(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return Integer.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }
    }

    public Integer getInt(int index, int defaultValue) {
        Integer value = getInt(index);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Long getLong(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return Long.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }
    }

    public Long getLong(int index, long defaultValue) {
        Long value = getLong(index);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Float getFloat(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return Float.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }

    }

    public Float getFloat(int index, float defaultValue) {
        Float value = getFloat(index);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Double getDouble(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return Double.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }
    }

    public Double getDouble(int index, double defaultValue) {
        Double value = getDouble(index);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public Boolean getBoolean(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return Boolean.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }
    }

    public Boolean getBoolean(int index, boolean defaultValue) {
        Boolean value = getBoolean(index);
        if (value != null)
            return value;
        else
            return defaultValue;
    }

    public String getString(int index) {
        try {
            Object obj = lst.get(index);
            if (obj instanceof String)
                return String.valueOf((String) obj);
            else
                return null;
        } catch (IndexOutOfBoundsException e) {
            throw new CsonException("index out of bounds");
        }
    }

    public String getString(int index, String defaultValue) {
        String value = getString(index);
        if (value != null)
            return value;
        else
            return defaultValue;
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
            String value = (String) obj;
            if (Util.beNumber(value) || Util.beBool(value))
                builder.append(value);
            else
                builder.append('"').append(value).append('"');
            builder.append('\n');
        }
        builder.append(indent).append(']');
        return builder.toString();
    }

}