package com.navercorp.techshare.beview.model.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 11..
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AjaxResponse {
	private String message;
	private Map<String, Object> data;

	public AjaxResponse() {
	}

	public AjaxResponse(Boolean message){
		this.message = String.valueOf(message);
	}

	public AjaxResponse(String message) {
		this.message = message;
	}

	public AjaxResponse(Map<String, Object> data) {
		this.data = data;
	}

	public AjaxResponse(String message, Map<String, Object> data) {
		this.message = message;
		this.data = data;
	}
}