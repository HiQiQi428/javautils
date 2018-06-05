package org.luncert.datastruct.Tree;

import java.util.ArrayList;
import java.util.List;

public class ValueNode<T> {

    private int id;

    private T value;

    private ValueNode<T> parent;

    private List<ValueNode<T>> children;

    public ValueNode() {}

    public ValueNode(int id, T value) {
        this.value = value;
        children = new ArrayList<>();
    }

    public int getId() { return id; }

    public T getValue() { return value; }

    public ValueNode<T> getParent() { return parent; }

    public List<ValueNode<T>> getChildren() { return children; }

    public void setParent(ValueNode<T> parent) { this.parent = parent; }

    public void addChild(ValueNode<T> child) { children.add(child); }

    public boolean match(int id) { return this.id == id; }

}