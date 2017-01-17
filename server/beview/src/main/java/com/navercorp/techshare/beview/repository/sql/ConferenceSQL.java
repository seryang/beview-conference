package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by Naver on 2017. 1. 12..
 */
public class ConferenceSQL {
	public static final String CONFERENCE_SELECT_BY_ID = "SELECT * FROM conference WHERE id = ?";
	public static final String CONFERENCE_SELECT_BY_IDX = "SELECT * FROM conference WHERE idx = ?";
	public static final String CONFERENCE_SELECT_ALL = "SELECT * FROM conference";
	public static final String CONFERENCE_INSERT = "INSERT INTO conference VALUES (null, ?, ?, ?, ?, ?)";
	public static final String CONFERENCE_UPDATE = "UPDATE conference SET id = ?, name = ?, startDate = ?, endDate = ?, location = ? where idx = ?";
	public static final String CONFERENCE_DELETE = "DELETE from conference WHERE idx = ?";
}
