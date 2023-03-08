package com.sk.learn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.learn.hr.HrMasterEntity;
import com.sk.learn.hr.HrMasterJdbcRepository;
import com.sk.learn.hr.HrMasterRepository;
import com.sk.learn.mobile.LogPgmEntity;
import com.sk.learn.mobile.LogPgmRepository;

@Service
public class MultiSourceService {
	
	@Autowired HrMasterRepository hrMasterRepository;
	@Autowired HrMasterJdbcRepository hrMasterJdbcRepository;
	@Autowired LogPgmRepository logPgmRepository;

	//Multi DataSource Transaction
	@Transactional("multiTransactionManager")
	public void test1() {
		hrMasterRepository.save(HrMasterEntity.builder().sno("99991").name("강태우").build());
		test1_2();
	}
	
	public void test1_2() {
		logPgmRepository.save(LogPgmEntity.builder().id("test").programid("test").build());
		test1_3();
	}
	
	public void test1_3() {
		int result = 5;
		hrMasterJdbcRepository.update();
		if(result == 5) throw new IllegalStateException("강제오류!");
	}

	//Primary(HR) DataSource Transaction
	@Transactional
	public void test2() {
		hrMasterRepository.save(HrMasterEntity.builder().sno("99991").name("강태우").build());
		test2_2();
	}
	
	public void test2_2() {
		logPgmRepository.save(LogPgmEntity.builder().id("test").programid("test").build());
		test2_3();
	}
	
	public void test2_3() {
		int result = 5;
		hrMasterJdbcRepository.update();
		if(result == 5) throw new IllegalStateException("강제오류!");
	}
	
	//Mobile DataSource Transaction
	@Transactional("mobileTransactionManager")
	public void test3() {
		hrMasterRepository.save(HrMasterEntity.builder().sno("99991").name("강태우").build());
		test3_2();
	}
	
	public void test3_2() {
		logPgmRepository.save(LogPgmEntity.builder().id("test").programid("test").build());
		test3_3();
	}
	
	public void test3_3() {
		int result = 5;
		hrMasterJdbcRepository.update();
		if(result == 5) throw new IllegalStateException("강제오류!");
	}
	
	//Multi DataSource Transaction
	@Transactional("multiTransactionManager")
	public void test4() {
		hrMasterRepository.save(HrMasterEntity.builder().sno("99991").name("강태우").build());
		test4_2();
	}
	
	public void test4_2() {
		logPgmRepository.save(LogPgmEntity.builder().id("test").programid("test").build());
		test4_3();
	}
	
	public void test4_3() {
		hrMasterJdbcRepository.update();
	}
	

}
