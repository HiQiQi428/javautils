package org.luncert.jrequest.html;

import java.util.Properties;
import java.util.Stack;

import org.luncert.mullog.Mullog;

public class HTMLResolver {

    private static final String WORDS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

    private static final char TAG_START = '<';

    private static final char TAG_END = '>';

    private static final String PROP_VALUE_END = "\"' >";

    private static final char PROP_SPLIT = '=';

    private static final char SPACE = ' ';

    public HTMLResolver() {

    }

    private boolean in(String target, char parttern) { return target.indexOf(parttern) >= 0; }

    public void resolve(String raw) {
        Document doc = new Document(raw);
        HTMLNode rootNode = new HTMLNode(doc);
        doc.setRootNode(rootNode);
    }

}