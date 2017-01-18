package com.navercorp.techshare.beview.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.navercorp.techshare.beview.Utils.Pagination;
import com.navercorp.techshare.beview.model.Track;
import com.navercorp.techshare.beview.repository.sql.TrackSQL;

/**
 * Created by seungdols on 2017. 1. 12..
 */
@Repository
public class TrackDao {

	private JdbcTemplate jdbcTemplate;
	private BeanPropertyRowMapper<Track> trackRowMapper = BeanPropertyRowMapper.newInstance(Track.class);

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Track selectTrack(String idx) {
		try {
			return jdbcTemplate.queryForObject(TrackSQL.SELECT_TRACK, trackRowMapper, idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public void insertTrack(Track track) {
		jdbcTemplate.update(TrackSQL.INSERT_TRACK, track.getName(), track.getLocation(), track.getConferenceIdx());
	}

	public List<Track> selectTrackAllList(Integer page) {
		try {
			String SELECT_ALL_SQL = TrackSQL.SELECT_TRACK_ALL;

			if (page == null) {
				return jdbcTemplate.query(SELECT_ALL_SQL, trackRowMapper);
			} else {
				return jdbcTemplate.query(buildPageSQL(SELECT_ALL_SQL), trackRowMapper, Pagination.getStart(page),
					Pagination.getEnd());
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Track isExistTrackInConference(Integer conferenceId, String name) {
		try {
			return jdbcTemplate.queryForObject(TrackSQL.IS_EXIST_TRACK, trackRowMapper, conferenceId, name);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Track isExistTrackInConference(Integer conferenceId, String name, String idx) {
		try {
			return jdbcTemplate.queryForObject(TrackSQL.IS_EXIST_TRACK_BEFORE_UPDATE, trackRowMapper, conferenceId,
				name, idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public Integer updateTrack(String id, Track track) {
		return jdbcTemplate.update(TrackSQL.UPDATE_TRACK, track.getName(), track.getLocation(),
			id, track.getConferenceIdx());
	}

	public Integer deleteTrack(String id) {
		return jdbcTemplate.update(TrackSQL.DELETE_TRACK, id);
	}

	public List<Track> selectTrackAllListByConferenceId(Integer idx) {
		try {
			return jdbcTemplate.query(TrackSQL.SELECT_TRACK_ALL_BY_CONFERENCE_ID, trackRowMapper, idx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	//	TODO 각 DAO에서 추상화 시켜야 함
	private static String buildPageSQL(String SELECT_SQL) {
		StringBuilder sql = new StringBuilder(SELECT_SQL);

		String part = " limit ? , ?";
		sql.append(part);

		return sql.toString();
	}
}
