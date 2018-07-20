package org.luncert.springconfigurer;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.luncert.springconfigurer.ConfigObject;

import net.sf.json.JSONObject;

public class JsonProxy implements ConfigObject {

	private JSONObject jsonObject;

	public JsonProxy(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	private void checkNamespace(String namespace) {
		if (namespace == null || namespace.length() == 0 || namespace.equals(":"))
			throw new InvalidParameterException("namespace");
	}

	@Override
	public void setAttribute(String namespace, Object value) {
		checkNamespace(namespace);

		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = jsonObject;

		while (true) {
			String name = tokenizer.nextToken();
			if (!tokenizer.hasMoreTokens()) {
				tmp.put(name, value);
				return;
			} else {
				if (tmp.has(name))
					tmp = tmp.getJSONObject(name);
				else {
					// 由于JSONObject不能存放空的JSONObject，所以只能倒着来
					List<String> tokens = new ArrayList<>();
					while (tokenizer.hasMoreTokens())
						tokens.add(tokenizer.nextToken());
					Object v = value;
					for (int i = tokens.size() - 1; i >= 0; i--) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put(tokens.get(i), v);
						v = jsonObj;
					}
					tmp.put(name, v);
					return;
				}
			}
		}
	}

	@Override
	public Object getAttribute(String namespace) {
		checkNamespace(namespace);

		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = jsonObject;
		while (true) {
			String name = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				tmp = tmp.getJSONObject(name);
				if (tmp == null)
					return null;
			} else
				return tmp.get(name);
		}
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

}