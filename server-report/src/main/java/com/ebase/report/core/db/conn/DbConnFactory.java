package com.ebase.report.core.db.conn;

import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.exception.DbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DbConnFactory {
    private static final Logger logger = LoggerFactory.getLogger(DbConnFactory.class);

    public static Connection factory(String dsName) throws DbException {
        Connection conn = null;
        DataSource ds = null;

        if (logger.isDebugEnabled()) {
            logger.debug("Get Connection from DataSource:" + dsName);
        }
        try {
            ds = DataSourceManager.get().getDataSource(dsName);

            conn = ds.getConnection();
        } catch (SQLException e) {
            logger.error("On getting Connection, SQLException occurred: ", e);
            throw new DbException(e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Got the Connection successfully.");
        }
        return conn;
    }

    public static boolean testConn(String dsName) {
        boolean b = true;

        try {
            Connection conn =  factory(dsName);
            DataBaseUtil.closeConnection(conn);
        } catch (Exception e) {
            b = false;
        }

        return b;
    }
}
