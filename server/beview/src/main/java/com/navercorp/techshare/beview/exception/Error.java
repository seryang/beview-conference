package com.navercorp.techshare.beview.exception;

/**
 * Created by Naver on 2017. 1. 16..
 */
public enum Error {
	AUTHORIZED_FAIL("로그인이 필요합니다."),
	LOGIN_FAIL("ID 또는 Password가 잘못되었습니다."),
	INVALID("잘못된 입력입니다."),
	DUPLICATE("기존에 데이터가 존재합니다."),
	UPLOAD_FAIL("업로드에 실패했습니다."),
	EXIST_ID("이미 존재하는 ID입니다."),
	EXIST_NAME("이미 존재하는 이름입니다."),
	ACCESS_DENY("관리자가 아닙니다.");

	private String description;

	private Error(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}