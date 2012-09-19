package fengfei.forest.slice.database;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.database.pool.BonePoolableDataSourceFactory;
import fengfei.forest.database.pool.DbcpPoolableDataSourceFactory;
import fengfei.forest.database.pool.PoolableDataSourceFactory;
import fengfei.forest.database.pool.TomcatPoolableDataSourceFactory;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class DatabaseSliceGroupFactory extends SliceGroupFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseSliceGroupFactory.class);
	public static final String Database = "database";
	public static final String POOL_NAME = "poolName";
	public static final String POOL_BONECP = "BoneCP";
	public static final String POOL_DBCP = "DBCP";
	public static final String POOL_TOMCAT_JDBC = "TomcatJDBC";
	protected static Map<String, Class<? extends ConnectonUrlMaker>> connectonUrlMakerClazz = new HashMap<>();
	protected Map<String, PoolableSliceGroup<?>> poolableSliceGroupCache = new HashMap<>();
	protected Map<String, DatabaseSliceGroup<?>> databaseSliceGroupCache = new HashMap<>();
	static {

		registerDriver("oracle.jdbc.driver.OracleDriver",
				OracleThinConnectonUrlMaker.class);
		registerDriver("org.gjt.mm.mysql.Driver", MysqlConnectonUrlMaker.class);
		registerDriver("com.mysql.jdbc.Driver", MysqlConnectonUrlMaker.class);
		registerDriver("org.postgresql.Driver",
				PostgreSQLConnectonUrlMaker.class);

	}

	public Map<String, DatabaseSliceGroup<?>> allDatabaseSliceGroups() {
		return databaseSliceGroupCache;
	}

	public Map<String, PoolableSliceGroup<?>> allPoolableSliceGroup() {
		return poolableSliceGroupCache;
	}

	public <Source> DatabaseSliceGroup<Source> getDatabaseSliceGroup(
			SlicePlotter<Source> plotter, String unitName) {
		@SuppressWarnings("unchecked")
		DatabaseSliceGroup<Source> group = (DatabaseSliceGroup<Source>) databaseSliceGroupCache
				.get(unitName);
		if (group == null) {
			group = new DatabaseSliceGroup<>(this, plotter, unitName);
			databaseSliceGroupCache.put(unitName, group);
		}

		return group;
	}

	public <Source> DatabaseSliceGroup<Source> getDatabaseSliceGroup(
			String unitName) {

		@SuppressWarnings("unchecked")
		PoolableSliceGroup<Source> group = (PoolableSliceGroup<Source>) databaseSliceGroupCache
				.get(unitName);
		if (group == null) {
			group = new PoolableSliceGroup<>(this, unitName);
			databaseSliceGroupCache.put(unitName, group);
		}

		return group;
	}

	public <Source> PoolableSliceGroup<Source> getPoolableSliceGroup(
			SlicePlotter<Source> plotter, String unitName) {
		@SuppressWarnings("unchecked")
		PoolableSliceGroup<Source> group = (PoolableSliceGroup<Source>) poolableSliceGroupCache
				.get(unitName);
		if (group == null) {
			group = new PoolableSliceGroup<>(this, plotter, unitName);
			poolableSliceGroupCache.put(unitName, group);
		}

		return group;
	}

	public <Source> PoolableSliceGroup<Source> getPoolableSliceGroup(
			String unitName) {
		@SuppressWarnings("unchecked")
		PoolableSliceGroup<Source> group = (PoolableSliceGroup<Source>) poolableSliceGroupCache
				.get(unitName);
		if (group == null) {
			group = new PoolableSliceGroup<>(this, unitName);
			poolableSliceGroupCache.put(unitName, group);
		}

		return group;
	}

	public PoolableDataSourceFactory getPoolableDataSourceFactory(
			String unitName) {
		GroupConfig group = groupConfigCache.get(unitName);

		if (group == null) {
			throw new NonExistedSliceException("unitName=" + unitName);
		}
		Map<String, String> info = group.getDefaultExtraInfo();
		String poolName = info.get(POOL_NAME);
		if (null == poolName || "".equals(poolName)) {
			poolName = "BoneCP";
		}
		switch (poolName) {
		case POOL_BONECP:
			return new BonePoolableDataSourceFactory();
		case POOL_DBCP:
			return new DbcpPoolableDataSourceFactory();
		case POOL_TOMCAT_JDBC:
			return new TomcatPoolableDataSourceFactory();
		default:
			logger.warn("Can't supported pool, default using BoneCP.");
			return new BonePoolableDataSourceFactory();
		}

	}

	public static void registerDriver(String driverName,
			Class<? extends ConnectonUrlMaker> clazz) {
		connectonUrlMakerClazz.put(driverName, clazz);
	}

	public ConnectonUrlMaker getConnectonUrlMaker(String unitName) {
		GroupConfig group = groupConfigCache.get(unitName);

		if (group == null) {
			throw new NonExistedSliceException("unitName=" + unitName);
		}
		Map<String, String> info = group.getDefaultExtraInfo();
		String driverName = info.get(ServerSlice.KEY_DRIVER_CLASS);
		Class<? extends ConnectonUrlMaker> clazz = connectonUrlMakerClazz
				.get(driverName);
		if (null == clazz) {
			String msg = "Not registered ConnectonUrlMaker for driver: "
					+ driverName;
			logger.error(msg);
			throw new RuntimeException(msg);
		}
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("create ConnectonUrlMaker error", e);
			throw new RuntimeException(e);
		}

	}
}
