package com.example.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( HttpStatus.NOT_FOUND)
public class UserNotPresentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private int code;

	public UserNotPresentException(int code) {
		super();
		this.code=code;
		// TODO Auto-generated constructor stub
	}

	public UserNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,int code) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public UserNotPresentException(String message, Throwable cause,int code) {
		super(message, cause);
		this.code= code;
		// TODO Auto-generated constructor stub
	}
	public UserNotPresentException(String message ) {
		super(message);
		// TODO Auto-generated constructor stub
	}


	public UserNotPresentException(String message,int code ) {
		super(message);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	public UserNotPresentException(Throwable cause, int code) {
		super(cause);
		this.code= code;
		// TODO Auto-generated constructor stub
	}

	
}
