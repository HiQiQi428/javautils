package org.luncert.simpleutils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonResult {

    private int statusCode;
    
    private String description;

    private Object data;

    public JsonResult(int statusCode) { this(statusCode, null, null); }

    public JsonResult(int statusCode, String description) { this(statusCode, description, null); }

    public JsonResult(int statusCode, String description, Object data) {
        this.statusCode = statusCode;
        this.description = description;
        this.data = data;
    }

    private static String stringify(int statusCode, String description, Object data) {
        JSONObject json = new JSONObject();
        json.put("statusCode", statusCode);
        json.put("description", description);
        if (data instanceof Map) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.putAll((Map<?, ?>)data);
            json.put("data", jsonObj);
        }
        else if (data instanceof List) {
            JSONArray jsonArray = JSONArray.fromObject(data);
            json.put("data", jsonArray);
        }
        else json.put("data", data);
        return json.toString();
    }

    @Override
    public String toString() {
        return stringify(statusCode, description, data);
    }

    public static String build(int statusCode) { return build(statusCode, null, null); }

    public static String build(int statusCode, String description) { return build(statusCode, description, null); }

    /**
     * @param statusCode 状态码
     * @param description 描述
     * @param data 数据
     */
    public static String build(int statusCode, String description, Object data) {
        return stringify(statusCode, description, data);
    }

}
