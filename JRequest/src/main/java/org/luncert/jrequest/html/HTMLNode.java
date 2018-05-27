package org.luncert.jrequest.html;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTMLNode {

    private Document doc;

    private int startPos;

    private int endPos;

    private HTMLNode parent;

    private List<HTMLNode> children;

    private String tagName;

    private String id;

    private String clazz;

    private Map<String, String> properties;

    public HTMLNode(Document doc) {
        this.doc = doc;
        children = new ArrayList<HTMLNode>();
    }

}