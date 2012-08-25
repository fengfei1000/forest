package fengfei.forest.database.dbutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ForestGrower {

    <T> List<T> select(String sql, Transducer<T> transducer, Object... params) throws SQLException;

    <T> List<T> select(String sql, Class<T> clazz, Object... params) throws SQLException;

    <T> T selectOne(String sql, Transducer<T> transducer, Object... params) throws SQLException;

    <T> T selectOne(String sql, Class<T> clazz, Object... params) throws SQLException;

    int count(String sql, Object... params) throws SQLException;

    int update(String sql, Object... params) throws SQLException;

    void begin() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    Connection getConnection();

}
