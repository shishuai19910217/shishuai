/**
 * 
 */
package com.ido85.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author rongxj
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactorySecondary", transactionManagerRef = "transactionManagerSecondary", basePackages = "com.ido85.seo")
public class SecondaryDataSourceConfiguration {

	@Value("${spring.secondaryDatasource.username}")
	private String user;

	@Value("${spring.secondaryDatasource.password}")
	private String password;

	@Value("${spring.secondaryDatasource.url}")
	private String dataSourceUrl;

	@Value("${spring.secondaryDatasource.dataSourceClassName}")
	private String dataSourceClassName;

	@Value("${spring.secondaryDatasource.poolName}")
	private String poolName;

	@Value("${spring.secondaryDatasource.connectionTimeout}")
	private int connectionTimeout;

	@Value("${spring.secondaryDatasource.maxLifetime}")
	private int maxLifetime;

	@Value("${spring.secondaryDatasource.maximumPoolSize}")
	private int maximumPoolSize;

	@Value("${spring.secondaryDatasource.minimumIdle}")
	private int minimumIdle;

	@Value("${spring.secondaryDatasource.idleTimeout}")
	private int idleTimeout;

	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenantId;

	@Value("${multitenancy.hibernate.multiTenancy}")
	private String hibernateMultiTenancy;

	@Value("${multitenancy.hibernate.tenant_identifier_resolver}")
	private String tenant_identifier_resolver;

	@Value("${multitenancy.hibernate.multi_tenant_connection_provider}")
	private String multi_tenant_connection_provider;
	
	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenant;

	@Bean(name = "secondaryDataSource")
	public DataSource secondaryDataSource() {
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

	@Bean(name = "entityManagerFactorySecondary")
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
			@Qualifier("secondaryDataSource") DataSource dataSource,
			JpaProperties jpaProperties,
			MultiTenantConnectionProvider multiTenantConnectionProvider,
			CurrentTenantIdentifierResolver tenantIdentifierResolver) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(jpaVendorAdapter());
//		jpaProperties.getProperties().put("hibernate.dialect",
//				"org.hibernate.dialect.MySQLDialect");
//		jpaProperties.getProperties().put("hibernate.multiTenancy",
//				hibernateMultiTenancy);
//		jpaProperties.getProperties().put(
//				"hibernate.tenant_identifier_resolver",
//				tenant_identifier_resolver);
//		jpaProperties.getProperties().put(
//				"hibernate.multi_tenant_connection_provider",
//				multi_tenant_connection_provider);
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT,
                          MultiTenancyStrategy.SCHEMA);
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER,
                          multiTenantConnectionProvider);
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
                          tenantIdentifierResolver);
//		properties.put(org.hibernate.cfg.Environment.IMPLICIT_NAMING_STRATEGY, "legacy-jpa");
//		properties.put(org.hibernate.cfg.Environment.PHYSICAL_NAMING_STRATEGY, PhysicalNamingStrategyStandardImpl.INSTANCE);

//		properties.put(org.hibernate.cfg.Environment.DEFAULT_SCHEMA,
//				defaultTenant);
		
		properties.put(org.hibernate.cfg.Environment.GENERATE_STATISTICS,
				false);
		
		factory.setJpaPropertyMap(properties);
		factory.setPackagesToScan("com.ido85.seo");
		factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		factory.setPersistenceUnitName("seo");
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		factory.afterPropertiesSet();
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		return factory;
	}

	@Bean(name = "transactionManagerSecondary")
	@DependsOn("entityManagerFactorySecondary")
	PlatformTransactionManager transactionManagerSecondary(
			@Qualifier("entityManagerFactorySecondary") LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory) {
		return new JpaTransactionManager(
				secondaryEntityManagerFactory.getObject());
	}
	
	@Bean
    public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
	    jpaVendorAdapter.setDatabase(Database.MYSQL);
//	    jpaVendorAdapter.setGenerateDdl(true);
	    return jpaVendorAdapter;
//        return new HibernateJpaVendorAdapter();
    }

}
