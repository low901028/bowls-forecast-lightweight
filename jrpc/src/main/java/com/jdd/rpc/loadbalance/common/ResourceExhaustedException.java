package com.jdd.rpc.loadbalance.common;

public class ResourceExhaustedException extends RuntimeException {

    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    public ResourceExhaustedException(String message) {
        super(message);
    }

    public ResourceExhaustedException(Throwable cause) {
        super(cause);
    }

    public ResourceExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }
}