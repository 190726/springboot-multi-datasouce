package com.sk.learn.mobile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LogPgmJdbcRepository {
	
private final JdbcTemplate jdbcTemplate;
	
	public LogPgmJdbcRepository(@Qualifier("mobileJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Optional<LogPgmEntity> selectBy(String pid) {
		return jdbcTemplate.query("select PID, PROGRAMID from LOG_BY_PGM where PID =?", 
				new RowMapper<LogPgmEntity>() {
					@Override
					public LogPgmEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
						return LogPgmEntity.builder().id(rs.getString("pID")).programid(rs.getString("PROGRAMID")).build();
					}
				},
				pid).stream().findAny();
	}
}
