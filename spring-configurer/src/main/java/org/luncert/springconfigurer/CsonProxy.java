package org.luncert.springconfigurer;

import java.security.InvalidParameterException;
import java.util.StringTokenizer;

import org.luncert.cson.CsonObject;
import org.luncert.springconfigurer.ConfigObject;

public class CsonProxy implements ConfigObject {

	private CsonObject csonObject;

	public CsonProxy(CsonObject csonObject) {
		this.csonObject = csonObject;
	}

	private void checkNamespace(String namespace) {
		if (namespace == null || namespace.length() == 0 || namespace.equals(":"))
			throw new InvalidParameterException("namespace");
	}

	private CsonObject getParent(StringTokenizer tokenizer) {
		CsonObject tmp = csonObject;
		for (int i = 0, limit = tokenizer.countTokens() - 1; i < limit; i++) {
			String name = tokenizer.nextToken();
			tmp = tmp.getCsonObject(name);
			if (tmp == null) return null;
		}
		return tmp;
	}

	@Override
	public void setAttribute(String namespace, Object value) {
		checkNamespace(namespace);

		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = csonObject;

		while (true) {
			String name = tokenizer.nextToken();
			if (!tokenizer.hasMoreTokens()) {
				tmp.put(name, value);
				return;
			} else {
				if (!tmp.hasKey(name))
					tmp.put(name, new CsonObject());
				tmp = tmp.getCsonObject(name);
			}
		}
	}

	@Override
	public Object getAttribute(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getObject(tokenizer.nextToken());
		else return null;
	}

	@Override
	public String getString(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getString(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Boolean getBoolean(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getBoolean(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Integer getInteger(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getInt(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Long getLong(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getLong(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Double getDouble(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getDouble(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Float getFloat(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getFloat(tokenizer.nextToken());
		else return null;
	}

	@Override
	public String toString() {
		return csonObject.toString();
	}

}