package com.navercorp.techshare.beview.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Getter
@Setter
public class AuthorizationException extends RuntimeException  {
	private static final long serialVersionUID = -5249109031447313175L;
	private String message;

	public AuthorizationException(){}

	public AuthorizationException(String message){
		this.message = message;
	}
}
