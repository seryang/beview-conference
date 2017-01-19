package com.navercorp.techshare.beview.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.service.AuthService;

@Aspect
@Component
public class AuthAop {

	@Autowired
	private AuthService authService;

	@Value("${admin.id}")
	private String adminId;

	@Around("@annotation(com.navercorp.techshare.beview.annotation.Auth)")
	public Object admin(ProceedingJoinPoint joinPoint) throws Throwable {

		User loginUser = authService.cookieCheck();

		if (loginUser == null) {
			throw new AuthorizationException(Error.AUTHORIZED_FAIL);
		}

//		if(! adminId.equals(loginUser.getId()) ){
//			throw new AuthorizationException(Error.ACCESS_DENY);
//		}

		return joinPoint.proceed();
	}

//	@Around("within(com.navercorp.techshare.beview.controller.FavoriteController..*)")
//	public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
//
//		User loginUser = authService.cookieCheck();
//
//		if (loginUser == null) {
//			throw new AuthorizationException(Error.AUTHORIZED_FAIL);
//		}
//
//		return joinPoint.proceed();
//	}
}