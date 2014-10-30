package exceptions;

public class NotSupportException extends RuntimeException {

	public NotSupportException() {
		super();
	}

	public NotSupportException(Exception ex) {
		super(ex);
	}
}
