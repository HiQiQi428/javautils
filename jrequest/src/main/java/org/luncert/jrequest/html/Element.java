package org.luncert.jrequest.html;

import java.util.Map;

public class Element {

    private int startPos;

    private int endPos;

    private String tagName;

    private Map<String, String> properties;

    private String content;

    public Element(String content) { this.content = content; }

    public Element(String tagName, Map<String, String> properties) {
        this.tagName = tagName;
        this.properties = properties;
    }

    public void setStartPos(int startPos) { this.startPos = startPos; }

    public void setEndPos(int endPos) { this.endPos = endPos; }

    public int getStartPos() { return startPos; }

    public int getEndPos() { return endPos; }

}