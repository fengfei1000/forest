package fengfei.forest.slice.database.pool;

import java.lang.reflect.InvocationTargetException;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCPDataSource;

import fengfei.forest.slice.database.PoolableDataSourceFactory;
import fengfei.forest.slice.database.ServerSlice;

public class BonePoolableDataSourceFactory implements PoolableDataSourceFactory {

    private static final Logger logger = LoggerFactory
        .getLogger(BonePoolableDataSourceFactory.class);

    @Override
    public DataSource createDataSource(String url, ServerSlice slice) {
        try {
            Class.forName(slice.getDriverClass()); // load the DB driver

            BoneCPDataSource ds = new BoneCPDataSource();
            BeanUtils.copyProperties(ds, slice.getExtraInfo());
            ds.setJdbcUrl(url); // set the JDBC url
            ds.setUsername(slice.getUsername()); // set the username
            ds.setPassword(slice.getPassword()); // set the password
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
