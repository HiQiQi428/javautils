package org.luncert.datastruct.FibHeap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.luncert.mullog.Mullog;

@RunWith(JUnit4.class)
public class TestFibHeap {

    private FibHeap<Integer> h = new FibHeap<>();

    public void init() {
        h.insert(2, 10);
        h.insert(3, 10);
        h.insert(1, 10);
    }

    @Test
    public void test() {
        init();

        FibNode<Integer> min = h.extractMin();
        Mullog.info(min.key());

        Mullog.debug(h.size());
        Mullog.debug(h.root().key());
        Mullog.debug(h.root().beSingle());
        Mullog.debug(h.root().child().beSingle());
        Mullog.debug(h.root().child().key());
        Mullog.debug(h.root().child().parent().key());

        System.out.println();
        
        min = h.extractMin();
        Mullog.info(min.key());

        Mullog.debug(h.size());
        Mullog.debug(h.root().key());
        Mullog.debug(h.root().beSingle());
        // Mullog.debug(h.root().child().beSingle());
        // Mullog.debug(h.root().child().key());
        // Mullog.debug(h.root().child().parent().key());

        min = h.extractMin();
        Mullog.info(min.key());

        Mullog.info(h.size());
    }

    @Test
    public void testDecreaseKey() {
        init();

        h.extractMin();

        h.decreaseKey(h.root().child(), -10);

        FibNode<Integer> min = h.extractMin();
        Mullog.info(min.key());
    }

    @Test
    public void testDeleteKey() {
        init();

        h.extractMin();

        h.delete(h.root().child());

        Mullog.info(h.size());
        Mullog.info(h.root().key());
    }

}