package fengfei.forest.slice.database;

import fengfei.forest.slice.Slice;

public class PooledConnectionServerSlice extends Slice {

    static final String KEY_HOST = "host", KEY_PORT = "port", KEY_USER = "username",
            KEY_PASSWORD = "password";

    public PooledConnectionServerSlice(Slice slice) {
        this.status = slice.getStatus();
        this.extraInfo = slice.getExtraInfo();
        this.id = slice.getId();
        this.isPhysical = slice.isPhysical();
        this.suffix = slice.getSuffix();
        this.weight = slice.getWeight();

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
        return Integer.parseInt(KEY_PORT);
    }

}
