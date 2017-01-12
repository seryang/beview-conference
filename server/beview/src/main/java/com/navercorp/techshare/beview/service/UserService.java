package com.navercorp.techshare.beview.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.net.httpserver.HttpContext;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.UserDao;

/**
 * Created by Naver on 2017. 1. 10..
 */
@Service
public class UserService {

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserDao userDao;

	public AjaxResponse insertUser(User user) {
		if (userDao.getUser(user.getId()) != null) {
			throw new AuthorizationException("존재하는 ID입니다.");
		}
		userDao.insertUser(user);
		return new AjaxResponse();
	}

	public AjaxResponse loginUser(User user) {
		User existUser = userDao.getUser(user);

		if (existUser == null) {
			throw new AuthorizationException("ID 또는 Password가 잘못되었습니다.");
		}

		Cookie cookie = new Cookie("id", existUser.getId());
		Cookie cookie1 = new Cookie("password", existUser.getPassword());

		final int MAX_AGE = 24 * 60 * 60;

		// CORS에 따른 Header값 보이지 않는 문제로 인해 설정해야함
		response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
		cookie.setMaxAge(MAX_AGE);
		cookie1.setMaxAge(MAX_AGE);
		cookie.setPath("/");
		cookie1.setPath("/");
		response.addCookie(cookie);
		response.addCookie(cookie1);

		return new AjaxResponse();
	}

	public AjaxResponse checkEmail(String email) {
		if (userDao.getUser(email) != null) {
			throw new AuthorizationException("존재하는 ID입니다.");
		}
		return new AjaxResponse();
	}
}