package com.ebase.report.core.db.handler;

import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.DemandType;
import com.ebase.report.common.FilterTypeEnum;
import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.core.utils.excel.ExportExcelUtils;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.Dimension;
import com.ebase.report.cube.DimensionKey;
import com.ebase.report.model.RptDataDict;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.FilterArea;
import com.ebase.report.model.dynamic.FilterAreaValue;
import com.ebase.report.model.dynamic.ReportDatasource;
import com.ebase.report.model.dynamic.ReportMeasure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public abstract class AbstractAccessor implements ReportAccessor {
    private static Logger LOG = LoggerFactory.getLogger(AbstractAccessor.class);
    //固定长度  10000 分页一个
    public final Integer LENGTH = 10000;

    protected final String EXCEL_NAME = "数据报表明细";

    private final String LIMIT = " limit ";


    public String getReportSql(ReportDatasource reportDatasource) {
        return null;
    }

    public Long query(String sql, Connection conn, CubeTree cubeTree) throws DbException {

        LOG.info("开始执行sql = {}",sql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Long time = 0L;
        try {
             //执行sql 查询
            long startTime=System.currentTimeMillis();   //获取开始时间
            //
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            long endTime=System.currentTimeMillis();   //获取开始时间

            time = endTime - startTime;
            LOG.info("程序运行时间： = {}",time+"ms");

            // 遍历结果集
            rsToCubeTree(rs, cubeTree);

        } catch (SQLException e) {
            LOG.error("Occurred DbException.", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
            DataBaseUtil.closeStatment(pstmt);
        }

        return time;
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
//            } else if (DataBaseType.DB_TYPE_HIVE.equals(dataBaseType)) {
//                // hive
//                tableNamePattern = "%";
            } else {
                throw new RuntimeException("不认识的数据库类型!");
            }

            rs = dbMetaData.getTables(null, schemaPattern, tableNamePattern, types);

            while (rs.next()) {
                //只要表名这一列
//                System.out.println(rs.getObject("TABLE_NAME"));

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

                    //columnName = columnName + "@" + materialCode@4000
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
    public List<File> queryFromDetail(Integer count,String sql, Connection conn) {
        List<File> files = new ArrayList<>();

        //看数据量是否过大
        if(count < LENGTH){
            generateTmpMap(conn, files, sql);
        }else{
            int size = count / LENGTH;
            size = count % LENGTH == 0 ? size - 1 : size;
            for(int i = 0; i <= size ; i ++){
                String s = LIMIT + (i * LENGTH) + "," + LENGTH;

                String sqlDetail = sql + s;
                generateTmpMap(conn, files, sqlDetail);
            }
        }

        return files;
    }

    @Override
    public String toWhereSqlFtiler(ReportDatasource reportDatasource){
        String dbTypeEnum = reportDatasource.getDatabaseType();
        List<FilterArea> filter = reportDatasource.getReportDynamicParam().getFilter();
        String sqlFtiler = "";

        StringBuilder builder = new StringBuilder();
        //如果是时间类型 可能有范围
        filter.forEach(x -> {

            //是时间
            List<FilterAreaValue> tmp = x.getTmp();
            tmp.stream().filter(z -> z.getIsChecked() != null && z.getIsChecked() == 1).forEach(y -> {

                Map<FilterTypeEnum, String> fieldValue = y.getFieldVal();
                fieldValue.keySet().stream().filter(m -> StringUtil.isNotEmpty(fieldValue.get(m))).forEach(f -> {
                    String value = fieldValue.get(f);
                    if(FilterTypeEnum.isScope(f)){
                        builder.append(" and " + x.getCode() + " " + f.getName() + " '" + value + "' ");
                    }else if(FilterTypeEnum.RG.equals(f)){
                        builder.append(" and " + x.getCode() + " " + f.getName() + " '%" + value + "%' ");
                    }else{
                        String whw = "";
                        if(f.equals(FilterTypeEnum.EQ)){
                            whw = "in";
                        }else{
                            whw = "not in";
                        }

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(" and " + x.getCode() + " "+ whw + " (");
                        for (String s : value.split("##")) {
                            stringBuilder.append(" '" + s + "',");
                        }
                        String s = stringBuilder.substring(0,stringBuilder.lastIndexOf(","));
                        builder.append(s);
                        builder.append(") ");
                    }
                });
            });
//


        });

        sqlFtiler = builder.toString();



        return sqlFtiler;
    }

    @Override
    public String toSelectMeasures(ReportDatasource reportDatasource) {
        String dbTypeEnum = reportDatasource.getDatabaseType();
        Set<ReportMeasure> measures = reportDatasource.getReportDynamicParam().getMeasures();

        String selectSql = "";

        if(CollectionUtils.isNotEmpty(measures)){
            StringBuilder builder = new StringBuilder();

            measures.forEach(x -> {
                MeasureTypeEnum measureEnum = x.getMeasureType();

                String measureType = measureEnum.getMeasureType();
                if (!MeasureTypeEnum.CUSTOM.equals(measureEnum)) {
                    //系统级 都是系统级的
                    //db2 count(1)
                    builder.append(measureType + "( " + x.getFieldCode() + " ) as " + x.getKey() + ",");
                }

            });
            selectSql = builder.substring(0,builder.lastIndexOf(","));
        }
        return selectSql;
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
            LOG.error("", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
            DataBaseUtil.closeStatment(pstmt);
        }

        return dataDicts;
    }

    //生成map
    protected void generateTmpMap(Connection conn, List<File> files, String sqlDetail) {
        Map<String, List<Object>> tmpMap = new HashMap<>();
        tmpMap = queryDateil(sqlDetail, conn, tmpMap);
        try {
            generateFile(files, tmpMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //生成file文件
    protected void generateFile(List<File> files, Map<String, List<Object>> tmpMap) throws IOException {
        //生成excel 并生成file
        String fileName = this.getClass().getResource("/").getPath() + new Date().getTime() + files.size() + ".xls";
        ;
        FileOutputStream fout = null;
        Workbook workbook = null;
        try {
            workbook = ExportExcelUtils.createExcelWorkBook(EXCEL_NAME,EXCEL_NAME,EXCEL_NAME + System.currentTimeMillis(),tmpMap);

            File file = new File(fileName);
            fout = new FileOutputStream(file);
            workbook.write(fout);
            files.add(file);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            workbook.close();
        }
    }


    @Override
    public String typesConvert(String type) {
        if(StringUtils.isNotEmpty(type)){
            DBFieldTypeEnum dbFieldTypeEnum = DBFieldTypeEnum.getByCode(type);
            if(dbFieldTypeEnum != null){
                return dbFieldTypeEnum.getName();
            }
        }
        return type;
    }

}
