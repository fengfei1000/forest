package fengfei.forest.slice.exception;

public class ErrorSliceConfigException extends SliceRuntimeException {

	private static final long serialVersionUID = 1L;

	public ErrorSliceConfigException() {
		super();
	}

	public ErrorSliceConfigException(String description) {
		super(description);

	}

	public ErrorSliceConfigException(String description, Throwable throwable) {
		super(description, throwable);

	}
}
