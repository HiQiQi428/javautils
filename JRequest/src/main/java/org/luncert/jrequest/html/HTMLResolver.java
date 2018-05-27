package org.luncert.jrequest.html;

import java.util.Properties;
import java.util.Stack;

public class HTMLResolver {

    private static final String WORDS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

    private static final char TAG_START = '<';

    private static final char TAG_END = '>';

    private static final String PROP_BOUND = "\"'";

    private static final char PROP_PAIR = '=';

    private int cursor;

    public HTMLResolver() {

    }

    private boolean in(String target, char parttern) { return target.indexOf(parttern) >= 0; }

    public void resolve(String raw) {
        Document doc = new Document(raw);
        HTMLNode rootNode = new HTMLNode(doc);
        doc.setRootNode(rootNode);

        // Stack<Integer> stack = new Stack<Integer>();

        Properties props = new Properties();
        cursor = 0;
        for (int limit = raw.length(); cursor < limit; cursor++) {
            if (raw.charAt(cursor) == TAG_START) {
                while (cursor < limit && raw.charAt(cursor) != TAG_END) {
                    int i = cursor;
                    // crop prop name
                    while (cursor < limit && in(WORDS, raw.charAt(cursor))) cursor++;
                    String key = raw.substring(i, cursor);
                    // crop prop value
                    if (raw.charAt(cursor) == PROP_PAIR) {
                        while (cursor < limit && in(PROP_BOUND, raw.charAt(cursor))) cursor++;
                        i = cursor;
                        while (cursor < limit && !in(PROP_BOUND, raw.charAt(cursor))) cursor++;
                        props.put(key, raw.subSequence(i, cursor));
                    }
                    else props.put(key, null);
                }
            }
        }
        System.out.println(props);
    }

}