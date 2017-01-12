package com.navercorp.techshare.beview.model;

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
	private String name;
	private String location;
	private Integer conferenceIdx;
}
