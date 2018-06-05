package org.luncert.datastruct.FibHeap;

/**
 * 斐波那契堆节点
 */
public class FibNode <T> {
    
    private FibNode<T> right;

    private FibNode<T> left;

    private FibNode<T> parent;

    private FibNode<T> child;

    private boolean mark;

    private int degree;

    private int key;

    private T value;

    public FibNode(int key, T value) {
        right = left = this;
        parent = child = null;
        mark = false;
        degree = 0;
        this.key = key;
        this.value = value;
    }

    /**
     * 如果节点自成环，则返回true
     */
    public boolean beSingle() { return right == this && left == this; }

    public void right(FibNode<T> right) { this.right = right; }

    public FibNode<T> right() { return right; }

    public void left(FibNode<T> left) { this.left = left; }

    public FibNode<T> left() { return left; }

    public void parent(FibNode<T> parent) { this.parent = parent; }

    public FibNode<T> parent() { return parent; }

    public void child(FibNode<T> child) { this.child = child; }

    public FibNode<T> child() { return child; }

    public void degree(int degree) { this.degree = degree; }

    public int degree() { return degree; }

    public void mark(boolean mark) { this.mark = mark; }

    public boolean mark() { return mark; }

    public void key(int key) { this.key = key; }

    public int key() { return key; }

    public void value(T value) { this.value = value; }

    public T value() { return value; }

}