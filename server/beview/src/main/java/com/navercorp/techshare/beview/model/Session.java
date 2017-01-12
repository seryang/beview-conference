package com.navercorp.techshare.beview.model;

import java.sql.Time;

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
public class Session {
	private Integer idx;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Time startTime;
	@NotNull
	private Time endTime;
	private String file;
	@NotNull
	private Integer trackIdx;
	@NotNull
	private Integer speakerIdx;

	public Session() {
	}

	public Session(String name, String description, String startTime, String endTime, String file, int trackIdx,
		int speakerIdx) {
		this.name = name;
		this.description = description;
		this.startTime = Time.valueOf(startTime);
		this.endTime = Time.valueOf(endTime);
		this.file = file;
		this.trackIdx = trackIdx;
		this.speakerIdx = speakerIdx;
	}

	public Session(int idx, String name, String description, Time startTime, Time endTime, String file, int trackIdx,
		int speakerIdx) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.file = file;
		this.trackIdx = trackIdx;
		this.speakerIdx = speakerIdx;
	}
}