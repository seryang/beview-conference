package com.navercorp.techshare.beview.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.techshare.beview.controller.ExceptionController;
import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.UserDao;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Service
public class AuthService {

	private final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private HttpServletResponse httpResponse;

	@Autowired
	private UserDao userDao;

	public User cookieCheck() {

		Map<String, String> cookieMap = getCookie();

		logger.info("Cookie : " + cookieMap);

		if (cookieMap.isEmpty() || cookieMap.size() != 2) {
			return null;
		}

		User user = new User(cookieMap.get("id"), cookieMap.get("password"));
		return userDao.getUser(user);
	}

	public Map<String, String> getCookie() {
		Cookie[] cookies = httpRequest.getCookies();
		Map<String, String> map = new HashMap();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("id".equals(cookie.getName())) {
					map.put("id", cookie.getValue());
				} else if ("password".equals(cookie.getName())) {
					map.put("password", cookie.getValue());
				}
			}
		}
		return map;
	}

	public AjaxResponse logout() {
		Map<String, String> cookies = getCookie();

		for (String cookieKey : cookies.keySet()) {
			Cookie cookie = new Cookie(cookieKey, "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			httpResponse.addCookie(cookie);
		}

		return new AjaxResponse();
	}
}
