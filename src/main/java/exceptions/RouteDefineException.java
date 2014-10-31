package exceptions;

public class RouteDefineException extends RuntimeException {

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
