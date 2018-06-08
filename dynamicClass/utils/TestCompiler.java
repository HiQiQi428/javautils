package org.luncert.mullog.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

@RunWith(JUnit4.class)
public class TestCompiler {

    @Test
    public void test() {
        Map<String, byte[]> results;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        MemoryJavaFileManager manager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
        JavaFileObject javaFileObject = manager.makeStringSource("MullogProxy", "");
        CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
        if (task.call()) results = manager.getClassBytes();
    }
    
}