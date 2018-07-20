package org.luncert.springconfigurer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestPropertiesAdapter {

    @Test
    public void test() {
        PropertiesAdapter pAdapter = new PropertiesAdapter();
        pAdapter.setAttribute("ak", 123);
        pAdapter.setAttribute("wx", "asd");
        System.out.println(pAdapter.toString());
    }

}