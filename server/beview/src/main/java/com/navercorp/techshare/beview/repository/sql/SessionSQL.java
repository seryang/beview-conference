package com.navercorp.techshare.beview.repository.sql;

/**
 * Created by Naver on 2017. 1. 12..
 */
public class SessionSQL {
	public static final String SESSION_INSERT = "INSERT INTO session VALUES (null, ?, ?, ?, ?, ?, ?)";
	public static final String SESSION_CHECK = "SELECT * FROM session INNER JOIN track on session.track_idx = ? where session.name = ?";
	public static final String SESSION_CHECK_BEFORE_UPDATE = "SELECT * FROM session INNER JOIN track on session.track_idx = ? where session.name = ? and session.idx != ?";
	public static final String SESSION_SELECT = "SELECT * FROM session WHERE idx = ?";
	public static final String SESSION_UPDATE = "UPDATE session SET name = ?, description = ?, time = ?, file = ?, track_idx = ?, speaker_idx = ? where session.idx = ?";
	public static final String SESSION_SELECT_ALL = "select session.*, track.name as trackName, speaker.name as speakerName from session left join track on session.track_idx = track.idx, speaker where session.speaker_idx = speaker.idx";
	public static final String SESSION_SELECT_ALL_BY_TRACK_ID = "select session.* from session, track where session.track_idx = track.idx and track.idx = ? order by session.time asc";
	public static final String SESSION_DELETE = "DELETE from session WHERE idx = ?";
}
