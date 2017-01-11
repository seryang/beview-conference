package com.navercorp.techshare.beview.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.model.User;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Service
public class AuthService {

	@Autowired
	private HttpServletRequest httpRequest;

	@Autowired
	private HttpServletResponse httpResponse;

	public User loginCheck() {
		User user = getSignUser();
		return user == null ? null : user;
	}

	public User getSignUser() {
		Map<String, String> cookies = getCookie();
		return !cookies.isEmpty() ? new User(cookies.get("id"), cookies.get("password")) : null;
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
