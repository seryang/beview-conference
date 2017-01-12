package com.navercorp.techshare.beview.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

	public Integer insertSession(Session session) {
		return jdbcTemplate.update(SessionSQL.SESSION_INSERT, session.getName(), session.getDescription(), session.getStartTime(), session.getEndTime(), session.getFile(), session.getTrackIdx(), session.getSpeakerIdx());
	}
}
