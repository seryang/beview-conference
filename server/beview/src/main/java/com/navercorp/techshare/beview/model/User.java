package com.navercorp.techshare.beview.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	@NotNull
	@Size(max = 50)
	@Pattern(regexp = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
	private String id;

	@NotNull
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
}
