package com.naver.test;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.naver.test.*.repository" }, transactionManagerRef = "jpaTransaction")
@Import(value = MybatisConfiguration.class)
public class PersistenceConfiguration {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		containerEntityManagerFactoryBean.setDataSource(dataSource());
		containerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		containerEntityManagerFactoryBean.setPackagesToScan("com.naver.test.*.model");

		return containerEntityManagerFactoryBean;
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().addScript("classpath:notice_schema.sql")
				.addScript("classpath:school_schema.sql").setScriptEncoding("UTF-8").setType(EmbeddedDatabaseType.HSQL)
				.build();
	}

	@Bean
	@Qualifier(value = "jpaTransaction")
	public PlatformTransactionManager jpaTransaction(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}
}