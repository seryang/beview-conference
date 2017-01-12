package com.navercorp.techshare.beview.model;

import java.sql.Time;

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
	private String name;
	private String description;
	private Time startTime;
	private Time endTime;
	private String file;
	private Integer trackIdx;
	private Integer speakerIdx;
}
