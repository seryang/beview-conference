package com.navercorp.techshare.beview.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.model.Session;
import com.navercorp.techshare.beview.repository.sql.ConferenceSQL;
import com.navercorp.techshare.beview.repository.sql.SessionSQL;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Repository
public class SessionDao {

	private JdbcTemplate jdbcTemplate;
	private BeanPropertyRowMapper<Session> sessionRowMapper = BeanPropertyRowMapper.newInstance(Session.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 세션 생성
	public Integer insertSession(Session session) {
		return jdbcTemplate.update(SessionSQL.SESSION_INSERT, session.getName(), session.getDescription(),
			session.getStartTime(), session.getEndTime(), session.getFile(), session.getTrackIdx(),
			session.getSpeakerIdx());
	}

	// 세션 유무 체크 ( track 테이블과 조인하여 체크)
	public Session isSession(String name, Integer trackIdx) {
		try {
			return jdbcTemplate.queryForObject(SessionSQL.SESSION_CHECK, sessionRowMapper,
				trackIdx, name);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 세션 조회
	public Session selectSession(Integer idx) {
		try {
			return jdbcTemplate.queryForObject(SessionSQL.SESSION_SELECT, sessionRowMapper,
				idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	// 세션 업데이트
	public Integer updateSession(Session session) {
		return jdbcTemplate.update(SessionSQL.SESSION_UPDATE, session.getName(), session.getDescription(),
			session.getStartTime(), session.getEndTime(), session.getFile(), session.getTrackIdx(),
			session.getSpeakerIdx(), session.getIdx());
	}

	// 세션 전체 조회
	public List<Session> selectAllSession() {
		try {
			return jdbcTemplate.query(SessionSQL.SESSION_SELECT_ALL, sessionRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 세션 삭제
	public Integer deleteSession(Integer idx) {
		return jdbcTemplate.update(SessionSQL.SESSION_DELETE, idx);
	}
}
