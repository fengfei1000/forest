package fengfei.forest.slice.database;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import fengfei.forest.database.pool.PoolableDataSourceFactory;
import fengfei.forest.slice.Function;
import fengfei.forest.slice.SlicePlotter;

public class PoolableSliceGroup<Source> extends DatabaseSliceGroup<Source> {

	private Map<String, DataSource> pooledDataSources = new ConcurrentHashMap<>();
	private ConnectonUrlMaker urlMaker;
	private PoolableDataSourceFactory dataSourceFactory;

	public PoolableSliceGroup(DatabaseSliceGroupFactory factory, String unitName) {
		this(factory, null, unitName);
	}

	public PoolableSliceGroup(
			DatabaseSliceGroupFactory factory,
			SlicePlotter<Source> plotter,
			String unitName) {
		super(factory, plotter, unitName);
		this.dataSourceFactory = factory.getPoolableDataSourceFactory(unitName);
		this.urlMaker = factory.getConnectonUrlMaker(unitName);
	}

	public DataSource getDataSource(Source key) throws SQLException {
		ServerSlice slice = get(key);
		return getDataSource(slice);
	}

	public DataSource getDataSource(Source key, Function function) throws SQLException {
		ServerSlice slice = get(key, function);
		return getDataSource(slice);
	}

	protected DataSource getDataSource(ServerSlice slice) throws SQLException {
		String url = urlMaker.makeUrl(slice);
		DataSource dataSource = pooledDataSources.get(url);
		if (dataSource == null) {
			dataSource = dataSourceFactory.createDataSource(
					slice.getDriverClass(),
					url,
					slice.getUsername(),
					slice.getPassword(),
					slice.getExtraInfo());
			pooledDataSources.put(url, dataSource);
		}
		return dataSource;
	}

	@Override
	public String toString() {
		return "PoolableSliceGroup [pooledDataSources=" + pooledDataSources + ", urlMaker=" + urlMaker + ", dataSourceFactory=" + dataSourceFactory + ", sliceGroup=" + sliceGroup + ", plotter=" + plotter + ", overType=" + overType + "]";
	}

}
