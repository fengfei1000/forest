package fengfei.forest.slice.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import fengfei.forest.slice.Function;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.impl.SliceGroupFactory;

public class PoolableSliceGroup<Source> extends DatabaseSliceGroup<Source> {

    private Map<String, DataSource> pooledDataSource = new ConcurrentHashMap<>();
    private ConnectonUrlMaker urlMaker;
    private PoolableDataSourceFactory dataSourceFactory;

    public PoolableSliceGroup(SliceGroupFactory factory, String unitName) {
        super(factory, unitName);
    }

    public PoolableSliceGroup(
        SliceGroupFactory factory,
        SlicePlotter<Source> plotter,
        String unitName) {
        super(factory, plotter, unitName);
    }

    public Connection getConnection(Source key) throws SQLException {
        ServerSlice slice = get(key);
        return getConnection(slice);
    }

    public Connection getConnection(Source key, Function function) throws SQLException {
        ServerSlice slice = get(key, function);
        return getConnection(slice);
    }

    protected Connection getConnection(ServerSlice slice) throws SQLException {
        String url = urlMaker.makeUrl(slice);
        DataSource dataSource = pooledDataSource.get(url);
        if (dataSource == null) {
            dataSource = dataSourceFactory.createDataSource(url, slice);
        }
        return dataSource.getConnection();
    }

}
