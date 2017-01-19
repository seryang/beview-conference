package com.navercorp.techshare.beview.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Speaker {
	private Integer idx;
	@NotNull
	private String name;
	private String img;
	@NotNull
	private String email;
	@NotNull
	private String phone;
	private String description;
	private Integer sessionIdx;
	private String sessionName;

	public Speaker() {
	}

	public Speaker(Integer idx, String name, String img, String email, String phone, String description,
		Integer sessionIdx) {
		this.idx = idx;
		this.name = name;
		this.img = img;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.sessionIdx = sessionIdx;
	}

	public Speaker convertData(Speaker updateData) {
		this.name = updateData.getName();
		this.img = updateData.getImg();
		this.email = updateData.getEmail();
		this.phone = updateData.getPhone();
		this.description = updateData.getDescription();
		return this;
	}
}
