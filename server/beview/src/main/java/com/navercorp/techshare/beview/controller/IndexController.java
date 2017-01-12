package com.navercorp.techshare.beview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Controller
@RequestMapping("/uploadTest")
public class IndexController {

	@RequestMapping("")
	public String index(){
		return "index";
	}
}
