package com.navercorp.techshare.beview.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.navercorp.techshare.beview.model.User;
import com.navercorp.techshare.beview.repository.sql.UserSQL;

/**
 * Created by Naver on 2017. 1. 10..
 */
@Repository
public class UserDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Integer insertUser(User user) {
		return jdbcTemplate.update(UserSQL.USER_INSERT, user.getId(), user.getPassword(), user.getIsFirst());
	}

	public User getUser(String id) {
		try {
			return jdbcTemplate.queryForObject(UserSQL.USER_SELECT, new UserMapper(), id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public User getUser(User user) {
		try {
			return jdbcTemplate.queryForObject(UserSQL.USER_CHECK, new UserMapper(), user.getId(), user.getPassword());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	private class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getString("id"), rs.getString("password"));
		}
	}
}
