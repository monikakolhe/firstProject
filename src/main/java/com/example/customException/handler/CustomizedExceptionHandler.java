package com.example.customException.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.customException.ApplicationException;
import com.example.customException.DuplicateNameException;
import com.example.customException.ErrorDetails;
import com.example.customException.PasswordNotMatchException;
import com.example.customException.UserNotPresentException;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotPresentException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotPresentException userNotPresent,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), userNotPresent.getMessage(),
				request.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(DuplicateNameException.class)
	public final ResponseEntity<ErrorDetails> handleDuplicateNameException(DuplicateNameException userPresent,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), userPresent.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.FOUND);

	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	public final ResponseEntity<ErrorDetails> handlePasswordNotMatchException(PasswordNotMatchException passwordNotMatch,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), passwordNotMatch.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(ApplicationException.class)
	public final ResponseEntity<ErrorDetails> handleApplicationException(ApplicationException applicationExpec,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), applicationExpec.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(applicationExpec.getCode()));

	}
}
