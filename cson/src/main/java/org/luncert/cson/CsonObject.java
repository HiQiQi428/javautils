package org.luncert.cson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public final class CsonObject {

    private static class Parser {

        static int row, col;
    
        public static CsonObject parse(String content) {
            CsonObject ret = new CsonObject();
            CsonObject tmp = ret;
            
            row = col = 0;
            char[] seqs = content.toCharArray();
            int i = 0, limit = content.length(), depth = 0;
            while (i < limit) {
                char c = content.charAt(i);
                if (c == '\t') {
                    depth++;
                }
                else if (beLetter(c)) {
                    String key = nextKey(content, i);
                }
                else {
                    i++;
                }
            }
    
            return null;
        }
    
        private static boolean beLetter(char c) {
            return 65 <= c && c <= 90 || 97 <= c && c <= 122;
        }
    
        private static boolean beValidTokenChar(char c) {
            return beLetter(c) || c == '_' || c == '-' || c == '@' || c == '#' || c == '$';
        }
    
        private static String nextKey(String src, int start) {
            int i = start + 1;
            int limit = src.length();
            for (; i < limit && beValidTokenChar(src.charAt(i)); i++);
            if (i == limit) throw new CsonException("end of file");
            if (src.charAt(i) != ':') throw new CsonException(MessageFormat.format("invalid character at (%d, %d)", row, col));
            else {
                i++; // skip ':'
                return src.substring(start, i);
            }
        }

    }

	public void setProperty(String path, String value) {
        if (path == null || path.equals(":")) return;

        StringTokenizer tokenizer = new StringTokenizer(path, ":");
        JSONObject tmp = configs;

        while (true) {
            String name = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens()) {
                tmp.put(name, value);
                return;
            }
            else {
                if (tmp.getJSONObject(name) == null) tmp = tmp.getJSONObject(name);
                else {
                    List<String> tokens = new ArrayList<>();
                    while (tokenizer.hasMoreTokens()) tokens.add(tokenizer.nextToken());
                    Object v = value;
                    for (int i = tokens.size() - 1; i >= 0; i--) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put(tokens.get(i), v);
                        v = jsonObj;
                    }
                    tmp.put(name, v);
                    return;
                }
            }
        }
	}

    public String getString(String path) {
        if (path == null || path.equals(":")) return null;

        StringTokenizer tokenizer = new StringTokenizer(path, ":");
        JSONObject tmp = configs;
        while (true) {
            String name = tokenizer.nextToken();
            if (tokenizer.hasMoreTokens()) {
                tmp = tmp.getJSONObject(name);
                if (tmp == null) return null;
            }
            else return tmp.getString(name);
        }
    }

    public int getInt(int index) {
        return 0;
    }

    public String serialize() {
        return null;
    }

    public <T> T deserialize(Class<T> clazz) {
        return null;
    }

    public static CsonObject parse(File file) throws IOException {
        return parse(new FileInputStream(file));
    }

    public static CsonObject parse(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) buffer.append(line).append("\n");
        reader.close();
        return parse(buffer.toString());
    }

    public static CsonObject parse(String content) {
        return Parser.parse(content);
    }

}