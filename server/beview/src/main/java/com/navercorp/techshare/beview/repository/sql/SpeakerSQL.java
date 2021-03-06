package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by seungdols on 2017. 1. 17..
 */
public class SpeakerSQL {
	public static final String SELECT_SPEAKER = "SELECT * FROM speaker WHERE idx = ?";
	public static final String SELECT_ALL_SPEAKER = "SELECT speaker.*, session.name as sessionName FROM speaker left join session on speaker.session_idx = session.idx";
	public static final String INSERT_SPEAKER = "INSERT INTO speaker VALUES (null, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_SPEAKER = "UPDATE speaker SET name = ?, img = ?, email = ?, phone = ?, description = ?, session_idx = ? WHERE idx = ?";
	public static final String DELETE_SPEAKER = "DELETE FROM speaker WHERE idx = ?";
	public static final String SELECT_CHECK = "SELECT * FROM speaker WHERE email = ?";
	public static final String SELECT_CHECK_BEFORE_UPDATE = "SELECT * FROM speaker WHERE email = ? and idx != ?";
}
