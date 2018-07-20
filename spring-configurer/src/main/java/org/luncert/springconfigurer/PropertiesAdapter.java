package org.luncert.springconfigurer;

import java.util.Properties;

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

}