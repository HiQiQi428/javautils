package org.luncert.mullog;

import java.util.Properties;

import org.luncert.mullog.appender.StandardAppender;

public class CustomAppender extends StandardAppender {

    public CustomAppender(Properties props) {
        super(props);
    }

    @Override
    protected void output(String data) throws Exception {
        System.out.println(data);
    }

}