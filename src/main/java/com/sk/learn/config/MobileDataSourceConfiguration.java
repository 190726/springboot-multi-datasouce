package com.sk.learn.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence-db.properties"})
@EnableJpaRepositories(basePackages = "com.sk.learn.mobile"
						,entityManagerFactoryRef = "mobileEntityManagerFactory"
						,transactionManagerRef = "mobileTransactionManager")
public class MobileDataSourceConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.mobile")
	public DataSourceProperties mobileDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.mobile.hikari")
	public DataSource mobileDataSource(@Qualifier("mobileDataSourceProperties") DataSourceProperties mobileDataSourceProperties) {
		return mobileDataSourceProperties
				.initializeDataSourceBuilder()
				.build();
	}
	
	@Bean
	public JdbcTemplate mobileJdbcTemplate(@Qualifier("mobileDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	/*
	 * EntityManagerFactoryBuilder 빈 생성을 위에서는 insysDataSource 를 @Primary로 지정해야 한다. 
	 * @Primary로 지정된 DataSource 가 없는 경우 EntityManagerFactoryBuilder 빈이 생성되지 않는다.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean mobileEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("mobileDataSource") DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.sk.learn.mobile")
				.persistenceUnit("hrEntityManagerFactory")
				.build();
	}
	
	@Bean
	public PlatformTransactionManager mobileTransactionManager(
			@Qualifier("mobileEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	/**
	 * Jpa Multiple Transaction(Hr + Mobile)
	 */
	@Bean
	public PlatformTransactionManager multiTransactionManager(
			PlatformTransactionManager hrTransactionManager,
			@Qualifier("mobileTransactionManager") PlatformTransactionManager mobileTransactionManager
			) {
		return new ChainedTransactionManager(hrTransactionManager, mobileTransactionManager);
	}
}