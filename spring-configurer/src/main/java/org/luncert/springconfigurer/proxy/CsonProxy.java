package org.luncert.springconfigurer.proxy;

import java.util.StringTokenizer;

import org.luncert.cson.CsonObject;
import org.luncert.springconfigurer.ConfigObject;

public class CsonProxy implements ConfigObject {

	private CsonObject csonObject;

	public CsonProxy(CsonObject csonObject) {
		this.csonObject = csonObject;
	}

	@Override
	public void setAttribute(String namespace, Object value) {
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		CsonObject tmp = csonObject;
		while (true) {
			String name = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				tmp = tmp.getCsonObject(name, null);
				if (tmp == null) return null;
			}
			else return tmp.getString(name);
		}
	}

	@Override
	public Object getAttribute(String namespace) {
		return null;
	}
    
}