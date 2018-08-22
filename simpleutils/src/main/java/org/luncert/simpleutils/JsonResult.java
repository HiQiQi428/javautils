package org.luncert.simpleutils;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonResult {

    public static String build(int statusCode) { return build(statusCode, null, null); }

    public static String build(int statusCode, String description) { return build(statusCode, description, null); }

    public static String build(int statusCode, String description, Object data) {
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

}
