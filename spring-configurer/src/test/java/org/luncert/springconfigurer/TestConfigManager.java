package org.luncert.springconfigurer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestConfigManager {
    
    ConfigManager configManager;

    @Before
    public void before() throws Exception {
        configManager = new ConfigManager();
        // configManager.startWatchChange();
    }

    @Test
    public void test() {
        configManager.setAttribute("school:name:id", 1032);
        System.out.println(configManager.getInteger("school:name:id"));
    }

    @After
    public void after() {
        // configManager.stopWatchChange();
    }

}