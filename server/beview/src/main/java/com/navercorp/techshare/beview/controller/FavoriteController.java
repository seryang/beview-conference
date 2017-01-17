package com.navercorp.techshare.beview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercorp.techshare.beview.annotation.Auth;
import com.navercorp.techshare.beview.exception.AuthorizationException;
import com.navercorp.techshare.beview.exception.Error;
import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.service.FavoriteService;

/**
 * Created by Naver on 2017. 1. 16..
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	/**
	 * [찜하기]
	 *
	 * @param sessionIdx
	 * @param userId
	 * @return AjaxResponse
	 */
	@Auth
	@PostMapping("{sessionIdx}")
	public AjaxResponse createFavorite(@PathVariable Integer sessionIdx, @CookieValue(value = "id", defaultValue = "0") String userId){
		if("0".equals(userId)){
			throw new AuthorizationException(Error.AUTHORIZED_FAIL);
		}
		return favoriteService.createFavorite(sessionIdx, userId);
	}

	/**
	 * [찜하기 삭제]
	 *
	 * @param sessionIdx
	 * @param userId
	 * @return
	 */
	@Auth
	@DeleteMapping("/{sessionIdx}")
	public AjaxResponse deleteFavorite(@PathVariable Integer sessionIdx, @CookieValue(value = "id", defaultValue = "0") String userId){
		if("0".equals(userId)){
			throw new AuthorizationException(Error.AUTHORIZED_FAIL);
		}
		return favoriteService.deleteFavorite(sessionIdx, userId);
	}
}
