package com.navercorp.techshare.beview.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.model.response.AjaxResponse;

/**
 * Created by Naver on 2017. 1. 16..
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

	// 찜하기
	@Auth
	@PostMapping("{sessionIdx}")
	public AjaxResponse favoriteSession(@PathVariable Integer sessionIdx){

	}

	// 찜하기 삭제
	@Auth
	@DeleteMapping("/{sessionIdx}")
	public AjaxResponse favoriteSession(@PathVariable Integer sessionIdx){

	}

	// 찜하기 전체 리스트
	@Auth
	@GetMapping()
	public AjaxResponse favoriteSession(){

	}
}
