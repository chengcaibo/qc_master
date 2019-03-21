package com.qichong.exception;

public class QCErrorException extends Exception {

	/**  */
	private static final long serialVersionUID = 1L;

	public QCErrorException() {
		super();
	}

	public QCErrorException(String message) {
		super(message);
	}

	public QCErrorException(Throwable th) {
		super(th);
	}

}
