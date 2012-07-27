package fengfei.forest.slice.database;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import fengfei.forest.slice.SlicePlotter;

import fengfei.forest.slice.config.xml.GroupConfig;
import fengfei.forest.slice.database.pool.BonePoolableDataSourceFactory;
import fengfei.forest.slice.database.pool.DbcpPoolableDataSourceFactory;
import fengfei.forest.slice.database.pool.TomcatPoolableDataSourceFactory;
import fengfei.forest.slice.exception.NonExistedSliceException;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class DatabaseSliceGroupFactory extends SliceGroupFactory {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSliceGroupFactory.class);
    public static final String Database = "database";
    public static final String POOL_NAME = "poolName";
    public static final String POOL_BONECP = "BoneCP";
    public static final String POOL_DBCP = "DBCP";
    public static final String POOL_TOMCAT_JDBC = "TomcatJDBC";
    protected static Map<String, Class<? extends ConnectonUrlMaker>> connectonUrlMakerClazz = new HashMap<>();
    static {

        register("oracle.jdbc.driver.OracleDriver", OracleThinConnectonUrlMaker.class);
        register("org.gjt.mm.mysql.Driver", MysqlConnectonUrlMaker.class);
        register("com.mysql.jdbc.Driver", MysqlConnectonUrlMaker.class);
        register("org.postgresql.Driver", PostgreSQLConnectonUrlMaker.class);


    }

    public <Source> DatabaseSliceGroup<Source> getDatabaseSliceGroup(
        SlicePlotter<Source> plotter,
        String unitName) {
        return new DatabaseSliceGroup<>(this, plotter, unitName);
    }

    public <Source> PoolableSliceGroup<Source> getPoolableSliceGroup(
        SlicePlotter<Source> plotter,
        String unitName) {
        return new PoolableSliceGroup<>(this, plotter, unitName);
    }

    public <Source> DatabaseSliceGroup<Source> getDatabaseSliceGroup(String unitName) {
        return new DatabaseSliceGroup<>(this, unitName);
    }

    public <Source> PoolableSliceGroup<Source> getPoolableSliceGroup(String unitName) {
        return new PoolableSliceGroup<>(this, unitName);
    }

    public PoolableDataSourceFactory getPoolableDataSourceFactory(String unitName) {
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



    public static void registerDriver(String driverName, Class<? extends ConnectonUrlMaker> clazz) {
        connectonUrlMakerClazz.put(driverName, clazz);
    }

    public ConnectonUrlMaker getConnectonUrlMaker(String unitName) {
        GroupConfig group = groupConfigCache.get(unitName);

        if (group == null) {
            throw new NonExistedSliceException("unitName=" + unitName);
        }
        Map<String, String> info = group.getDefaultExtraInfo();
        String driverName = info.get(ServerSlice.KEY_DRIVER_CLASS);
        Class<? extends ConnectonUrlMaker> clazz = connectonUrlMakerClazz.get(driverName);
        if (null == clazz) {
            String msg = "Not registered ConnectonUrlMaker for driver: " + driverName;
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
