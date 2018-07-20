package org.luncert.cson;

public class CsonBuilder {

    String source;
    int size;
    int cursor;
    int lineNum;
    int colNum;

    private boolean beLetter(char c) {
        return 65 <= c && c <= 90 || 97 <= c && c <= 122;
    }

    private boolean beNumber(char c) {
        return 48 <= c && c <= 57;
    }

    private boolean outOfLength() {
        return cursor >= size;
    }

    private char getChar() {
        if (outOfLength()) throw new CsonSyntaxError("end of source");
        else return source.charAt(cursor);
    }

    private void skipSpace() {
        while (!outOfLength() && getChar() == ' ') {
            colNum++;
            cursor++;
        }
    }

    private void skipNewLine() {
        if (outOfLength()) return;
        if (getChar() == '\n') {
            colNum = 1;
            lineNum++;
            cursor++;
        }
        else throw new CsonSyntaxError(lineNum, colNum, "invalid character");
    }

    private void skipAll() {
        char c;
        while (!outOfLength()) {
            c = getChar();
            if (c == ' ') colNum++;
            else if (c == '\t') colNum += 4;
            else if (c == '\n') {
                colNum = 1;
                lineNum++;
            }
            else break;
            cursor++;
        }
    }

    private char read(char c) {
        cursor++;
        if (c == '\t') colNum += 4;
        else if (c == '\n') {
            colNum = 1;
            lineNum++;
        }
        else colNum++;
        return c;
    }

    private String read(String value) {
        int length = value.length();
        cursor += length;
        colNum += length;
        return value;
    }

    private int readIndent() {
        int count = 0;
        char c;
        while (!outOfLength()) {
            c = getChar();
            if (c == '\t') {
                count += 4;
                colNum += 4;
            }
            else if (c == ' ') {
                count += 1;
                colNum += 1;
            }
            else break;
            cursor++;
        }
        return count;
    }

    // syntax

    private CsonArray array() {
        CsonArray csonArray = new CsonArray();
        char c = getChar();
        // checking if c is [ is not needed
        read(c);
        
        skipAll(); // skip ' ', '\t', '\n'
        while (true) {
            c = getChar();
            if (c == '\'' || c == '"') csonArray.add(string());
            else if (c == 't' || c == 'f') csonArray.add(bool());
            else if (beNumber(c)) csonArray.add(number());
            else if (c == ']') {
                read(']');
                break;
            }
            else throw new CsonSyntaxError(lineNum, colNum, "invalid character " + c);

            skipAll(); // skip ' ', '\t', '\n'
            // skip ','
            if (getChar() == ',') {
                read(c);
                skipAll();
            }
        }
        return csonArray;
    }

    private String bool() {
        if (source.substring(cursor, cursor + 4).equals("true")) return read("true");
        else if (source.substring(cursor, cursor + 5).equals("false")) return read("false");
        else throw new CsonSyntaxError(lineNum, colNum, "invalid character");
    }

    /**
     * include integer and decimal
     */
    private String number() {
        int start = cursor;
        char c = getChar();
        if (c == '+' || c == '-') {
            read(c);
            c = getChar();
        }
        // intergral part
        while (beNumber(c)) {
            read(c);
            if (outOfLength()) break;
            c = getChar();
        }
        // decimal part
        if (c == '.') {
            read(c);
            c = getChar();
            while (beNumber(c)) {
                read(c);
                if (outOfLength()) break;
                c = getChar();
            }
        }
        return source.substring(start, cursor);
    }

    private String string() {
        int start = cursor;
        char quote = getChar(), c;
        // checking if c is ' or " is not needed, so I just skip it
        read(quote);
        if (outOfLength()) throw new CsonSyntaxError("end of source");

        c = getChar();
        while (c != '"' && c != '\'' && c != '\n') {
            read(c);
            if (outOfLength()) break;
            c = getChar();
        }
        if (c == '"' || c == '\'')  {
            if (c == quote) {
                read(c);
                return source.substring(start + 1, cursor - 1);
            }
            else throw new CsonSyntaxError(lineNum, colNum, String.format("string starts with %c, but ends with %c", quote, c));
        }
        else throw new CsonSyntaxError(lineNum, colNum, String.format("%c is expected, but got %c", quote, c));
    }

    private Object value() {
        skipSpace();
        if (outOfLength()) throw new CsonSyntaxError("end of source");

        char c = getChar();
        Object ret;
        if (c == '"' || c == '\'') ret = string();
        else if (beNumber(c) || c == '+' || c == '-') ret = number();
        else if (c == 't' || c == 'f') ret = bool();
        else if (c == '[') ret = array();
        else {
            // handle empty object
            if (getChar() == '{') {
                read('{');
                skipSpace();
                if (getChar() == '}') {
                    read('}');
                    ret = new CsonObject();
                }
                else throw new CsonSyntaxError(lineNum, colNum, "invalid character");
            }
            else {
                skipNewLine();
                return object();
            }
        }
        skipSpace();
        skipNewLine();
        return ret;
    }

    private String key() {
        skipSpace();
        if (outOfLength()) throw new CsonSyntaxError("end of source");

        int start = cursor;
        char c = getChar();
        if (beLetter(c) || c == '_') {
            read(c);
            if (!outOfLength()) {
                c = source.charAt(cursor);
                while (beLetter(c) || beNumber(c) || c == '_' || c == '-') {
                    read(c);
                    if (!outOfLength()) c = source.charAt(cursor);
                    else break;
                }
            }
            return source.substring(start, cursor);
        }
        else throw new CsonSyntaxError(lineNum, colNum, "letter or _ is expected, but got " + c);
    }

    private CsonObject object() {
        CsonObject csonObject = new CsonObject();
        int indent = readIndent();
        int tmp = 0;
        while (!outOfLength()) {
            String key = key();
            char c = getChar();
            if (c == ':') {
                read(c);
                csonObject.put(key, value());
            }
            else throw new CsonSyntaxError(lineNum, colNum, "invalid character");

            tmp = cursor;
            if (readIndent() != indent) {
                cursor = tmp; // restore
                break;
            }
        }
        return csonObject;
    }

    public CsonObject build(String source) {
        this.source = source;
        size = source.length();
        cursor = 0;
        lineNum = 1;
        colNum = 1;
        return object();
    }

}