package com.navercorp.techshare.beview.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.Utils.Pagination;
import com.navercorp.techshare.beview.model.Session;
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
		GeneratedKeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement statement = con.prepareStatement(SessionSQL.SESSION_INSERT,
				Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, session.getName());
			statement.setString(2, session.getDescription());
			statement.setTime(3, session.getStartTime());
			statement.setTime(4, session.getEndTime());
			statement.setString(5, session.getFile());
			statement.setInt(6, session.getTrackIdx());
			statement.setInt(7, session.getSpeakerIdx());
			return statement;
		}, holder);

		return holder.getKey().intValue();
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

	public Session isSession(String name, Integer trackIdx, Integer idx) {
		try {
			return jdbcTemplate.queryForObject(SessionSQL.SESSION_CHECK_BEFORE_UPDATE, sessionRowMapper,
				trackIdx, name, idx);
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
	public List<Session> selectAllSession(Integer page) {
		try {
			String SELECT_ALL_SQL = SessionSQL.SESSION_SELECT_ALL;

			if (page == null) {
				return jdbcTemplate.query(SELECT_ALL_SQL, sessionRowMapper);
			} else {
				return jdbcTemplate.query(buildPageSQL(SELECT_ALL_SQL), sessionRowMapper, Pagination.getStart(page),
					Pagination.getEnd());
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 세션 삭제
	public Integer deleteSession(Integer idx) {
		return jdbcTemplate.update(SessionSQL.SESSION_DELETE, idx);
	}

	//	TODO 각 DAO에서 추상화 시켜야 함
	private static String buildPageSQL(String SELECT_SQL) {
		StringBuilder sql = new StringBuilder(SELECT_SQL);

		String part = " limit ? , ?";
		sql.append(part);

		return sql.toString();
	}
}
