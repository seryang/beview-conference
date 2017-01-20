package com.navercorp.techshare.beview.model;

import java.util.List;

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
public class Track {
	private Integer idx;
	@NotNull
	private String name;
	@NotNull
	private String location;
	@NotNull
	private Integer conferenceIdx;
	private String conferenceName;
	private List<Session> sessionList;

	public Track() {
	}

	public Track(Integer idx, String name, String location, Integer conferenceIdx) {
		this.idx = idx;
		this.name = name;
		this.location = location;
		this.conferenceIdx = conferenceIdx;
	}

}
