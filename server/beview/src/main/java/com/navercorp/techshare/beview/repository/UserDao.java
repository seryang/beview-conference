package com.navercorp.techshare.beview.repository;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.navercorp.techshare.beview.controller.UserVo;

/**
 * Created by Naver on 2017. 1. 10..
 */
@Repository
public class UserDao {

	private NamedParameterJdbcOperations db;
	private SimpleJdbcInsertOperations productInsertion;
	private RowMapper<UserVo> productMapper;

	@Autowired
	public UserDao(DataSource dataSource) {
		this.db = new NamedParameterJdbcTemplate(dataSource);

		this.productInsertion = new SimpleJdbcInsert(dataSource)
			.withTableName("user")
//			.usingGeneratedKeyColumns("id");

		this.productMapper = (rs, rowNum) -> {
			UserVo uvo = new UserVo();
			uvo.setId(rs.getString("id")); // PK는 필수값. getInt를 써도 문제는 없음
			uvo.setPassword(rs.getString("password"));
			uvo.setIsFirst(rs.getBoolean("isFirst"));
			return uvo;
		};
	}

	private Map<String, Object> mapColumns(UserVo uvo) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", uvo.getId());
		params.put("password", uvo.getPassword());
		params.put("isFirst", uvo.getIsFirst());
		return params;
	}

	public Integer insertUser(UserVo uvo) {
		Map<String, Object> params = mapColumns(uvo);
		return productInsertion.executeAndReturnKey(params).intValue();
	}
}
