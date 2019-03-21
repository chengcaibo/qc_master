package com.qichong.exception;

/**
 * 自定义异常：数据已存在
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-10 10:58:29
 */
public class DataAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataAlreadyExistsException() {
		super();
	}

	public DataAlreadyExistsException(String message) {
		super(message);
	}
}
