package com.navercorp.techshare.beview.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Naver on 2017. 1. 10..
 */
@RequestMapping("/test")
@RestController
public class TestController {

	@RequestMapping("")
	public String hello() {
		return "hello, world";
	}
}
