package fengfei.forest.database.pool;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCPDataSource;

public class BonePoolableDataSourceFactory implements PoolableDataSourceFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(BonePoolableDataSourceFactory.class);

	@Override
	public DataSource createDataSource(
			String driverClass,
			String url,
			String user,
			String password,
			Map<String, String> params) {
		try {
			Class.forName(driverClass); // load the DB driver

			BoneCPDataSource ds = new BoneCPDataSource();
			BeanUtils.copyProperties(ds, params);
			ds.setJdbcUrl(url); // set the JDBC url
			ds.setUsername(user); // set the username
			ds.setPassword(password); // set the password
			return ds;
		} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
			logger.error("create DataSource error", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void destory(DataSource dataSource) {
		BoneCPDataSource ds = (BoneCPDataSource) dataSource;
		ds.close();

	}

}
