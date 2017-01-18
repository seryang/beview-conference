package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by seungdols on 2017. 1. 12..
 */
public class TrackSQL {
	public static final String INSERT_TRACK = "INSERT INTO track VALUES (null, ?, ?, ?)";
	public static final String SELECT_TRACK = "SELECT * FROM track WHERE idx = ?";
	public static final String SELECT_TRACK_ALL = "SELECT track.*, conference.name as conferenceName FROM track LEFT JOIN conference on track.conference_idx = conference.idx";
	public static final String UPDATE_TRACK = "UPDATE track SET name = ?, location = ? WHERE idx = ? AND conference_idx = ?";
	public static final String DELETE_TRACK = "DELETE FROM track WHERE idx = ?";
	public static final String IS_EXIST_TRACK = "SELECT track.idx,track.name,track.location,track.conference_idx FROM track JOIN conference on track.conference_idx = ? WHERE track.name = ?";
	public static final String IS_EXIST_TRACK_BEFORE_UPDATE = "SELECT track.idx,track.name,track.location,track.conference_idx FROM track JOIN conference on track.conference_idx = ? WHERE track.name = ? and track.idx != ?";
}
