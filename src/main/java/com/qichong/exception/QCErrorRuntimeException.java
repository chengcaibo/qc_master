package com.qichong.exception;

public class QCErrorRuntimeException extends RuntimeException {

	/**  */
	private static final long serialVersionUID = 1L;

	public QCErrorRuntimeException() {
		super();
	}

	public QCErrorRuntimeException(String message) {
		super(message);
	}

	public QCErrorRuntimeException(Throwable th) {
		super(th);
	}

}
