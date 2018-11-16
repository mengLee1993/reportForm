package com.ebase.report.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DataBaseUtil {
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeStatment(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
