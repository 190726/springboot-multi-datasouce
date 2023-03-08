package com.sk.learn.hr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HRPRN01MTT")
public class HrMasterEntity {
	
	@Id
	@Column(name = "SNO")
	private String sno;
	
	@Column(name = "KNAME")
	private String name;
	
	@Column(name = "DUTY_RANK")
	private String dutyRank;
	
	@Column(name = "DEPT_CODE")
	private String deptCode;
}