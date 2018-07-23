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

	private JSONObject getParent(StringTokenizer tokenizer) {
		JSONObject tmp = jsonObject;
		for (int i = 0, limit = tokenizer.countTokens() - 1; i < limit; i++) {
			String name = tokenizer.nextToken();
			tmp = tmp.getJSONObject(name);
			if (tmp == null) return null;
		}
		return tmp;
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
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.get(tokenizer.nextToken());
		else return null;
	}

	@Override
	public String getString(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getString(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Boolean getBoolean(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getBoolean(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Integer getInteger(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getInt(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Long getLong(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getLong(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Double getDouble(String namespace) {
		checkNamespace(namespace);
		StringTokenizer tokenizer = new StringTokenizer(namespace, ":");
		JSONObject tmp = getParent(tokenizer);
		if (tmp != null) return tmp.getDouble(tokenizer.nextToken());
		else return null;
	}

	@Override
	public Float getFloat(String namespace) {
		throw new RuntimeException("net.sf.json.JSONObject doesn't support getFloat");
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}

}