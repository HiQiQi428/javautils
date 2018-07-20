package org.luncert.cson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCsonBuilder {

    @Test
    public void test() throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream inputStream = TestCsonBuilder.class.getClassLoader().getResourceAsStream("test.cson");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) builder.append(line).append('\n');
        }

        CsonBuilder csonBuilder = new CsonBuilder();
        CsonObject csonObject = csonBuilder.build(builder.toString());
        System.out.println(csonObject.toString());
        System.out.println(csonObject.getInt("luncert"));
    }

}