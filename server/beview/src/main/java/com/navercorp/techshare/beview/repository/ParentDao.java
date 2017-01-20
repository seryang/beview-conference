package com.navercorp.techshare.beview.repository;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by seungdols on 2017. 1. 19..
 */
public class ParentDao {

	protected JdbcTemplate jdbcTemplate;

	protected String buildPageSQL(String SELECT_SQL) {
		StringBuilder sql = new StringBuilder(SELECT_SQL);

		String part = " limit ? , ?";
		sql.append(part);

		return sql.toString();
	}
}
