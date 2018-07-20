package org.luncert.springconfigurer;

public class ConfigurerException extends Exception {

	private static final long serialVersionUID = 1125468381497710211L;

    public ConfigurerException() {
        super();
    }

    public ConfigurerException(String message) {
        super(message);
    }

    public ConfigurerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurerException(Throwable cause) {
        super(cause);
    }

    protected ConfigurerException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}