package fengfei.forest.slice.database.dbutils.pool;

import javax.sql.DataSource;

import fengfei.forest.slice.database.ServerSlice;

public interface PooledDataSourceFactory {

    DataSource createDataSource(String key, String url, ServerSlice slice);

    public void destory(DataSource dataSource);
}
