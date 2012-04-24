package com.github.noxan.aves.auth;

public class AuthException extends Exception {
    private static final long serialVersionUID = 7250556977195914416L;

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
