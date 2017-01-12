package com.navercorp.techshare.beview.model.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import com.navercorp.techshare.beview.model.Conference;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxResponse {
	private String message;
	private List data;

	public AjaxResponse() {
	}

	public AjaxResponse(String message) {
		this.message = message;
	}

	public AjaxResponse(List data) {
		this.data = data;
	}
}