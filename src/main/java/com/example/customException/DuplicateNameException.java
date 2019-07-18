package com.example.customException;

public class DuplicateNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateNameException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicateNameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
