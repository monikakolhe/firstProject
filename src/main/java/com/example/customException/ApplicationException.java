package com.example.customException;


public class ApplicationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	public ApplicationException(int code) {
		super();
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace, int code) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public ApplicationException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public ApplicationException(String message, int code) {
		super(message);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public ApplicationException(Throwable cause,int code) {
		super(cause);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	
	
	

}
