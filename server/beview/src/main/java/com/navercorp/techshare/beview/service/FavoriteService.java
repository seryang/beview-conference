package com.navercorp.techshare.beview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.model.response.AjaxResponse;
import com.navercorp.techshare.beview.repository.FavoriteDao;

/**
 * Created by Naver on 2017. 1. 17..
 */
@Service
@Transactional
public class FavoriteService {

	@Autowired
	private FavoriteDao favoriteDao;

	public AjaxResponse createFavorite(Integer sessionIdx, String id) {
		favoriteDao.createFavorite(sessionIdx, id);
		return new AjaxResponse();
	}

	public AjaxResponse deleteFavorite(Integer sessionIdx, String id) {
		favoriteDao.deleteFavorite(sessionIdx, id);
		return new AjaxResponse();
	}
}
