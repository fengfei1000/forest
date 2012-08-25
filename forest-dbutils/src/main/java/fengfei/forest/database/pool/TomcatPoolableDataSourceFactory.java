package fengfei.forest.database.pool;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.database.utils.ParamsUtils;

public class TomcatPoolableDataSourceFactory implements PoolableDataSourceFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(TomcatPoolableDataSourceFactory.class);

	@Override
	public DataSource createDataSource(
			String driverClass,
			String url,
			String user,
			String password,
			Map<String, String> params) {

		org.apache.tomcat.jdbc.pool.DataSource ds = null;
		try {
			PoolProperties poolProperties = new PoolProperties();

			poolProperties.setLogAbandoned(ParamsUtils.getDefaultBoolean(
					params,
					"logAbandoned",
					false));
			poolProperties.setRemoveAbandoned(ParamsUtils.getDefaultBoolean(
					params,
					"removeAbandoned",
					false));
			poolProperties.setRemoveAbandonedTimeout(ParamsUtils.getDefaultInt(
					params,
					"removeAbandonedTimeout",
					60));
			BeanUtils.copyProperties(poolProperties, params);

			poolProperties
					.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
			ds = new org.apache.tomcat.jdbc.pool.DataSource();
			ds.setPoolProperties(poolProperties);
		} catch (Exception e) {
			logger.error("init error", e);
			throw new RuntimeException(e);
		}

		return ds;

	}

	@Override
	public void destory(DataSource dataSource) {
		org.apache.tomcat.jdbc.pool.DataSource ds = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
		ds.close();

	}

}
