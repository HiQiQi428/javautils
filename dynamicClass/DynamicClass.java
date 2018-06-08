package org.luncert.mullog.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class DynamicClass {

    private String name;

    private String packageName;

    private List<String> importStmts = new ArrayList<>();

    private String[] classModifiers;

    private List<String> fields = new ArrayList<>();

    private List<String> methods = new ArrayList<>();

    public DynamicClass(String name, String ... modifiers) {
        this.name = name;
        classModifiers = modifiers;
    }

    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    public void addImport(String packageName) {
        this.importStmts.add(packageName);
    }

    public void addField(String type, String name) {
        fields.add(type + name);
    }

    public void addField(String modifier, String type, String name) {
        fields.add(modifier + " " + type + " " + name);
    }

    public void addField(String modifier, String type, String name, String initStatm) {
        fields.add(modifier + " " + type + " " + name + " " + initStatm);
    }

    private String resolveParams(Map<String, String> params) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            str.append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
        }
        str.subSequence(0, str.length() - 1);
        return str.toString();
    }

    public void addMethod(String retType, String name, Map<String, String> params, String body) {
        methods.add(String.format("%s %s(%s){%s}", retType, name, resolveParams(params), body));
    }

    public void addMethod(String modifier, String retType, String name, Map<String, String> paramters, String body) {
        methods.add(String.format("%s %s %s(%s){%s}", modifier, retType, name, paramters));
    }

    public Object createClass() {
        StringBuilder code = new StringBuilder();
        code.append("package ").append(packageName).append(";\r\n");
        for (String importStmt : importStmts) code.append("import ").append(importStmt).append(";\r\n");
        for (String modifier : classModifiers) code.append(modifier).append(" ");
        code.append("class ").append(name).append(" {\r\n");

        for (String field : fields) code.append("\t").append(field).append(";");
        for (String method : methods) code.append(method);

        code.append("\r\n}");
        System.out.println(code.toString());

        // Map<String, byte[]> results;
        // JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
        // MemoryJavaFileManager manager = new MemoryJavaFileManager(standardJavaFileManager);
        // JavaFileObject javaFileObject = manager.makeStringSource("MullogProxy", "");
        // CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
        // if (task.call()) results = manager.getClassBytes();
        return null;
    }

}