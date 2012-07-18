package fengfei.forest.slice.database;

import javax.sql.DataSource;

public interface PoolableDataSourceFactory {

    DataSource createDataSource(String url, ServerSlice slice);

    public void destory(DataSource dataSource);
}
