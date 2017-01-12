package com.navercorp.techshare.beview.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.repository.sql.ConferenceSQL;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Repository
public class ConferenceDao {

	private JdbcTemplate jdbcTemplate;
	private BeanPropertyRowMapper<Conference> conferenceRowMapper = BeanPropertyRowMapper.newInstance(Conference.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Conference isConference(String id) {
		try {
			return jdbcTemplate.queryForObject(ConferenceSQL.CONFERENCE_SELECT, conferenceRowMapper,
				id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Integer insertConference(Conference conference) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_INSERT, conference.getId(), conference.getName(),
			conference.getStartDate(), conference.getEndDate(), conference.getLocation());
	}

	public List<Conference> selectAllConference() {
		try {
			return jdbcTemplate.query(ConferenceSQL.CONFERENCE_SELECT_ALL, conferenceRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Integer updateConference(Conference conference) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_UPDATE, conference.getId(), conference.getName(),
			conference.getStartDate(), conference.getEndDate(), conference.getLocation(), conference.getIdx());
	}

	public Integer deleteConference(String id) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_DELETE, id);
	}
}
