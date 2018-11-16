package com.ebase.report.core.db.handler;

import com.ebase.report.common.DemandType;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.Dimension;
import com.ebase.report.cube.DimensionKey;
import com.ebase.report.model.RptDataDict;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.ReportDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public abstract class AbstractAccessor implements ReportAccessor {
    private static Logger logger = LoggerFactory.getLogger(AbstractAccessor.class);
    //固定长度
    public final Integer LENGTH = 10000;

    public String getReportSql(ReportDatasource reportDatasource) {
        return null;
    }

    public ResultSet query(String sql, Connection conn, CubeTree cubeTree) throws DbException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            System.out.println(sql);
            //
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // 遍历结果集
            rsToCubeTree(rs, cubeTree);

        } catch (SQLException e) {
            logger.error("Occurred DbException.", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
            DataBaseUtil.closeStatment(pstmt);
        }

        return rs;
    }

    public CubeTree rsToCubeTree(ResultSet rs, CubeTree cubeTree) throws DbException {
        // 遍历结果集
        try {
            // 获得结果集元数据（元数据就是描述数据的数据，比如把表的列类型列名等作为数据）
            ResultSetMetaData rsmd = rs.getMetaData();

            // 获得列的总数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                DimensionKey dimensionKey = new DimensionKey();
                for (int i = 0; i < columnCount; i++) {
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnName = rsmd.getColumnLabel(i + 1);
//                    Object columnValue = rs.getObject(columnName);
                    String columnValue = rs.getString(columnName);
                    Dimension dimension = cubeTree.getDimensionMap().get(columnName);

                    if (null == dimension) {
                        cubeTree.getCells().put(dimensionKey.toString(), String.valueOf(columnValue));
                    } else {
                        String value = String.valueOf(columnValue);
                        if (DemandType.DIMENSION.getCode().equals(dimension.getDemandType().getCode())) {
                            dimensionKey.addDimension(columnName, value);
                        } else {
                            // 初始化当前结果成对应的cell里中
//                            String measuresKey = dimensionKey.toString() + "#"+DemandType.MEASURES.getCode()+"." + columnName;
                            String measuresKey = dimensionKey.getMeasuresKey(columnName);
                            cubeTree.getCells().put(measuresKey, String.valueOf(columnValue));

                            dimensionKey.addMeasures(measuresKey, String.valueOf(columnValue));
                        }
                    }
                }
                // 根据dimensionKey 初始化树结点
                cubeTree.addDimensionKey(dimensionKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }

        return cubeTree;
    }

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

            if (DataBaseType.DB_TYPE_ORACLE.equals(dataBaseType)) {
                schemaPattern = dbMetaData.getUserName().toUpperCase();
            } else if (DataBaseType.DB_TYPE_MYSQL.equals(dataBaseType)) {
                // MySQL 的 table 这一级别查询不到备注信息

//            }  else if (DATABASETYPE.SQLSERVER.equals(dbtype) || DATABASETYPE.SQLSERVER2005.equals(dbtype)) {
//                // SqlServer
//                tableNamePattern = "%";
//            }  else if (DATABASETYPE.DB2.equals(dbtype)) {
//                // DB2查询
//                schemaPattern = "jence_user";
//                tableNamePattern = "%";
//            } else if (DATABASETYPE.INFORMIX.equals(dbtype)) {
//                // SqlServer
//                tableNamePattern = "%";
            } else if (DataBaseType.DB_TYPE_HIVE.equals(dataBaseType)) {
                // hive
                tableNamePattern = "%";
            } else {
                throw new RuntimeException("不认识的数据库类型!");
            }

            rs = dbMetaData.getTables(null, schemaPattern, tableNamePattern, types);

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
    public Integer queryCount(String sqlCount, Connection conn) {
        Integer count = 0;
        ResultSet rs = null;
        try{
            PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sqlCount);
            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            // 获得列的总数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(columnName);

                    if(columnValue != null){
                        count = Integer.valueOf(columnValue.toString());
                    }
                }
                // 根据dimensionKey 初始化树结点
            }
        }catch (SQLException e){
            try {
                throw new DbException(e);
            } catch (DbException e1) {
                e1.printStackTrace();
            }
        } finally {
            DataBaseUtil.closeResultSet(rs);
        }

        return count;
    }

    @Override
    public Map<String, List<Object>> queryDateil(String sql, Connection conn, Map<String, List<Object>> tmpMap) {

        ResultSet rs = null;
        try{
            PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            // 获得列的总数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnLabel(i + 1);
                    //是oracle 分页字段 跳过
                    if("RN".equals(columnName)){
                        continue;
                    }

                    Object columnValue = rs.getString(columnName);

                    if(tmpMap.get(columnName) == null){
                        ArrayList<Object> objects = new ArrayList<>();
                        objects.add(columnValue);

                        tmpMap.put(columnName,objects);
                    }else{
                        tmpMap.get(columnName).add(columnValue);
                    }

                }
                // 根据dimensionKey 初始化树结点
            }
        }catch (SQLException e){
            try {
                throw new DbException(e);
            } catch (DbException e1) {
                e1.printStackTrace();
            }
        } finally {
            DataBaseUtil.closeResultSet(rs);
        }

        return tmpMap;
    }

    @Override
    public Map<String, List<Object>> queryFromDetail(Integer count,String sql, Connection conn, Map<String, List<Object>> tmpMap) {
        return queryDateil(sql,conn,tmpMap);
    }

    @Override
    public String toWhereSqlFtiler(ReportDatasource reportDatasource){
        return null;
    }

    @Override
    public String toSelectMeasures(ReportDatasource reportDatasource) {
        return null;
    }

    @Override
    public String toSelectDetailMeasures(ReportDatasource reportDatasource) {
        return null;
    }

    // distinct COUNT
    public Integer queryDistinctCount(String distinctSqlCount, Connection conn) {
        return this.queryCount(distinctSqlCount, conn);
    }

    // distinct value
    public List<RptDataDict> queryDistinctFeild(String distinctSql, Connection conn) throws DbException {
        List<RptDataDict> dataDicts = new ArrayList<RptDataDict>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(distinctSql);
//            pstmt.setString(1, tableName);


            rs = pstmt.executeQuery();

            while (rs.next()) {
                RptDataDict dataDict = new RptDataDict();
                dataDict.setFieldValue(rs.getString("field"));

                dataDicts.add(dataDict);
            }

        } catch (SQLException e) {
            logger.error("", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
            DataBaseUtil.closeStatment(pstmt);
        }

        return dataDicts;
    }
}
