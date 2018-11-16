package com.ebase.report.core.db.exception;

import org.springframework.util.StringUtils;

import java.sql.SQLException;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DbException extends SQLException {
    public static final String BASE_DB_DATASOURCE = "db.datasource";
    public static final String BASE_DB_LOCK = "db.lock";
    public static final String BASE_DB_CONN = "db.conn";
    public static final String BASE_DB_DATA_TYPE = "db.data.type";

    public static final String DB_GENERIC = "db.generic";
    public static final String SQL_GENERIC = "db.sql.generic";
    public static final String DUPLICATED_PK = "";

    public static final String ENTITY_PARAM_IS_NOT_EXISTS = "db.duplicated.pk";

    private SQLException sqlException = null;

    public DbException(SQLException cause) {
        sqlException = cause;
    }

    public SQLException getSQLException() {
        return sqlException;
    }

    public static String getDbExceptionValue(SQLException ex) {
        String sqlState = ex.getSQLState();
        if (StringUtils.isEmpty(sqlState)) {
            return DbException.SQL_GENERIC;
        }

        if ("23000".equals(sqlState)) {
            return DbException.DUPLICATED_PK;
        } else {
            return DbException.SQL_GENERIC;
        }
    }

}
