package org.luncert.cson;

class CsonSyntaxError extends RuntimeException {

    private static final long serialVersionUID = -2711499364027464853L;

    public CsonSyntaxError(String message) {
        super(message);
    }

    public CsonSyntaxError(int lineNum, int colNum, String msg) {
        super("[" + lineNum + ", " + colNum + "] " + msg);
    }

}