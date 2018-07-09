package org.luncert.mullog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.luncert.mullog.annotation.BindAppender;

@RunWith(JUnit4.class)
@BindAppender(name = "console1")
public class TestMullog {

    Mullog mullog = new Mullog(TestMullog.class);

    @Test
    public void test() {
        mullog.info("desc", "msg", 1);
    }

}