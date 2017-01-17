package com.navercorp.techshare.beview.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.repository.sql.FavoriteSQL;

/**
 * Created by Naver on 2017. 1. 17..
 */
@Repository
public class FavoriteDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Integer createFavorite(Integer sessionIdx, String id) {
		return jdbcTemplate.update(FavoriteSQL.FAVORITE_INSERT, id, sessionIdx);
	}

	public Integer deleteFavorite(Integer sessionIdx, String id) {
		return jdbcTemplate.update(FavoriteSQL.FAVORITE_DELETE, id, sessionIdx);
	}
}
