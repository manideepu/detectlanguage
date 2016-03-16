package com.language.exception;

/**
 * This exception is thrown whenever an error is encountered during corpus learning.
 */
public class NGramException extends RuntimeException {
    public NGramException() {
        super();
    }

    public NGramException(final Throwable cause) {
        super(cause);
    }

    public NGramException(final String message) {
        super(message);
    }

    public NGramException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected NGramException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
