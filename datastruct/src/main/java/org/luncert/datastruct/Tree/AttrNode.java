package org.luncert.datastruct.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AttrNode {

    private Properties props;

    private AttrNode parent;

    private List<AttrNode> children;

    public AttrNode() { children = new ArrayList<>(); }

    public void setAttr(String name, String value) { props.put(name, value); }

    public String getAttr(String name) { return props.getProperty(name); }

    public void setParent(AttrNode parent) { this.parent = parent; }

    public void addChild(AttrNode child) { this.children.add(child); }

    public AttrNode getParent() { return parent; }

    public List<AttrNode> getChildren() { return children; }

}