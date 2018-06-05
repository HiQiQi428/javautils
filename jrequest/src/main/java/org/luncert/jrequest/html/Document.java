package org.luncert.jrequest.html;

import java.util.HashMap;
import java.util.Map;

import org.luncert.datastruct.Tree.ValueNode;

public class Document {

    private String raw;

    private ValueNode<Element> rootNode;

    public Document(String raw) { this.raw = raw; }

    public void setRootNode(ValueNode<Element> rootNode) { this.rootNode = rootNode; }

    /**
     * document: element*
     * element: <[ ( !stringLiteral ) | ( identifier [attributes]+>element</identifier ) ]> | stringLiteral
     * attributes: identifier { = ["|'] stringLiteral ["|'] }
     * identifier: [a-z|A-Z][a-z|A-Z|0-9|_-]*
     * stringLiteral: r/[!<>]+/
     */
    private static class AST {

        private static int id;

        private static int start;

        private static int cursor;
    
        private static int limit;
    
        private static String raw;

        public static ValueNode<Element> resolve(String raw) {
            AST.raw = raw;
            limit = raw.length();
            cursor = 0;
            id = 0;

            ValueNode<Element> rootNode = new ValueNode<>();

            ValueNode<Element> curNode;

            while (cursor < limit) {
                curNode = element();
            }

            return rootNode;
        }
    
        private static ValueNode<Element> element() {
            start = cursor;

            ValueNode<Element> node;
            Element elem;
            if (raw.charAt(cursor) == '<') {
                cursor++;
                if (raw.charAt(cursor) == '!') {
                    cursor++;
                    elem = new Element(text());
                    return new ValueNode<>(id++, elem);
                }
                else {
                    elem = new Element(identifier(), attributes());
                    node = new ValueNode<>(id++, elem);

                    ValueNode<Element> child;
                    while ((child = element()) != null) node.addChild(child);

                    return node;
                }
            }
            else {
                elem = new Element(text());
                node = new ValueNode<>(id++, elem);;
            }
            elem.setStartPos(start);
            elem.setEndPos(cursor);
            return node;
        }
    
        private static Map<String, String> attributes() {
            Map<String, String> ret = new HashMap<>();
            String name = null;
            while ((name = identifier()) != null) {
                if (raw.charAt(cursor) == '=') {
                    cursor++;
                    ret.put(name, string());
                }
                else ret.put(name, null);
            }
            return ret;
        }

        private static String identifier() {
            start = cursor;
            char c = raw.charAt(cursor);
            if (64 < c && c < 91 || 96 < c && c < 123) {
                while (cursor < limit && (64 < c && c < 91 || 96 < c && c < 123 || c == 95 || c == 45)) cursor++;
                return raw.substring(start, cursor);
            }
            else return null;
        }

        private static String string() {
            start = cursor;
            if ("\"'".indexOf(raw.charAt(cursor)) != -1) {
                start = cursor++;
                while (cursor < limit && "\"' />".indexOf(raw.charAt(cursor)) == -1) cursor++;
            }
            else {
                while (cursor < limit && raw.charAt(cursor) == ' ') start = cursor++;
                while (cursor < limit && " />".indexOf(raw.charAt(cursor)) == -1) cursor++;
            }
            return raw.substring(start, cursor);
        }

        private static String text() {
            start = cursor;
            while (cursor < limit && "<>".indexOf(raw.charAt(cursor)) == -1) cursor++;
            return raw.substring(start, cursor);
        }

    }


    public static Document resolve(String raw) {
        Document doc = new Document(raw);
        doc.setRootNode(AST.resolve(raw));
        return doc;
    }

}