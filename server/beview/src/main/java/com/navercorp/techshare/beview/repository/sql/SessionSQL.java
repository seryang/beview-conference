package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by Naver on 2017. 1. 12..
 */
public class SessionSQL {
	public static final String SESSION_INSERT = "INSERT INTO session VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
	public static final String SESSION_CHECK = "SELECT * FROM session INNER JOIN track on session.track_idx = ? where session.name = ?";
	public static final String SESSION_SELECT = "SELECT * FROM session WHERE idx = ?";
	public static final String SESSION_UPDATE = "UPDATE session SET name = ?, description = ?, startDate = ?, endDate = ?, file = ?, track_idx = ?, speaker_idx = ? where idx = ?";
	public static final String SESSION_SELECT_ALL = "SELECT * FROM session";
	public static final String SESSION_DELETE = "DELETE from session WHERE idx = ?";
}
