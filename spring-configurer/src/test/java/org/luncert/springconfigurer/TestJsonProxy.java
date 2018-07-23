package org.luncert.springconfigurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import net.sf.json.JSONObject;

@RunWith(JUnit4.class)
public class TestJsonProxy {

    @Test
    public void test() {
        JSONObject jsonObject = new JSONObject();
        JsonProxy jsonProxy = new JsonProxy(jsonObject);
        jsonProxy.setAttribute("school:name:id", 13);
        System.out.println(jsonProxy.getInteger("school:name:id"));
        System.out.println(jsonProxy.toString());
    }

}