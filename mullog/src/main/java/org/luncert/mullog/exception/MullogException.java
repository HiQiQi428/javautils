package org.luncert.mullog.exception;

public class MullogException extends RuntimeException {

    private static final long serialVersionUID = 5840420538881903915L;

	public MullogException() {
        super();
    }

    public MullogException(String message) {
        super(message);
    }

    public MullogException(String message, Throwable cause) {
        super(message, cause);
    }

    public MullogException(Throwable cause) {
        super(cause);
    }

    protected MullogException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}