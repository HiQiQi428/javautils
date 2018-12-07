package org.luncert.mullog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TestMullog {

    Mullog mullog = new Mullog("console1");

    @Test
    public void test() {
        mullog.info("desc", "msg", 1);
        // mullog.setTmpAppender("console2").ifPresent((log) -> log.info("this is appender console2"));
    }

}