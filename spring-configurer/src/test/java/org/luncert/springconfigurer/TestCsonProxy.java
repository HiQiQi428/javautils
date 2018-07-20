package org.luncert.springconfigurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.luncert.cson.CsonObject;

@RunWith(JUnit4.class)
public class TestCsonProxy {

    @Test
    public void test() {
        CsonObject csonObject = new CsonObject();
        CsonProxy csonProxy = new CsonProxy(csonObject);
        csonProxy.setAttribute("school:name:id", 13);
        System.out.println(csonProxy.toString());
    }

}