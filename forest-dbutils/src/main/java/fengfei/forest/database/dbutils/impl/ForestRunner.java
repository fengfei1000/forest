package fengfei.forest.database.dbutils.impl;

import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.dbutils.QueryRunner;

public class ForestRunner extends QueryRunner {

    protected void rethrow(SQLException cause, String sql, Object... params) throws SQLException {

        String causeMessage = cause.getMessage();
        if (causeMessage == null) {
            causeMessage = "";
        }
        StringBuffer msg = new StringBuffer(causeMessage);

        msg.append(" Query: ");
        msg.append(sql);
        msg.append(" Parameters: ");

        if (params == null) {
            msg.append("[]");
        } else {
            msg.append(Arrays.deepToString(params));
        }

        SQLException e = new SQLException(
            msg.toString(),
            cause.getSQLState(),
            cause.getErrorCode(),
            cause);

        // e.setNextException(cause);

        throw e;
    }
}
