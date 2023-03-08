package com.sk.learn.hr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository	
public class HrMasterJdbcRepository {
	
	private final JdbcTemplate jdbcTemplate;
	
	public HrMasterJdbcRepository(@Qualifier("hrJdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Optional<HrMasterEntity> selectBy(String sno) {
		return jdbcTemplate.query("select sno, kname, duty_rank, dept_code from HRPRN01MTT where sno =?", 
				new RowMapper<HrMasterEntity>() {
					@Override
					public HrMasterEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
						return HrMasterEntity.builder()
									.sno(rs.getString("SNO"))
									.name(rs.getString("KNAME"))
									.dutyRank(rs.getString("DUTY_RANK"))
									.deptCode(rs.getString("DEPT_CODE"))
								.build();
					}
				},
				sno).stream().findAny();
	}

	//테스트용 쿼리
	public void update() {
		jdbcTemplate.update("update hrprn01mtt set ENAME_L = 'MWOON' where sno = '98576'");
	}
}