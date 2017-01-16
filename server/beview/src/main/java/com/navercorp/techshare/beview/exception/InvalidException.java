package com.navercorp.techshare.beview.exception;

import lombok.Getter;

/**
 * Created by Naver on 2017. 1. 16..
 */
@Getter
public class InvalidException extends RuntimeException {
	private String message;

	public InvalidException(){}

	public InvalidException(Error invalid) {
		this.message = invalid.getDescription();
	}
}