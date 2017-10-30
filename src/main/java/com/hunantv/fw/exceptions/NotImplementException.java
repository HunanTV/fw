package com.hunantv.fw.exceptions;

public class NotImplementException extends RuntimeException {

    private static final long serialVersionUID = 541999505175736031L;

    public NotImplementException() {
        super();
    }

    public NotImplementException(String msg) {
        super(msg);
    }

    public NotImplementException(Exception ex) {
        super(ex);
    }
}
