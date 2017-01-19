package com.navercorp.techshare.beview.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.Utils.Pagination;
import com.navercorp.techshare.beview.model.Speaker;
import com.navercorp.techshare.beview.repository.sql.SpeakerSQL;

/**
 * Created by seungdols on 2017. 1. 17..
 */
@Repository
public class SpeakerDao extends ParentDao {

	private BeanPropertyRowMapper<Speaker> speakerMapper = BeanPropertyRowMapper.newInstance(Speaker.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Speaker selectSpeaker(String id) {
		try {
			return jdbcTemplate.queryForObject(SpeakerSQL.SELECT_SPEAKER, speakerMapper, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Speaker> selectAllSpeaker(Integer page) {
		try {

			String selectSql = SpeakerSQL.SELECT_ALL_SPEAKER;

			if (page != null) {
				selectSql = buildPageSQL(selectSql);
			}
			return jdbcTemplate.query(selectSql, speakerMapper, Pagination.getStart(page),
				Pagination.getEnd());

		} catch (
			EmptyResultDataAccessException e)

		{
			return null;
		}

	}

	public Integer updateSpeaker(String id, Speaker speaker) {
		return jdbcTemplate.update(SpeakerSQL.UPDATE_SPEAKER, speaker.getName(), speaker.getImg(), speaker.getEmail(),
			speaker.getPhone(), speaker.getDescription(), speaker.getSessionIdx(), id);
	}

	public Integer deleteSpeaker(String id) {
		return jdbcTemplate.update(SpeakerSQL.DELETE_SPEAKER, id);
	}

	public void createSpeaker(Speaker speaker) {
		jdbcTemplate.update(SpeakerSQL.INSERT_SPEAKER, speaker.getName(), speaker.getImg(),
			speaker.getEmail(), speaker.getPhone(), speaker.getDescription(), speaker.getSessionIdx());
	}

	public Speaker isExistSpeaker(Speaker speaker) {
		try {
			return jdbcTemplate.queryForObject(SpeakerSQL.SELECT_CHECK, speakerMapper, speaker.getEmail());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Speaker isExistSpeaker(Speaker speaker, String id) {
		try {
			return jdbcTemplate.queryForObject(SpeakerSQL.SELECT_CHECK_BEFORE_UPDATE, speakerMapper, speaker.getEmail(),
				id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
