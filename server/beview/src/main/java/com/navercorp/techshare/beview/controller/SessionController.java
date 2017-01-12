package com.navercorp.techshare.beview.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.SessionService;

/**
 * Created by Naver on 2017. 1. 12..
 */
@RestController
@RequestMapping("/api/session")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@Auth
	@PostMapping
	public AjaxResponse createSession(@RequestBody @Valid Session session, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new AuthorizationException("잘못된 입력입니다.");
		}
		return sessionService.createSession(session);
	}

	@PostMapping("/uploadFile")
	public AjaxResponse fileUpload(MultipartFile file) {
		return sessionService.uploadFile(file);
	}
}
