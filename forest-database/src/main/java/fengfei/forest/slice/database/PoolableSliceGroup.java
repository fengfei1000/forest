package fengfei.forest.slice.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import fengfei.forest.database.pool.PoolableDataSourceFactory;
import fengfei.forest.database.pool.PoolableException;
import fengfei.forest.slice.Function;
import fengfei.forest.slice.SlicePlotter;
import fengfei.forest.slice.exception.SliceException;

public class PoolableSliceGroup<Source> extends DatabaseSliceGroup<Source> {

	private Map<String, DataSource> pooledDataSources = new ConcurrentHashMap<>();
	private ConnectonUrlMaker urlMaker;
	private PoolableDataSourceFactory dataSourceFactory;

	public PoolableSliceGroup(DatabaseSliceGroupFactory factory, String unitName) {
		this(factory, null, unitName);
	}

	public PoolableSliceGroup(DatabaseSliceGroupFactory factory,
			SlicePlotter<Source> plotter, String unitName) {
		super(factory, plotter, unitName);
		this.dataSourceFactory = factory.getPoolableDataSourceFactory(unitName);
		this.urlMaker = factory.getConnectonUrlMaker(unitName);
	}

	public DataSource getDataSource(Source key) throws SliceException {
		ServerSlice slice = get(key);
		return getDataSource(slice);
	}

	public DataSource getDataSource(Source key, Function function)
			throws SliceException {
		ServerSlice slice = get(key, function);
		return getDataSource(slice);
	}

	public Connection getConnection(Source key) throws SliceException {
		ServerSlice slice = get(key);
		DataSource dataSource = getDataSource(slice);
		if (dataSource == null) {
			throw new SliceException("");
		} else {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {

				throw new SliceException("Can't get connection for slice "
						+ slice, e);
			}
		}

	}

	public Connection getConnection(Source key, Function function)
			throws SliceException {
		ServerSlice slice = get(key, function);
		DataSource dataSource = getDataSource(slice);
		if (dataSource == null) {
			throw new SliceException("");
		} else {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				throw new SliceException(String.format(
						"Can't get connection by Function(%s), for slice %s",
						function.name(), slice), e);
			}
		}
	}

	protected DataSource getDataSource(ServerSlice slice) throws SliceException {
		String url = urlMaker.makeUrl(slice);
		DataSource dataSource = pooledDataSources.get(url);
		if (dataSource == null) {
			try {
				dataSource = dataSourceFactory.createDataSource(
						slice.getDriverClass(), url, slice.getUsername(),
						slice.getPassword(), slice.getExtraInfo());
				pooledDataSources.put(url, dataSource);
			} catch (PoolableException e) {

				throw new SliceException(
						"Can't create datasource for the slice " + slice, e);
			}
		}
		return dataSource;
	}

	@Override
	public String toString() {
		return "PoolableSliceGroup [pooledDataSources=" + pooledDataSources
				+ ", urlMaker=" + urlMaker + ", dataSourceFactory="
				+ dataSourceFactory + ", sliceGroup=" + sliceGroup
				+ ", plotter=" + plotter + ", overType=" + overType + "]";
	}

}
