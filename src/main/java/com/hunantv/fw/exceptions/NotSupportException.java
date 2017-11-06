package com.hunantv.fw.exceptions;

public class NotSupportException extends RuntimeException {

    private static final long serialVersionUID = 3943723892229201867L;

    public NotSupportException() {
        super();
    }

    public NotSupportException(String msg) {
        super(msg);
    }

    public NotSupportException(Exception ex) {
        super(ex);
    }
}
