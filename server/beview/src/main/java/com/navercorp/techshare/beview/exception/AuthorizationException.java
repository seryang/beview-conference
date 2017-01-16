package com.navercorp.techshare.beview.exception;

import lombok.Getter;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Getter
public class AuthorizationException extends RuntimeException  {
	private String message;

	public AuthorizationException(Error validError){
		this.message = validError.getDescription();
	}
}
