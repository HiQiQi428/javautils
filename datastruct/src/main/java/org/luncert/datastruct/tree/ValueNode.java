package org.luncert.datastruct.tree;

import java.util.ArrayList;
import java.util.List;

public class ValueNode<K, V> {

    private K key;

    private V value;

    private ValueNode<K, V> parent;

    private List<ValueNode<K, V>> children;

    public ValueNode() {}

    public ValueNode(K key, V value) {
        this.key = key;
        this.value = value;
        children = new ArrayList<>();
    }

    public void setKey(K key) { this.key = key; }

    public void setValue(V value) { this.value = value; }

    public K getKey() { return key; }

    public V getValue() { return value; }

    public ValueNode<K, V> getParent() { return parent; }

    public List<ValueNode<K, V>> getChildren() { return children; }

    /**
     * one direction to the parent
     */
    public void setParent(ValueNode<K, V> parent) { this.parent = parent; }

    /**
     * one direction to a child
     */
    public void addChild(ValueNode<K, V> child) { children.add(child); }

    /**
     * interrelate with a child
     */
    public void interrelate(ValueNode<K, V> child) {
        addChild(child);
        child.setParent(this);
    }

    public boolean removeChild(ValueNode<K, V> child) {
        return children.remove(child);
    }

    public boolean match(K key) { return this.key.equals(key); }

}