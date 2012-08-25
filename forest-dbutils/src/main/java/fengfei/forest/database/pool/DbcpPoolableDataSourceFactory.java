package fengfei.forest.database.pool;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.AbandonedObjectPool;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.pool.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.database.utils.ParamsUtils;

public class DbcpPoolableDataSourceFactory implements PoolableDataSourceFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(DbcpPoolableDataSourceFactory.class);

	@Override
	public DataSource createDataSource(
			String driverClass,
			String url,
			String user,
			String password,
			Map<String, String> params) {

		DataSource ds = null;
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				url,
				user,
				password);
		try {
			AbandonedConfig conf = new AbandonedConfig();
			conf.setLogAbandoned(ParamsUtils.getDefaultBoolean(params, "logAbandoned", false));
			conf.setRemoveAbandoned(ParamsUtils.getDefaultBoolean(
					params,
					"removeAbandoned",
					false));
			conf.setRemoveAbandonedTimeout(ParamsUtils.getDefaultInt(
					params,
					"removeAbandonedTimeout",
					60));
			ObjectPool connectionPool = null;
			// if (conf.getLogAbandoned() && conf.getRemoveAbandoned()) {
			// for watch connections
			connectionPool = new AbandonedObjectPool(null, conf);
			BeanUtils.copyProperties(connectionPool, params);
			// }
			new PoolableConnectionFactory(
					connectionFactory,
					connectionPool,
					null,
					ParamsUtils.getValidationQuery(params),
					ParamsUtils.getDefaultBoolean(params, "defaultReadOnly"),
					ParamsUtils.getDefaultBoolean(params, "defaultAutoCommit"),
					conf);
			ds = new ClosablePoolingDataSource(connectionPool);

		} catch (Exception e) {
			logger.error("init error", e);
			throw new RuntimeException(e);
		}

		return ds;

	}

	@Override
	public void destory(DataSource dataSource) {
		ClosablePoolingDataSource ds = (ClosablePoolingDataSource) dataSource;
		ds.close();

	}

}
