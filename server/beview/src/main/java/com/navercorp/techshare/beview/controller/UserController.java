package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.AuthService;
import com.navercorp.techshare.beview.service.UserService;

/**
 * Created by Naver on 2017. 1. 10..
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	/**
	 * [사용자 가입]
	 *
	 * @param user
	 * @return AjaxResponse
	 */
	@PostMapping
	public AjaxResponse insertUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException(Error.INVALID);
		}
		return userService.insertUser(user);
	}

	/**
	 * [아이디 중복체크]
	 *
	 * @param email
	 * @return AjaxResponse
	 */
	@GetMapping("/check")
	public AjaxResponse checkUser(@RequestParam String email) {
		return userService.checkEmail(email);
	}

	/**
	 * [사용자 로그인]
	 *
	 * @param user
	 * @return AjaxResponse
	 */
	@PostMapping("/login")
	public AjaxResponse login(@RequestBody @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException(Error.INVALID);
		}
		return userService.loginUser(user);
	}

	/**
	 * [로그아웃]
	 */
	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	public AjaxResponse logout() {
		return authService.logout();
	}
}