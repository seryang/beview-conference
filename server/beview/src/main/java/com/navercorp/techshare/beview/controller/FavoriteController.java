package com.navercorp.techshare.beview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@Api(value = "찜하기 CD", description = "세션 찜하기에 대한 API")
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
	@ApiOperation("찜하기 정보 생성")
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
	@ApiOperation("찜하기 정보 삭제")
	public AjaxResponse deleteFavorite(@PathVariable Integer sessionIdx, @CookieValue(value = "id", defaultValue = "0") String userId){
		if("0".equals(userId)){
			throw new AuthorizationException(Error.AUTHORIZED_FAIL);
		}
		return favoriteService.deleteFavorite(sessionIdx, userId);
	}
}
