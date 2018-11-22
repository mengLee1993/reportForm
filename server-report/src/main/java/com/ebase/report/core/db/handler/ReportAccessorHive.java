package com.ebase.report.core.db.handler;

import com.ebase.report.common.DemandType;
import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.common.SubjectTypeEnum;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.cube.Dimension;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.CustomIndex;
import com.ebase.report.model.dynamic.ReportDatasource;
import com.ebase.report.model.dynamic.ReportDynamicParam;
import com.ebase.report.model.dynamic.ReportTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportAccessorHive extends AbstractAccessor {
    private static Logger logger = LoggerFactory.getLogger(ReportAccessorOracle.class);

    private final String LIMIT = " limit ";

    @Override
    public List<RptDataTable> queryAllTables(Connection conn, DataBaseType dataBaseType) throws DbException {
        List<RptDataTable> tables = new ArrayList<RptDataTable>();
        ResultSet rs = null;

        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            // 数据库
            String catalog = null;
            // 数据库的用户
            String schemaPattern = null;// meta.getUserName();
            // 表名
            String tableNamePattern = null;

            //可为:"TABLE",   "VIEW",   "SYSTEM   TABLE",
            //"GLOBAL   TEMPORARY",   "LOCAL   TEMPORARY",   "ALIAS",   "SYNONYM"
            String[] types = {"TABLE"};/*只要表*/

            rs = dbMetaData.getTables(null, schemaPattern, null, types);

            while (rs.next()) {
                //只要表名这一列
                System.out.println(rs.getObject("TABLE_NAME"));

                RptDataTable rptDataTable = new RptDataTable();
                rptDataTable.setTableCode(rs.getObject("TABLE_NAME").toString());

                tables.add(rptDataTable);
            }

        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
        }

        return tables;
    }

    @Override
    public List<RptDataField> queryColumns(Connection conn, String tableName) throws DbException {
        return null;
    }

    @Override
    public String typesConvert(String type) {
        type.toUpperCase();
        if (type.startsWith("STRING") || type.startsWith("BINARY")) {
            return "String";
        } else if (type.startsWith("TINYINT")) {
            // return "Integer";Byte
            return "Byte";
        } else if (type.startsWith("INT")) {
            return "INT";
        } else if (type.startsWith("BIGINT")) {
            return "Long";
        } else if (type.startsWith("FLOAT") || type.startsWith("DOUBLE")) {
            return "double";
        } else if (type.startsWith("DECIMAL")) {
            return "BigDecimal";
        } else if (type.startsWith("BOOLEAN")) {
            return "Boolean";
        } else if (type.startsWith("TIMESTAMP")) {
            return "TimeStamp";
        }
        return type;
    }

    @Override
    public DataDetailSQL getReportFromDetailSql(ReportDatasource reportDatasource) {
        return null;
    }

    @Override
    public String reportCoreDetail(ReportDatasource reportDatasource) {
        return null;
    }
}
