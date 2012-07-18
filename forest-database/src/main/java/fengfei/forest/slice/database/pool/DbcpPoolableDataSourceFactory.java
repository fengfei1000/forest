package fengfei.forest.slice.database.pool;

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

import fengfei.forest.slice.database.PoolableDataSourceFactory;
import fengfei.forest.slice.database.ServerSlice;
import fengfei.forest.slice.database.utils.SliceUtils;

public class DbcpPoolableDataSourceFactory implements PoolableDataSourceFactory {

    private static final Logger logger = LoggerFactory
        .getLogger(DbcpPoolableDataSourceFactory.class);

    @Override
    public DataSource createDataSource(String url, ServerSlice slice) {

        DataSource ds = null;
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
            url,
            slice.getUsername(),
            slice.getPassword());
        try {
            AbandonedConfig conf = new AbandonedConfig();
            conf.setLogAbandoned(SliceUtils.getDefaultBoolean(slice, "logAbandoned", false));
            conf.setRemoveAbandoned(SliceUtils.getDefaultBoolean(slice, "removeAbandoned", false));
            conf.setRemoveAbandonedTimeout(SliceUtils.getDefaultInt(
                slice,
                "removeAbandonedTimeout",
                60));
            ObjectPool connectionPool = null;
            // if (conf.getLogAbandoned() && conf.getRemoveAbandoned()) {
            // for watch connections
            connectionPool = new AbandonedObjectPool(null, conf);
            BeanUtils.copyProperties(connectionPool, slice.getExtraInfo());
            // }
            new PoolableConnectionFactory(
                connectionFactory,
                connectionPool,
                null,
                SliceUtils.getValidationQuery(slice),
                SliceUtils.getDefaultBoolean(slice, "defaultReadOnly"),
                SliceUtils.getDefaultBoolean(slice, "defaultAutoCommit"),
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
