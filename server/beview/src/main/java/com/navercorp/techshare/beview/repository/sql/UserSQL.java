package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by Naver on 2017. 1. 11..
 */
public class UserSQL {
	public static final String USER_INSERT = "INSERT INTO user VALUES (?, ?, ?)";
	public static final String USER_SELECT = "SELECT * FROM user WHERE id = ?";
	public static final String USER_CHECK = "SELECT * FROM user WHERE id = ? and password = ?";
}
