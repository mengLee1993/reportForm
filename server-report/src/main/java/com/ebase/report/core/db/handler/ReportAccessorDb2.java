package com.ebase.report.core.db.handler;

import com.ebase.report.common.*;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.utils.ReportExportUtil;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.core.utils.excel.ExportExcelUtils;
import com.ebase.report.cube.Dimension;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportAccessorDb2 extends AbstractAccessor {
    private static Logger logger = LoggerFactory.getLogger(ReportAccessorDb2.class);

    private final String LIMIT = " limit ";

    private static final String TABNAME = "TABNAME";

    private static final String REMARKS = "REMARKS";


    /**
     * 生成my sql 实现
     * @param reportDatasource
     * @return
     */
    @Override
    public String getReportSql(ReportDatasource reportDatasource) {
        String sql = "";
        //前台动态参数
        ReportDynamicParam reportDynamicParam = reportDatasource.getReportDynamicParam();
        //主题
        String subjectType = reportDatasource.getSubjectType();
        //数据库类型
        String dbTypeEnumByName = reportDatasource.getDatabaseType();

        Boolean dimension = reportDynamicParam.getDimension();

        List<Dimension> line = reportDynamicParam.getLine();  //行维度

        List<Dimension> column = reportDynamicParam.getColumn();  //列维度

        Set<ReportMeasure> measures = reportDynamicParam.getMeasures(); //度量值

        String filterSql = toWhereSqlFtiler(reportDatasource);
        //这个拼指标的sql 如果没有指标 就是
        String selectSql = toSelectMeasures(reportDatasource);

        if(SubjectTypeEnum.DATATABLE.getCode().equals(subjectType)){
            StringBuilder builderSelect = new StringBuilder(" select ");

            StringBuilder builderWhe = new StringBuilder(" where 1 = 1 ");

            //可能没有维度
            StringBuilder builderGroup = null;
            if(dimension){ //有维度的
                builderGroup = new StringBuilder(" group by ");
            }else{  //没有维度
                builderGroup = new StringBuilder("");
            }

            ReportTable reportTable = reportDatasource.getReportTables().get(0);

            //行维度转换
            conversionFoSql(dbTypeEnumByName, line, builderSelect, builderWhe, builderGroup);

            //列维度转换
            conversionFoSql(dbTypeEnumByName, column, builderSelect, builderWhe, builderGroup);

            builderSelect.append(selectSql);
            builderSelect.append(" from " + reportTable.getTableCode());
            //可能没有
            String group = "";
            if(dimension){
                group = builderGroup.substring(0,builderGroup.lastIndexOf(","));
            }
            builderSelect.append(builderWhe).append(filterSql).append(group);

            sql = builderSelect.toString();

        }else if(SubjectTypeEnum.SUBJECT.getMsg().equals(subjectType)){
            //主题 （多张表）

        }
        return sql;
    }


    /**
     * 转换sql
     * db2 的  select "movie_emotion" as "F2" from "movie_data_10w"
     * @param dbTypeEnumByName
     * @param line
     * @param builderSelect
     * @param builderWhe
     * @param builderGroup
     */
    private void conversionFoSql(String dbTypeEnumByName, List<Dimension> line, StringBuilder builderSelect, StringBuilder builderWhe, StringBuilder builderGroup) {
//        Boolean boo = true;
        for(Dimension x:line){
            DemandType demandType = x.getDemandType();

            if(DemandType.DIMENSION.equals(demandType)){
                //维  度
                String code = x.getFieldCode(); //name age as f124
                builderSelect.append( code + " as \"" + x.getKey() + "\",");
                builderGroup.append( code + " ,");
                String sql = x.toWHereSql(dbTypeEnumByName);
                builderWhe.append(sql);
//                boo = false;
            }else{

            }
        }
    }

    @Override
    public List<RptDataField> queryColumns(Connection conn, String tableName) throws DbException {
        List<RptDataField> fieldList = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "show full fields from "+tableName;
            //
            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, tableName);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RptDataField field = new RptDataField();
//                String javaStyle = StringUtil.javaStyle(rs.getString(1));
                field.setFieldCode(rs.getString(1));
                field.setFieldType(typesConvert(rs.getString(2)));
                field.setFieldName(rs.getString(9));

                fieldList.add(field);
            }

        } catch (SQLException e) {
            logger.error("", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
            DataBaseUtil.closeStatment(pstmt);
        }

        return fieldList;
    }


    /**
     * 生成sql 没有group by 和 函数的
     * @param reportDatasource
     * @return
     */
    @Override
    public DataDetailSQL getReportFromDetailSql(ReportDatasource reportDatasource) {

        DataDetailSQL dataDetailSQL = new DataDetailSQL();

        //前台动态参数
        ReportDynamicParam reportDynamicParam = reportDatasource.getReportDynamicParam();
        //主题
        String subjectType = reportDatasource.getSubjectType();
        //数据库类型
        String dbTypeEnumByName = reportDatasource.getDatabaseType();

        List<Dimension> line = reportDynamicParam.getLine();  //行维度

        List<Dimension> column = reportDynamicParam.getColumn();  //列维度

        //给仍出来
        String filterSql = toWhereSqlFtiler(reportDatasource);

        String selectSql = toSelectDetailMeasures(reportDatasource);
        if(SubjectTypeEnum.DATATABLE.getCode().equals(subjectType)){
            StringBuilder builderSelect = new StringBuilder(" select ");

            StringBuilder builderWhe = new StringBuilder(" where 1 = 1 ");

            ReportTable reportTable = reportDatasource.getReportTables().get(0);

            //详细sql转换
            conversionDetailFoSql(dbTypeEnumByName, line, builderSelect, builderWhe);

            conversionDetailFoSql(dbTypeEnumByName, column, builderSelect, builderWhe);

            String select = null;
            if(StringUtil.isEmpty(selectSql)){
                select = builderSelect.substring(0,builderSelect.lastIndexOf(","));
            }else{
                select = builderSelect.append(selectSql).toString();
            }

//            String select = builderSelect.substring(0,builderSelect.lastIndexOf(","));

            StringBuilder builder = new StringBuilder(select);
            builder.append("  from " + reportTable.getTableCode());

            builder.append(builderWhe).append(filterSql);

            StringBuilder builderCount = new StringBuilder(" select COUNT(1) ");
            builderCount.append("  from " + reportTable.getTableCode());
            builderCount.append(builderWhe).append(filterSql);

            dataDetailSQL.setSql(builder.toString());
            dataDetailSQL.setSqlCount(builderCount.toString());

        }else if(SubjectTypeEnum.SUBJECT.getMsg().equals(subjectType)){
            //主题 （多张表）

        }
        return dataDetailSQL;


    }

    @Override
    public Integer queryCount(String sqlCount, Connection conn) {

        return super.queryCount(sqlCount,conn);
    }

    @Override
    public Map<String, List<Object>> queryDateil(String sql, Connection conn, Map<String, List<Object>> tmpMap) {
        return super.queryDateil(sql,conn,tmpMap);
    }

    /**
     * 详细sql 转换
     * @param dbTypeEnumByName
     * @param line
     * @param builderSelect
     * @param builderWhe
     */
    private List<Long> conversionDetailFoSql(String dbTypeEnumByName, List<Dimension> line, StringBuilder builderSelect, StringBuilder builderWhe) {
        List<Long> fieldIds = new ArrayList<>();
        line.forEach(x -> {
            DemandType demandType = x.getDemandType();

            if(DemandType.DIMENSION.equals(demandType)){
                //维  度
                String code = x.getFieldCode(); //name age as f124
                builderSelect.append(code + " as \"" + x.getFieldName() + "\",");
                String s = x.toWHereSql(dbTypeEnumByName);
                builderWhe.append(s);

                //设置指标id
                if(x.getFieldId() != null){
                    fieldIds.add(Long.valueOf(x.getFieldId()));
                }
            }else{
                //度量值
            }

        });
        return fieldIds;
    }

    @Override
    public List<File> queryFromDetail(Integer count, String sql, Connection conn)  {
       return super.queryFromDetail(count,sql,conn);
    }




    /**
     * 根据数据库类型 得sqlwhere条件过滤sql
     * @param reportDatasource
     * @return
     */
    public String toWhereSqlFtiler(ReportDatasource reportDatasource){
        return super.toWhereSqlFtiler(reportDatasource);
    }

    /**
     * 返回 select sql
     * @param reportDatasource
     * @return
     */
    public String toSelectMeasures(ReportDatasource reportDatasource){
       return super.toSelectMeasures(reportDatasource);
    }

    /**
     * 返回详细select 数据
     * @param reportDatasource
     * @return
     */
    public String toSelectDetailMeasures(ReportDatasource reportDatasource) {

        String dbTypeEnum = reportDatasource.getDatabaseType();
        Set<ReportMeasure> measures = reportDatasource.getReportDynamicParam().getMeasures();

        String selectSql = "";

        if(CollectionUtils.isNotEmpty(measures)){
            StringBuilder builder = new StringBuilder();

            measures.forEach(x -> {
                MeasureTypeEnum measureEnum = x.getMeasureType();

                if (!MeasureTypeEnum.CUSTOM.equals(measureEnum)) {
                    //系统级
                    String measureType = measureEnum.getMeasureType();
                    builder.append( x.getFieldCode() + " as \"" + x.getFieldName() + "\",");
                }
            });
            selectSql = builder.substring(0,builder.lastIndexOf(","));
        }


        return selectSql;

    }

    /**
     * 查询详细sql
     * @param reportDatasource
     * @return
     */
    @Override
    public Map<String,Object> reportCoreDetail(ReportDatasource reportDatasource) {
        Map<String,Object> tmp = new HashMap<>();

        //前台动态参数
        ReportDynamicParam reportDynamicParam = reportDatasource.getReportDynamicParam();
        //主题
        String subjectType = reportDatasource.getSubjectType();
        //数据库类型
        String dbTypeEnumByName = reportDatasource.getDatabaseType();

        List<Dimension> line = reportDynamicParam.getLine();  //行维度

        //给仍出来
        String filterSql = toWhereSqlFtiler(reportDatasource);

        Set<ReportMeasure> measures = reportDynamicParam.getMeasures();
        if(SubjectTypeEnum.DATATABLE.getCode().equals(subjectType)){

            StringBuilder builderSelect = new StringBuilder(" select ");

            StringBuilder builderWhe = new StringBuilder(" where 1 = 1 ");

            ReportTable reportTable = reportDatasource.getReportTables().get(0);

            //fieldIds 指标的fieldId
            List<Long> fieldIds = conversionDetailFoSql(dbTypeEnumByName, line, builderSelect, builderWhe);

            String selectSql = getMeauresDetail(measures, fieldIds);

            String select = null;
            if(StringUtil.isEmpty(selectSql)){
                select = builderSelect.substring(0,builderSelect.lastIndexOf(","));
            }else{
                select = builderSelect.append(selectSql).toString();
            }

            StringBuilder builder = new StringBuilder(select);
            builder.append("  from " + reportTable.getTableCode() );
            builder.append(builderWhe).append(filterSql);

            //sql
            tmp.put(KEY_SQL,builder.toString());
            //字段集合
            tmp.put(KEY_FIELD_IDS,fieldIds);
        }else if(SubjectTypeEnum.SUBJECT.getMsg().equals(subjectType)){
            //主题 （多张表）

        }

        return tmp;
    }

    @Override
    public List<RptDataTable> queryAllTables(Connection conn, DataBaseType dataBaseType) throws DbException {
        List<RptDataTable> tables = new ArrayList<RptDataTable>();
        ResultSet rs = null;

        try {
            // 数据库

            String sql = "select TABNAME,REMARKS from syscat.tables where tabschema = current schema ";
            PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();


            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                RptDataTable rptDataTable = new RptDataTable();

                tables.add(rptDataTable);

                String tabName = rs.getString(TABNAME);
                String remarks = rs.getString(REMARKS);
                rptDataTable.setTableName(tabName);
                if(StringUtil.isEmpty(remarks)){
                    remarks = "此表描述为空,若需要,请去对应的表写入。";
                }
                rptDataTable.setComment(remarks);
            }
        } catch (SQLException e) {
            logger.error("error = {}",e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rs);
        }

        return tables;
    }
}
