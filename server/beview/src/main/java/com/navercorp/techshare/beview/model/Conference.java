package com.navercorp.techshare.beview.model;

import java.sql.Date;

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
public class Conference {
	private Integer idx;
	@NotNull
	private String id;
	@NotNull
	private String name;
	@NotNull
	private Date startDate;
	@NotNull
	private Date endDate;
	@NotNull
	private String location;

	public Conference(){}

	public Conference(String id, String name, String startDate, String endDate, String location){
		this.id = id;
		this.name = name;
		this.startDate = Date.valueOf(startDate);
		this.endDate = Date.valueOf(endDate);
		this.location = location;
	}

	public Conference(int idx, String id, String name, Date startDate, Date endDate, String location) {
		this.idx = idx;
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public Conference convertData(Conference updateData) {
		this.id = updateData.getId();
		this.name = updateData.getName();
		this.startDate = updateData.getStartDate();
		this.endDate = updateData.getEndDate();
		this.location = updateData.getLocation();

		return this;
	}
}
