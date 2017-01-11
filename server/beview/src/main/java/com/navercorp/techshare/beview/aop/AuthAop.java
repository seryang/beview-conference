package com.navercorp.techshare.beview.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.service.AuthService;

@Aspect
@Component
public class AuthAop {

	@Autowired
	AuthService authService;

	@Around("@annotation(com.navercorp.techshare.beview.annotation.Auth)")
	public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {

		if (authService.loginCheck() == null) {
			throw new AuthorizationException("로그인이 필요합니다.");
		}

		return joinPoint.proceed();
	}
}