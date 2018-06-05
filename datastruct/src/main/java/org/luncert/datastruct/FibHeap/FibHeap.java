package org.luncert.datastruct.FibHeap;

import java.util.ArrayList;
import java.util.List;

/**
 * 斐波那契堆
 */
public class FibHeap<T> {

    // 根链表
    private FibNode<T> root;

    // 最小元素
    private FibNode<T> minimum;

    // 堆总节点数
    private int size;

    private void addNodeToRootList(FibNode<T> node) {
        FibNode<T> right = root.right();
        // root - node - root.right
        root.right(node);
        node.left(root);

        right.left(node);
        node.right(right);
    }

    private void delNodeFromRootList(FibNode<T> node) {
        if (size == 1) root = null;
        else {
            FibNode<T> right = node.right(), left = node.left();
            
            right.left(left);
            left.right(right);
            // set root node
            if (root == node) {
                if (!root.beSingle()) {
                    root = root.right();
                }
                else root = null;
            }
        }
    }

    private void addNodeAsChild(FibNode<T> node, FibNode<T> parent) {
        // make another a child of node, incrementing node.degree
        node.parent(parent);
        FibNode<T> rootNode = parent.child();
        if (rootNode != null) {
            FibNode<T> right = rootNode.right();

            rootNode.right(node);
            node.left(rootNode);
    
            right.left(node);
            node.right(right);
        }
        else {
            parent.child(node);
            node.right(node);
            node.left(node);
        }
        parent.degree(parent.degree() + 1);
    }

    /**
     * 合并，保持堆性质
     */
    private void consolidate() {
        if (size <= 1) return;
        else {
            // 推论：一个n个节点的斐波那契数列中任意节点的最大度数D(n)=O(lg(n)), but...
            // int limit = ((int)Math.floor(Math.log(size))) + 1;
            int limit = size;
            List<FibNode<T>> A = new ArrayList<>();
            for (int i = 0; i < limit; i++) A.add(i, null);

            FibNode<T> node = root;
            FibNode<T> tmp, k;
            do {
                int degree = node.degree();
                while ((k = A.get(degree)) != null) {
                    if (node.key() > k.key()) {
                        // exchange
                        tmp = node;
                        node = k;
                        k = tmp;
                    }
                    // move k to node's child list
                    delNodeFromRootList(k);
                    addNodeAsChild(k, node);
                    // set k.mark
                    k.mark(false);
                    A.set(degree, null);
                    degree++;
                }
                A.set(degree, node);
            } while((node = node.right()) != root);
            // set minimum
            minimum = null;
            for (int i = 0; i < limit; i++) {
                if ((tmp = A.get(i)) != null) {
                    if (minimum == null) minimum = tmp;
                    else if (tmp.key() < minimum.key()) minimum = tmp;
                }
            }
        }
    }

    private void cut(FibNode<T> node, FibNode<T> parent) {
        // remove node from parent's child list
        node.right().left(node.left());
        node.left().right(node.right());
        // decrementing parent.degree
        parent.degree(parent.degree() - 1);
        // add node to root list of heap
        addNodeToRootList(node);
        node.parent(null);
        node.mark(false);
    }
    
    /**
     * 级联切断
     */
    private void cascadingCut(FibNode<T> node) {
        FibNode<T> parent = node.parent();
        if (parent != null) {
            if (!node.mark()) node.mark(true);
            else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }

    }

    public void insert(int key, T value) {
        FibNode<T> node = new FibNode<>(key, value);
        // no root
        if (root == null) minimum = root = node;
        else {
            // add node into root list
            addNodeToRootList(node);
            // set min node
            if (key < minimum.key()) minimum = node;
        }
        size++;
    }

    public FibNode<T> extractMin() {
        FibNode<T> z = minimum;
        if (z != null) {
            // add children of minimum into root list
            FibNode<T> childRoot = z.child(), child = childRoot, next;
            if (child != null) {
                do {
                    next = child.right();
                    addNodeToRootList(child);
                    child.parent(null);
                } while(next != childRoot);
            }
            // remove minimum from root list
            delNodeFromRootList(z);
            // consolidate
            consolidate();
            // set null
            z.right(null);
            z.left(null);
            z.degree(0);
            z.child(null);
        }
        size--;
        return z;
    }

    public void unionWith(FibHeap<T> aheap) {
        assert(aheap.root != null);
        if (root == null) {
            size = aheap.size;
            root = aheap.root;
            minimum = aheap.minimum;
        }
        else {
            // concatenate the root list
            FibNode<T> aroot = aheap.root,
                    right = root.right(),
                    left = aroot.left();

            root.right(aroot);
            aroot.left(root);

            left.right(right);
            right.left(left);
            // set minimum
            if (minimum.key() < aheap.minimum.key()) minimum = aheap.minimum;
            size += aheap.size;
        }
        aheap = null;
    }

    /**
     * @Param oldKey: old key value
     * @Param newkey: new key value
     */
    public void decreaseKey(FibNode<T> node, int newkey) {
        if (node.key() < newkey) throw new RuntimeException("new key connot smaller than before");
        node.key(newkey);
        FibNode<T> parent = node.parent();
        // 保证最小堆序
        if (parent != null && node.key() < parent.key()) {
            cut(node,parent);
            cascadingCut(parent);
        }
        if (node.key() < minimum.key()) minimum = node;
    }

    public void delete(FibNode<T> node) {
        // set node.key smaller than minimum.key
        decreaseKey(node, minimum.key() - 1);
        // remove node from root list
        extractMin();
    }

    // getter & setter
    
    public int size() { return size; }

    public FibNode<T> root() { return root; }

    public FibNode<T> minimum() { return minimum; }

}