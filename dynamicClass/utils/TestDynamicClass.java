package org.luncert.mullog.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestDynamicClass {

    @Test
    public void test() {
        DynamicClass d = new DynamicClass("MullogPProxy", "public", "static");
        d.setPackage("org.luncert.mullog");
        d.addImport("org.luncert.mullog.appender.UDPAppender");
        d.addField("private", "Appender", "appender");
        d.addMethod("public", "void", "show", null, "System.out.println(2018);");
        d.createClass();
    }

}