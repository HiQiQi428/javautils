package org.luncert.cson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestUtil {

    @Test
    public void testBeNumber() {
        System.out.println(Util.beNumber("+12"));
    }
}