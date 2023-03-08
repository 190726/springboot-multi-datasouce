package com.sk.learn.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence-db.properties"})
@EnableJpaRepositories(basePackages = "com.sk.learn.hr"
					  ,entityManagerFactoryRef = "hrEntityManagerFactory"
					  ,transactionManagerRef = "hrTransactionManager")
public class HrDataSourceConfiguration {
	
	/*
	 * Hr 과 Mobile 중 한쪽은 Primary 로 설정해줘야 한다. 
	 */
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hr")
	public DataSourceProperties hrDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hr.hikari")
	public DataSource hrDataSource(DataSourceProperties hrDataSourceProperties) {
		return hrDataSourceProperties
				.initializeDataSourceBuilder()
				.build();
	}
	
	@Bean
	public JdbcTemplate hrJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	

	/*
	 * EntityManagerFactoryBuilder 빈 생성을 위에서는 hrDataSource 를 @Primary로 지정해야 한다. 
	 * @Primary로 지정된 DataSource 가 없는 경우 EntityManagerFactoryBuilder 빈이 생성되지 않는다.
	 */
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean hrEntityManagerFactory(EntityManagerFactoryBuilder builder,
			DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.sk.learn.hr")
				.persistenceUnit("hrEntityManagerFactory")
				.build();
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager hrTransactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}