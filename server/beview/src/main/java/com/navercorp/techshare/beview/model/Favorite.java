package com.navercorp.techshare.beview.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 18..
 */
@Getter
@Setter
public class Favorite {
	private String userId;
	private Integer sessionIdx;

	public Favorite() {
	}

	public Favorite(String userId, Integer sessionIdx) {
		this.userId = userId;
		this.sessionIdx = sessionIdx;
	}
}
