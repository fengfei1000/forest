package fengfei.forest.slice.database;

import fengfei.forest.slice.Slice;

public class ServerSlice extends Slice {

	static final String KEY_HOST = "host";
	static final String KEY_PORT = "port";
	static final String KEY_USER = "username";
	static final String KEY_PASSWORD = "password";
	static final String KEY_DRIVER_CLASS = "driverClass";
	static final String KEY_SCHEMA = "schema";
	static final String KEY_DATABASE = "database";

	public ServerSlice(Slice slice) {
		this.status = slice.getStatus();
		this.extraInfo = slice.getExtraInfo();
		this.id = slice.getId();
		this.isPhysical = slice.isPhysical();
		this.weight = slice.getWeight();
	}

	public String getSuffix() {
		return getSliceId();
	}

	public String getUsername() {
		return extraInfo.get(KEY_USER);
	}

	public String getPassword() {
		return extraInfo.get(KEY_PASSWORD);
	}

	public String getHost() {
		return extraInfo.get(KEY_HOST);
	}

	public int getPort() {
		return Integer.parseInt(extraInfo.get(KEY_PORT));
	}

	public String getDriverClass() {
		return extraInfo.get(KEY_DRIVER_CLASS);
	}

	public String getSchema() {
		return extraInfo.get(KEY_SCHEMA);
	}

	public String getDatabase() {
		return extraInfo.get(KEY_DATABASE);
	}

	public String getDatabaseName() {
		return extraInfo.get(KEY_DATABASE);
	}
}
