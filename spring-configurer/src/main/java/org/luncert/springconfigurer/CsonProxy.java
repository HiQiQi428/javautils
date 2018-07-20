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
		CsonObject tmp = csonObject;
		while (true) {
			String name = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				tmp = tmp.getCsonObject(name);
				if (tmp == null)
					return null;
			} else
				return tmp.getObject(name);
		}
	}

	@Override
	public String toString() {
		return csonObject.toString();
	}

}