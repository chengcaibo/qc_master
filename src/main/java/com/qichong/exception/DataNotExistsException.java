package com.qichong.exception;

/**
 * 自定义异常：数据不已存在
 * 
 * @创建人 孙建雷
 * @修改人 孙建雷
 * @修改时间 2017-11-10 11:45:03
 */
public class DataNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataNotExistsException() {
		super();
	}

	public DataNotExistsException(String message) {
		super(message);
	}
}
