package fengfei.forest.slice.database.pool;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.slice.database.PoolableDataSourceFactory;
import fengfei.forest.slice.database.ServerSlice;
import fengfei.forest.slice.database.utils.SliceUtils;

public class TomcatPoolableDataSourceFactory implements PoolableDataSourceFactory {

    private static final Logger logger = LoggerFactory
        .getLogger(TomcatPoolableDataSourceFactory.class);

    @Override
    public DataSource createDataSource(String url, ServerSlice slice) {

        org.apache.tomcat.jdbc.pool.DataSource ds = null;
        try {
            PoolProperties poolProperties = new PoolProperties();

            poolProperties.setLogAbandoned(SliceUtils.getDefaultBoolean(
                slice,
                "logAbandoned",
                false));
            poolProperties.setRemoveAbandoned(SliceUtils.getDefaultBoolean(
                slice,
                "removeAbandoned",
                false));
            poolProperties.setRemoveAbandonedTimeout(SliceUtils.getDefaultInt(
                slice,
                "removeAbandonedTimeout",
                60));
            BeanUtils.copyProperties(poolProperties, slice.getExtraInfo());

            poolProperties
                .setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
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
