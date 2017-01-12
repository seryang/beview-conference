package com.navercorp.techshare.beview.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.repository.UserDao;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Service
public class AuthService {

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private HttpServletResponse httpResponse;

	@Autowired
	private UserDao userDao;

	public boolean cookieCheck() {

		Map<String, String> cookieMap = getCookie();

		if (cookieMap.isEmpty() || cookieMap.size() != 2) {
			return false;
		}

		User user = new User(cookieMap.get("id"), cookieMap.get("password"));
		return userDao.getUser(user) != null;
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

	public void logout() {
		Map<String, String> cookies = getCookie();

		for (String cookieKey : cookies.keySet()) {
			Cookie cookie = new Cookie(cookieKey, null);
			cookie.setMaxAge(0);
			httpResponse.addCookie(cookie);
		}
	}
}
