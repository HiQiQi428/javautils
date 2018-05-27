package org.luncert.jreuqest.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.luncert.jrequest.html.HTMLResolver;

@RunWith(JUnit4.class)
public class TestHTMLResolver {

    @Test
    public void test() throws IOException {
        // File file = new File("test.html");
        // byte[] buf = new byte[(int) file.length()];
        // FileInputStream in = new FileInputStream(file);
        // in.read(buf);
        // in.close();

        HTMLResolver resolver = new HTMLResolver();
        resolver.resolve("<div name=\"youTable\">");
    }

}