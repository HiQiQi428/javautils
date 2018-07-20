package org.luncert.springconfigurer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.luncert.mullog.Mullog;
import org.luncert.mullog.annotation.BindAppender;

@RunWith(JUnit4.class)
@BindAppender(name = "Test")
public class TestConfigManager {
    
    Mullog mullog = new Mullog(this);

    ConfigManager configManager;

    @Before
    public void before() throws Exception {
        configManager = new ConfigManager();
        // configManager.startWatchChange();
    }

    @Test
    public void test() {
        configManager.setAttribute("school:name:id", 1032);
        mullog.info(configManager.getAttribute("school:name:id"));
    }

    @After
    public void after() {
        // configManager.stopWatchChange();
    }

}