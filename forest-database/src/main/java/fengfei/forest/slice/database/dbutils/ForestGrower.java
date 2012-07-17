package fengfei.forest.slice.database.dbutils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ForestGrower {

    <T> List<T> select(Transducer<T> transducer, String sql, Object... params) throws SQLException;

    <T> List<T> select(Class<T> clazz, String sql, Object... params) throws SQLException;

    <T> T selectOne(Transducer<T> transducer, String sql, Object... params) throws SQLException;

    <T> T selectOne(Class<T> clazz, String sql, Object... params) throws SQLException;

    int count(String sql, Object... params) throws SQLException;

    int update(String sql, Object... params) throws SQLException;

    void begin() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    Connection getConnection();

}
