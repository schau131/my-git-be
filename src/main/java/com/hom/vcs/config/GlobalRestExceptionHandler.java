package com.hom.vcs.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hom.vcs.util.Constants;
//2nd day code changes - commit//

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(IOException.class)
	protected ResponseEntity<Object> handleIOException(
			IOException io, WebRequest webRequest){
		 Map<String, Object> body = new LinkedHashMap<>();
	     body.put("timestamp", LocalDateTime.now());
	     body.put("status", Constants.STATUS_FAILED);
	     body.put("message", io.getMessage());

	     return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
