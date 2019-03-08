package com.ido85.frame.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import javax.inject.Named;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 按Schema分租户
 * 
 * @author rongxj
 *
 */
@Named
public class SchemaBasedMultiTenantConnectionProvider implements
		MultiTenantConnectionProvider, ServiceRegistryAwareService {

	
	@Value("${spring.tenantDatasource.username}")
	private String user;

	@Value("${spring.tenantDatasource.password}")
	private String password;

	@Value("${spring.tenantDatasource.base.url}")
	private String dataSourceUrl;
	
	@Value("${spring.tenantDatasource.base.param}")
	private String dataSourceParam;

	@Value("${spring.tenantDatasource.dataSourceClassName}")
	private String dataSourceClassName;

	@Value("${spring.tenantDatasource.poolName}")
	private String poolName;

	@Value("${spring.tenantDatasource.connectionTimeout}")
	private int connectionTimeout;

	@Value("${spring.tenantDatasource.maxLifetime}")
	private int maxLifetime;

	@Value("${spring.tenantDatasource.maximumPoolSize}")
	private int maximumPoolSize;

	@Value("${spring.tenantDatasource.minimumIdle}")
	private int minimumIdle;

	@Value("${spring.tenantDatasource.idleTimeout}")
	private int idleTimeout;

	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenantId;

	
	private java.util.Map<String, DataSource> tenantDataSources = Maps.newConcurrentMap();
	
	public DataSource getDatasource(String tenantId) {
		
		if(tenantDataSources.get(tenantId) != null){
			return tenantDataSources.get(tenantId);
		}
		
		Properties dsProps = new Properties();
		dsProps.put("url", dataSourceUrl+tenantId+dataSourceParam);
		dsProps.put("user", user);
		dsProps.put("password", password);
		dsProps.put("prepStmtCacheSize", 250);
		dsProps.put("prepStmtCacheSqlLimit", 200);
		dsProps.put("cachePrepStmts", Boolean.TRUE);
		dsProps.put("useServerPrepStmts", Boolean.TRUE);

		Properties configProps = new Properties();
		configProps.put("dataSourceClassName", dataSourceClassName);
		configProps.put("poolName", tenantId);
		configProps.put("maximumPoolSize", maximumPoolSize);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("minimumIdle", minimumIdle);
		configProps.put("connectionTimeout", connectionTimeout);
		configProps.put("idleTimeout", idleTimeout);
		configProps.put("dataSourceProperties", dsProps);

		HikariConfig hc = new HikariConfig(configProps);
		HikariDataSource ds = new HikariDataSource(hc);
		tenantDataSources.put(tenantId, ds);
		return ds;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private final DatasourceConnectionProviderImpl connectionProvider = new
	// DatasourceConnectionProviderImpl();
	@Autowired
	@Qualifier(value = "secondaryDataSource")
	private DataSource dataSource;
	
	private Set<String> schemas = Sets.newConcurrentHashSet();
	
	@Value("${spring.jpa.multitenant.default-id}")
	private String defaultTenant;

	@Override
	public boolean isUnwrappableAs(
			@SuppressWarnings("rawtypes") Class unwrapType) {
		return false;
		// return connectionProvider.isUnwrappableAs(unwrapType);
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
		// return connectionProvider.unwrap(unwrapType);
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		// return connectionProvider.getConnection();
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		// connectionProvider.closeConnection(connection);
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier)
			throws SQLException {
		Connection connection = null;
		try{
			connection = getDatasource(tenantIdentifier).getConnection();//getAnyConnection();
		}catch(Exception e){
			if(!schemas.contains(tenantIdentifier)){
				connection = getAnyConnection();
				//创建数据库
				connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS "+ tenantIdentifier +" DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci");
				schemas.add(tenantIdentifier);
				if(!connection.isClosed()){
					connection.close();
				}
				//创建表
//				connection.createStatement().execute(sql);
				connection = getDatasource(tenantIdentifier).getConnection();
			}
		}
		 
		
		/*try {
			if(!schemas.contains(tenantIdentifier)){
				//创建数据库
				connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS "+ tenantIdentifier +" DEFAULT CHARSET utf8 COLLATE utf8_general_ci");
				schemas.add(tenantIdentifier);
				//创建表
//				connection.createStatement().execute(sql);
			}
			
			//创建
			connection.createStatement().execute("USE " + tenantIdentifier);
		} catch (SQLException e) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema ["
							+ tenantIdentifier + "]", e);
		}*/
		return connection;
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection)
			throws SQLException {
		
		if(!connection.isClosed()){
			connection.close();
		}
		
//		try {
//			connection.createStatement().execute("USE xxx"+defaultTenant);
//		} catch (SQLException e) {
//			throw new HibernateException(
//					"Could not alter JDBC connection to specified schema ["
//							+ tenantIdentifier + "]", e);
//		}finally {
//			try {
//				if(!connection.isClosed()){
//					connection.close();
//				}
//			} catch (Exception e2) {
//			}
//		}
		
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}

	@Override
	public void injectServices(ServiceRegistryImplementor serviceRegistry) {
		// TODO Auto-generated method stub

	}

}
