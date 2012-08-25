package fengfei.forest.database.pool;

import java.util.Map;

import javax.sql.DataSource;

public interface PoolableDataSourceFactory {

	DataSource createDataSource(
			String driverClass,
			String url,
			String user,
			String password,
			Map<String, String> params);

	public void destory(DataSource dataSource);
}
