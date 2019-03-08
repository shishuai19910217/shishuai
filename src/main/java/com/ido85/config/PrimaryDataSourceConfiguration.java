package com.ido85.config;

import java.util.Properties;





import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 数据源设置
 * @author rongxj
 *
 */
@Configuration
@ComponentScan
@EnableTransactionManagement 
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryPrimary",  
    transactionManagerRef="transactionManagerPrimary",  
    basePackages={"com.ido85.frame","com.ido85.master","com.ido85.log"}) 
public class PrimaryDataSourceConfiguration {

	@Value("${spring.primaryDataSource.username}")
	private String user;

	@Value("${spring.primaryDataSource.password}")
	private String password;

	@Value("${spring.primaryDataSource.url}")
	private String dataSourceUrl;

	@Value("${spring.primaryDataSource.dataSourceClassName}")
	private String dataSourceClassName;

	@Value("${spring.primaryDataSource.poolName}")
	private String poolName;

	@Value("${spring.primaryDataSource.connectionTimeout}")
	private int connectionTimeout;

	@Value("${spring.primaryDataSource.maxLifetime}")
	private int maxLifetime;

	@Value("${spring.primaryDataSource.maximumPoolSize}")
	private int maximumPoolSize;

	@Value("${spring.primaryDataSource.minimumIdle}")
	private int minimumIdle;

	@Value("${spring.primaryDataSource.idleTimeout}")
	private int idleTimeout;
	
	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenantId;
	
	@Value("${multitenancy.hibernate.multiTenancy}")
	private String hibernateMultiTenancy;
	
	@Value("${multitenancy.hibernate.tenant_identifier_resolver}")
	private String tenant_identifier_resolver;
	
	@Value("${multitenancy.hibernate.multi_tenant_connection_provider}")
	private String multi_tenant_connection_provider;

	@Bean
	@Primary  
	public DataSource primaryDataSource() {
		Properties dsProps = new Properties();
		dsProps.put("url", dataSourceUrl);
		dsProps.put("user", user);
		dsProps.put("password", password);
		dsProps.put("prepStmtCacheSize", 250);
		dsProps.put("prepStmtCacheSqlLimit", 2048);
		dsProps.put("cachePrepStmts", Boolean.TRUE);
		dsProps.put("useServerPrepStmts", Boolean.TRUE);

		Properties configProps = new Properties();
		configProps.put("dataSourceClassName", dataSourceClassName);
		configProps.put("poolName", poolName);
		configProps.put("maximumPoolSize", maximumPoolSize);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("connectionTimeout", connectionTimeout);
		configProps.put("idleTimeout", idleTimeout);
		configProps.put("dataSourceProperties", dsProps);

		HikariConfig hc = new HikariConfig(configProps);
		HikariDataSource ds = new HikariDataSource(hc);
		return ds;
	}

	
	@Bean(name="entityManagerFactoryPrimary")  
    @Primary 
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(DataSource dataSource, JpaProperties jpaProperties) {  
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		jpaProperties.getProperties().put("hibernate." + "ejb.naming_strategy_delegator", "none");
		jpaProperties.getProperties().put(org.hibernate.cfg.Environment.PHYSICAL_NAMING_STRATEGY, "com.ido85.frame.hibernate.model.naming.CamelPhysicalNamingStrategyImpl");
		factory.setJpaPropertyMap(jpaProperties.getProperties());
		factory.setPackagesToScan("com.ido85.frame","com.ido85.master","com.ido85.log");
		factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		factory.setPersistenceUnitName("system");
		factory.afterPropertiesSet();
		return factory;
    }
	
	@Bean(name = "transactionManagerPrimary")
	@DependsOn("entityManagerFactoryPrimary")
	@Primary
    PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactoryPrimary") LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary) {  
        return new JpaTransactionManager(entityManagerFactoryPrimary.getObject());  
    }
	
//	@Bean(name="entityManagerFactoryPrimary")  
//    @Primary  
//    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder, JpaProperties jpaProperties) {  
//        return builder.dataSource(primaryDataSource())  
//                    .properties(getVendorProperties(primaryDataSource(), jpaProperties))  
//                    .packages("")  
//                    .persistenceUnit("system")  
//                    .build();  
//    }
//	
//	private Map<String, String> getVendorProperties(DataSource dataSource, JpaProperties jpaProperties) {  
//        return jpaProperties.getHibernateProperties(dataSource);  
//    }
	
//	@Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        final LocalSessionFactoryBean b = new LocalSessionFactoryBean();
//        b.setPackagesToScan("com.ido85.orm");
//        b.setDataSource(primaryDataSource()());
//        b.setPhysicalNamingStrategy(new CamelPhysicalNamingStrategyImpl());
////        b.setHibernateProperties(Util.props(
////                USE_NEW_ID_GENERATOR_MAPPINGS, "true",
////                HBM2DDL_AUTO, "update",
////                ORDER_INSERTS, "true",
////                ORDER_UPDATES, "true",
////                MAX_FETCH_DEPTH, "0",
////                STATEMENT_FETCH_SIZE, "200",
////                STATEMENT_BATCH_SIZE, "50",
////                BATCH_VERSIONED_DATA, "true",
////                USE_STREAMS_FOR_BINARY, "true",
////                USE_SQL_COMMENTS, "true"
////        ));
//        return b;
//    }
}
