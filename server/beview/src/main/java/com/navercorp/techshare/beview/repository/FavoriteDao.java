package com.navercorp.techshare.beview.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.model.Favorite;
import com.navercorp.techshare.beview.repository.sql.FavoriteSQL;

/**
 * Created by Naver on 2017. 1. 17..
 */
@Repository
public class FavoriteDao extends ParentDao {
	private BeanPropertyRowMapper<Favorite> favoriteMapper = BeanPropertyRowMapper.newInstance(Favorite.class);

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

	public Favorite selectFavoriteById(String userId, Integer sessionIdx) {
		try {
			return jdbcTemplate.queryForObject(FavoriteSQL.FAVORITE_SELECT, favoriteMapper, userId, sessionIdx);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
