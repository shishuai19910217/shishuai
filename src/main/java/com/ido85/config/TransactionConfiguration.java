/**
 * 
 */
package com.ido85.config;

import javax.sql.DataSource;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


/**
 * 事务配置
 * @author rongxj
 *
 */
//@Configuration
public class TransactionConfiguration {
	/**
	 * 
	 * @param datasource
	 * @return
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource datasource){
		return new JdbcTemplate(datasource);
	}
	
	/**
	 * @param transactionManager
	 * @return
	 */
	public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
		return new TransactionTemplate(transactionManager);
	}
	
	@Bean
	public Validator validator(){
		return new LocalValidatorFactoryBean();
	}
	
//	@Bean
//	public HibernateTransactionManager transactionManager(DataSource dataSource){
//		HibernateTransactionManager manager = new org.springframework.orm.hibernate5.HibernateTransactionManager();
//		manager.setDataSource(dataSource);
//		return manager;
//	}
}
