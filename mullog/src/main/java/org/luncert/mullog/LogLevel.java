package org.luncert.mullog;

public final class LogLevel {

    public static final int DEBUG = 0;
    public static final int INFO = 1;
    public static final int WARN = 2;
    public static final int ERROR = 3;
    public static final int FATAL = 4;

    private static final String[] MULLOG_LEVEL = {"DEBUG", "INFO", "WARN", "ERROR", "FATAL"};

    public static String convertString(int loglevel) {
        return MULLOG_LEVEL[loglevel];
    }

    public static Integer convertInteger(String loglevel) {
        loglevel = loglevel.toUpperCase();
        for (int i = 0; i < MULLOG_LEVEL.length; i++) {
            if (loglevel.compareTo(MULLOG_LEVEL[i]) == 0)
                return i;
        }
        return null;
    }

}