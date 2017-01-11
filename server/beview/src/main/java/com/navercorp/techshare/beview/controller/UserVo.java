package com.navercorp.techshare.beview.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 10..
 */

public class UserVo {
	private String id;
	private String password;
	private boolean isFirst;

	public UserVo(){}

	public UserVo(String id, String password) {
		this.id = id;
		this.password = password;
		this.isFirst = false;
	}

	@Override
	public String toString() {
		return "UserVo{" +
			"id='" + id + '\'' +
			", password='" + password + '\'' +
			'}';
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIsFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public boolean getIsFirst() {
		return isFirst;
	}
}
