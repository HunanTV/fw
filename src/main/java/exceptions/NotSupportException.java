package exceptions;

public class NotSupportException extends RuntimeException {

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
