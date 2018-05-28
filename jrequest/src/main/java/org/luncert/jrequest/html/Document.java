package org.luncert.jrequest.html;

public class Document {

    private String raw;

    private HTMLNode rootNode;

    public Document(String raw) {
        this.raw = raw;
    }

    public void setRootNode(HTMLNode rootNode) { this.rootNode = rootNode; }
    
}