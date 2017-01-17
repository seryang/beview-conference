package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by Naver on 2017. 1. 17..
 */
public class FavoriteSQL {
	public static final String FAVORITE_INSERT = "INSERT INTO favorite VALUES (?, ?)";
	public static final String FAVORITE_DELETE = "DELETE from favorite WHERE user_id = ? and session_idx = ?";
}
