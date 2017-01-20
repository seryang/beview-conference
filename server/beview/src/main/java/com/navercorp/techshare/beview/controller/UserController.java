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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/api/users")
@Api(value = "User API", description = "사용자 정보 API")
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
	@ApiOperation("사용자 정보 생성")
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
	@ApiOperation("사용자 정보 중복체크")
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
	@ApiOperation("사용자 정보 로그인")
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
	@ApiOperation("사용자 정보 로그아웃")
	@ResponseStatus(HttpStatus.OK)
	public AjaxResponse logout() {
		return authService.logout();
	}
}