package com.navercorp.techshare.beview.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.repository.sql.ConferenceSQL;
import com.navercorp.techshare.beview.repository.sql.UserSQL;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Repository
public class ConferenceDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Conference isConference(String id) {
		try {
			return jdbcTemplate.queryForObject(ConferenceSQL.CONFERENCE_SELECT, new ConferenceDao.ConferenceMapper(),
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
			return jdbcTemplate.query(ConferenceSQL.CONFERENCE_SELECT_ALL, new ConferenceDao.ConferenceMapper());
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

	private class ConferenceMapper implements RowMapper<Conference> {
		@Override
		public Conference mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Conference(rs.getInt("idx")
				, rs.getString("id")
				, rs.getString("name")
				, rs.getDate("startDate")
				, rs.getDate("endDate")
				, rs.getString("location"));
		}
	}
}
