package fengfei.forest.slice.database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import fengfei.forest.slice.Slice;
import fengfei.forest.slice.exception.SliceException;

public class PoolableServerSlice extends ServerSlice {
	DataSource dataSource;

	public PoolableServerSlice(Slice slice) {
		super(slice);
	}

	public PoolableServerSlice(Slice slice, DataSource dataSource) {
		super(slice);

		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws SliceException {

		if (dataSource == null) {
			throw new SliceException("");
		} else {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {

				throw new SliceException("Can't get connection for slice "
						+ this, e);
			}
		}
	}

}
