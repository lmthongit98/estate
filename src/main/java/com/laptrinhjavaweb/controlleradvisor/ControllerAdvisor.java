package com.laptrinhjavaweb.controlleradvisor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.laptrinhjavaweb.dto.ResponseDTO;
import com.laptrinhjavaweb.myexception.FieldRequiredException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(FieldRequiredException.class)
	public ResponseEntity<ResponseDTO> handleFieldRequiredException(FieldRequiredException ex, WebRequest request) {
		ResponseDTO result = new ResponseDTO();
		List<String> errors = new ArrayList<String>();
		errors.add(ex.getMessage());
		result.setErrors(errors);
		return new ResponseEntity<ResponseDTO>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
