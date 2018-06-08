package org.luncert.mullog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestMullog {

    @Test
    public void test() {
        Mullog.info("desc", "msg", 1);
    }

}