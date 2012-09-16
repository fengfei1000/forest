package fengfei.forest.slice.database;

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
	private PoolableDataSourceFactory poolableDataSourceFactory;

	public PoolableSliceGroup(DatabaseSliceGroupFactory factory, String unitName) {
		this(factory, null, unitName);
	}

	public PoolableSliceGroup(DatabaseSliceGroupFactory factory,
			SlicePlotter<Source> plotter, String unitName) {
		super(factory, plotter, unitName);
		this.poolableDataSourceFactory = factory
				.getPoolableDataSourceFactory(unitName);
		this.urlMaker = factory.getConnectonUrlMaker(unitName);
	}

	public PoolableServerSlice getPoolableServerSlice(Source key)
			throws SliceException {
		ServerSlice slice = get(key);
		return getPoolableServerSlice(slice);
	}

	public PoolableServerSlice getPoolableServerSlice(Source key,
			Function function) throws SliceException {
		ServerSlice slice = get(key, function);
		return getPoolableServerSlice(slice);
	}

	private PoolableServerSlice getPoolableServerSlice(ServerSlice slice)
			throws SliceException {
		String url = urlMaker.makeUrl(slice);
		DataSource dataSource = pooledDataSources.get(url);
		if (dataSource == null) {
			try {
				dataSource = poolableDataSourceFactory.createDataSource(
						slice.getDriverClass(), url, slice.getUsername(),
						slice.getPassword(), slice.getExtraInfo());
				pooledDataSources.put(url, dataSource);
			} catch (PoolableException e) {
				throw new SliceException(
						"Can't create datasource for the slice " + slice, e);
			}
		}
		if (dataSource == null) {
			throw new SliceException("Can't get datasource for the slice"
					+ slice);
		}
		PoolableServerSlice poolableServerSlice = new PoolableServerSlice(
				slice, dataSource);
		return poolableServerSlice;
	}

	//
	// public Connection getConnection(Source key) throws SliceException {
	// ServerSlice slice = get(key);
	// DataSource dataSource = getDataSource(slice);
	// if (dataSource == null) {
	// throw new SliceException("");
	// } else {
	// try {
	// return dataSource.getConnection();
	// } catch (SQLException e) {
	//
	// throw new SliceException("Can't get connection for slice "
	// + slice, e);
	// }
	// }
	// }
	//
	// public Connection getConnection(Source key, Function function)
	// throws SliceException {
	// ServerSlice slice = get(key, function);
	// DataSource dataSource = getDataSource(slice);
	// if (dataSource == null) {
	// throw new SliceException("");
	// } else {
	// try {
	// return dataSource.getConnection();
	// } catch (SQLException e) {
	// throw new SliceException(String.format(
	// "Can't get connection by Function(%s), for slice %s",
	// function.name(), slice), e);
	// }
	// }
	// }

	public Map<String, DataSource> allPooledDataSources() {
		return pooledDataSources;
	}

	public PoolableDataSourceFactory getPoolableDataSourceFactory() {
		return poolableDataSourceFactory;
	}

	@Override
	public String toString() {
		return "PoolableSliceGroup [pooledDataSources=" + pooledDataSources
				+ ", urlMaker=" + urlMaker + ", poolableDataSourceFactory="
				+ poolableDataSourceFactory + ", sliceGroup=" + sliceGroup
				+ ", plotter=" + plotter + ", overType=" + overType + "]";
	}

}
