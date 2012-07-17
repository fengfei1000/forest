package fengfei.forest.slice.database;

import javax.sql.DataSource;


public interface PooledDataSourceFactory {

    DataSource createDataSource(String key, String url, ServerSlice slice);

    public void destory(DataSource dataSource);
}
