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
	private String time;
	private String file;
	@NotNull
	private Integer trackIdx;
	private String trackName;

	@NotNull
	private Integer speakerIdx;
	private String speakerName;
	private Speaker speaker;
	private Boolean favorite;

	public Session() {
	}

	public Session(String name, String description, String time, String file, int trackIdx,
		int speakerIdx) {
		this.name = name;
		this.description = description;
		this.time = time;
		this.file = file;
		this.trackIdx = trackIdx;
		this.speakerIdx = speakerIdx;
	}

	public Session(int idx, String name, String description, String file, int trackIdx,
		int speakerIdx) {
		this.idx = idx;
		this.name = name;
		this.description = description;
		this.time = time;
		this.file = file;
		this.trackIdx = trackIdx;
		this.speakerIdx = speakerIdx;
	}

	public Session convertData(Session updateData) {
		this.name = updateData.getName();
		this.description = updateData.getDescription();
		this.time = updateData.getTime();
		this.file = updateData.getFile();
		this.trackIdx = updateData.getTrackIdx();
		this.speakerIdx = updateData.getSpeakerIdx();
		return this;
	}
}
