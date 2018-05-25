package org.luncert.Mullog.exception;

public class ConfigNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9021061795592103156L;

    public ConfigNotFoundException(String message) { super(message); }

    public ConfigNotFoundException(Throwable cause) { super(cause); }

}