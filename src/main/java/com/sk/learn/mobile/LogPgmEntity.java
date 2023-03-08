package com.sk.learn.mobile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "LOG_BY_PGM")
@Entity
public class LogPgmEntity {
	
	@Id
	@Column(name = "PID")
	private String id;
	
	@Column(name = "PROGRAMID")
	private String programid;

}
