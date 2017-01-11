package com.navercorp.techshare.beview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.techshare.beview.service.UserService;

/**
 * Created by Naver on 2017. 1. 10..
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public boolean insertUser(@ModelAttribute UserVo uvo){
		System.out.println("!!");
		System.out.println(uvo);
		int a = userService.insertUser(uvo);
		return a > 0;
	}
}
