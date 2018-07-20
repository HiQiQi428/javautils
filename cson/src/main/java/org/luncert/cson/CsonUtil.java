package org.luncert.cson;

public class CsonUtil {

    private static boolean beNumber(char c) { return 48 <= c && c <= 57; }

    public static boolean beNumber(String src) {
        int i = 0, limit = src.length();
        char c = src.charAt(0);

        if (c == '.') return false;
        else if (c == '+' || c == '-') {
            if (src.charAt(i + 1) == '.') return false;
            else i++;
        }
        // intergral part
        while (i < limit) {
            c = src.charAt(i++);
            if (!beNumber(c)) break;
        }
        // decimal part
        if (c == '.') {
            while (i < limit) {
                c = src.charAt(i++);
                if (!beNumber(c)) break;
            }
        }
        
        return i == limit;
    }

    public static boolean beBool(String src) { return src.equals("true") || src.equals("false"); }

}