package fengfei.forest.slice.database.dbutils.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.forest.slice.database.dbutils.Transducer;
import fengfei.forest.slice.database.dbutils.ListHandler;
import fengfei.forest.slice.database.dbutils.OneBeanHandler;
import fengfei.forest.slice.database.dbutils.ForestGrower;
import fengfei.forest.slice.database.dbutils.SingleValueHandler;

public class DefaultForestGrower implements ForestGrower {

    private static Logger logger = LoggerFactory.getLogger(DefaultForestGrower.class);
    private Connection connection;
    private QueryRunner runner;

    public DefaultForestGrower(Connection connection) {
        runner = new ForestRunner();
        this.connection = connection;
    }

    @Override
    public <T> List<T> select(Transducer<T> transducer, String sql, Object... params)
        throws SQLException {
        printlnSQL(sql, params);
        List<T> list = runner.query(connection, sql, new ListHandler<T>(transducer), params);
        return list;
    }

    @Override
    public <T> List<T> select(Class<T> clazz, String sql, Object... params) throws SQLException {
        printlnSQL(sql, params);
        List<T> list = runner.query(connection, sql, new BeanListHandler<T>(clazz), params);
        return list;
    }

    @Override
    public <T> T selectOne(Transducer<T> transducer, String sql, Object... params)
        throws SQLException {
        printlnSQL(sql, params);
        T one = runner.query(connection, sql, new OneBeanHandler<T>(transducer), params);
        return one;
    }

    @Override
    public <T> T selectOne(Class<T> clazz, String sql, Object... params) throws SQLException {
        printlnSQL(sql, params);
        T one = runner.query(connection, sql, new BeanHandler<T>(clazz), params);
        return one;
    }

    @Override
    public int count(String sql, Object... params) throws SQLException {
        printlnSQL(sql, params);
        String one = runner.query(connection, sql, new SingleValueHandler(), params);
        if (one == null) {
            return 0;
        } else {
            int ct = Integer.parseInt(one);
            return ct;
        }
    }

    @Override
    public int update(String sql, Object... params) throws SQLException {
        printlnSQL(sql, params);

        boolean isReadOnly = connection.isReadOnly();
        connection.setReadOnly(false);
        int ct = runner.update(connection, sql, params);
        connection.setReadOnly(isReadOnly);
        return ct;
    }

    @Override
    public void begin() throws SQLException {
        this.connection.setAutoCommit(false);
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null) {
            this.connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null) {
            this.connection.rollback();
        }
        // try {
        // connection.setAutoCommit(true);
        // } catch (SQLException e) {
        // log.warn("Can't setAutoCommit true", e);
        // }
    }

    private void printlnSQL(String sql, Object... params) {
        logger
            .debug(sql + "  params: " + Arrays.asList(params == null ? new Object[] {} : params));
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            // try {
            // connection.setAutoCommit(true);
            // } catch (SQLException e) {
            // log.warn("Can't setAutoCommit true", e);
            // }
            connection.close();
        }
    }
}
