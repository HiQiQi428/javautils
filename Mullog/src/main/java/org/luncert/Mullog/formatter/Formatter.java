package org.luncert.Mullog.formatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * %t: timestamp
 * %l: log level
 * %m: method name
 * %c: class name
 * %M: message
 */
public class Formatter {

    private static final String[] MULLOG_LEVEL = {"INFO", "WARN", "DEBUG", "ERROR", "FATAL"};

    private static final String RE_FORMAT_STRING = "%[t|l|m|c|M]";

    private List<String> patterns;

    private StackTraceElement getStackTraceElement() {
        return (new Throwable()).getStackTrace()[5];
    }

    public Formatter(String formatString) {
        patterns = new ArrayList<>();
        String[] tmp = formatString.split(RE_FORMAT_STRING);
        int cursor = 0;
        String startPattern = formatString.substring(0, 2);
        if (startPattern.matches(RE_FORMAT_STRING)) {
            patterns.add(startPattern);
            cursor += 2;
        }
        for (String s : tmp) {
            cursor += s.length();
            if (s.length() > 0) {
                patterns.add(s);
                patterns.add(formatString.substring(cursor, cursor + 2));
                cursor += 2;
            }
        }
    }

    public String format(int logLevel, String message) {
        StringBuilder data = new StringBuilder();
        StackTraceElement stackTraceElement = getStackTraceElement();
        for (String pattern : patterns) {
            if (pattern.matches(RE_FORMAT_STRING)) {
                if (pattern.charAt(1) == 't') data.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                else if (pattern.charAt(1) == 'l') data.append(MULLOG_LEVEL[logLevel]);
                else if (pattern.charAt(1) == 'm') data.append(stackTraceElement.getMethodName());
                else if (pattern.charAt(1) == 'c') data.append(stackTraceElement.getClassName());
                else if (pattern.charAt(1) == 'M') data.append(message);
            }
            else data.append(pattern);
        }
        return data.toString();
    }

}