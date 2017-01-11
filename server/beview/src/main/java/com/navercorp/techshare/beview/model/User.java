package com.navercorp.techshare.beview.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 10..
 */

@Getter
@Setter
public class User {

	@Size(max = 50)
	@Pattern(regexp = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
	private String id;

	@Size(min = 6)
	private String password;

	private Boolean isFirst;

	public User() {
	}

	public User(String id, String password) {
		this.id = id;
		this.password = password;
		this.isFirst = false;
	}

	@Override
	public String toString() {
		return "User{" +
			"id='" + id + '\'' +
			", password='" + password + '\'' +
			", isFirst=" + isFirst +
			'}';
	}
}
