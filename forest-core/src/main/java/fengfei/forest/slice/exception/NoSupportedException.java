package fengfei.forest.slice.exception;

public class NoSupportedException extends SliceRuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSupportedException() {
		super();
	}

	public NoSupportedException(String description) {
		super(description);

	}

	public NoSupportedException(String description, Throwable throwable) {
		super(description, throwable);

	}

}
