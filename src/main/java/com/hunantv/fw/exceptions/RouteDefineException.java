package com.hunantv.fw.exceptions;

public class RouteDefineException extends RuntimeException {

    private static final long serialVersionUID = -2588600982370265727L;

    public RouteDefineException() {
        super();
    }

    public RouteDefineException(String msg) {
        super(msg);
    }

    public RouteDefineException(Exception ex) {
        super(ex);
    }
}
