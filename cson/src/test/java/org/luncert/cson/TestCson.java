package org.luncert.cson;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCson {

    @Test
    public void test() throws IOException {
        CsonObject csonObject = CsonObject.parse(TestCson.class.getClassLoader().getResourceAsStream("test.cson"));
        System.out.println(csonObject.toString());
    }

}