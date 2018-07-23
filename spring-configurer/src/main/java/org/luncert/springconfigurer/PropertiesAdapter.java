package org.luncert.springconfigurer;

import java.util.Properties;

import org.luncert.cson.CsonUtil;
import org.luncert.springconfigurer.ConfigObject;

public class PropertiesAdapter extends Properties implements ConfigObject {

	private static final long serialVersionUID = -3731622562205299098L;

	@Override
	public void setAttribute(String namespace, Object value) {
		setProperty(namespace, String.valueOf(value));
	}

	@Override
	public Object getAttribute(String namespace) {
		return getProperty(namespace);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		forEach((key, value) -> builder.append(key).append('=').append(value).append('\n'));
		return builder.substring(0, builder.length() - 1);
	}

	@Override
	public String getString(String namespace) {
		return getProperty(namespace);
	}

	@Override
	public Boolean getBoolean(String namespace) {
		String value = getProperty(namespace);
		if (CsonUtil.beBool(value)) return Boolean.valueOf(value);
		else return null;
	}

	@Override
	public Integer getInteger(String namespace) {
		String value = getProperty(namespace);
		if (CsonUtil.beNumber(value)) return Integer.valueOf(value);
		else return null;
	}

	@Override
	public Long getLong(String namespace) {
		String value = getProperty(namespace);
		if (CsonUtil.beNumber(value)) return Long.valueOf(value);
		else return null;
	}

	@Override
	public Double getDouble(String namespace) {
		String value = getProperty(namespace);
		if (CsonUtil.beNumber(value)) return Double.valueOf(value);
		else return null;
	}

	@Override
	public Float getFloat(String namespace) {
		String value = getProperty(namespace);
		if (CsonUtil.beNumber(value)) return Float.valueOf(value);
		else return null;
	}

}