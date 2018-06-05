package org.luncert.jrequest.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestHTMLResolver {

    @Test
    public void test() throws IOException {
        // File file = new File("test.html");
        // byte[] buf = new byte[(int) file.length()];
        // FileInputStream in = new FileInputStream(file);
        // in.read(buf);
        // in.close();

        Document doc = Document.resolve("");
    }

}