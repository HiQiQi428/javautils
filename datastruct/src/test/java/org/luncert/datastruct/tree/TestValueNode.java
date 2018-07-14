package org.luncert.datastruct.tree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.luncert.mullog.Mullog;

@RunWith(JUnit4.class)
public class TestValueNode {

    private Mullog mullog = new Mullog();

    @Test
    public void test() {
        ValueNode<Integer, String> root = new ValueNode<>(1, "hi");
        ValueNode<Integer, String> a1 = new ValueNode<>(2, "hi!");
        root.interrelate(a1);
        mullog.info(a1.getParent().getValue());
    }

}