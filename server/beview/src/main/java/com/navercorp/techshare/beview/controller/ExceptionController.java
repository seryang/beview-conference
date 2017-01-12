package com.navercorp.techshare.beview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.model.response.AjaxResponse;

@RestControllerAdvice
public class ExceptionController {

	private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public AjaxResponse exception(AuthorizationException e) {
		logger.error(e.getMessage());
		return new AjaxResponse(e.getMessage());
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public AjaxResponse exception(NullPointerException e) {
		logger.error(e.getMessage());
		return new AjaxResponse(e.getMessage());
	}
}