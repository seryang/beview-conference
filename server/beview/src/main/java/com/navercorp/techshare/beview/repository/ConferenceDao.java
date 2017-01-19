package com.navercorp.techshare.beview.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.Utils.Pagination;
import com.navercorp.techshare.beview.model.Conference;
import com.navercorp.techshare.beview.repository.sql.ConferenceSQL;

/**
 * Created by Naver on 2017. 1. 12..
 */
@Repository
public class ConferenceDao extends ParentDao {

	private BeanPropertyRowMapper<Conference> conferenceRowMapper = BeanPropertyRowMapper.newInstance(Conference.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// 컨퍼런스 유무 체크 (생성전 체크)
	public Conference isConferenceById(String id) {
		try {
			return jdbcTemplate.queryForObject(ConferenceSQL.CONFERENCE_CHECK_BY_ID, conferenceRowMapper,
				id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 컨퍼런스 유무 체크 (업데이트 전 체크)
	public Conference isConferenceById(String id, Integer idx) {
		try {
			return jdbcTemplate.queryForObject(ConferenceSQL.CONFERENCE_CHECK_BY_ID_BEFORE_UPDATE, conferenceRowMapper,
				id, idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 컨퍼런스 삭제
	public Integer deleteConference(Integer idx) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_DELETE, idx);
	}

	// 컨퍼런스 정보
	public Conference isConferenceByIdx(Integer idx) {
		try {
			return jdbcTemplate.queryForObject(ConferenceSQL.CONFERENCE_SELECT_BY_IDX, conferenceRowMapper,
				idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 컨퍼런스 생성
	public Integer insertConference(Conference conference) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_INSERT, conference.getId(), conference.getName(),
			conference.getStartDate(), conference.getEndDate(), conference.getLocation());
	}

	// 컨퍼런스 전체 조회
	public List<Conference> selectAllConference(Integer page) {
		try {
			String selectSql = ConferenceSQL.CONFERENCE_SELECT_ALL;

			if (page != null) {
				selectSql = buildPageSQL(selectSql);
				return jdbcTemplate.query(selectSql, conferenceRowMapper, Pagination.getStart(page), Pagination.getEnd());
			} else {
				return jdbcTemplate.query(selectSql, conferenceRowMapper);
			}

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	// 컨퍼런스 수정
	public Integer updateConference(Conference conference) {
		return jdbcTemplate.update(ConferenceSQL.CONFERENCE_UPDATE, conference.getId(), conference.getName(),
			conference.getStartDate(), conference.getEndDate(), conference.getLocation(), conference.getIdx());
	}

}
