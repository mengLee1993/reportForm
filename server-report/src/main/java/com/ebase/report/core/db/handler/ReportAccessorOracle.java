package com.ebase.report.core.db.handler;

import com.ebase.report.common.*;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.cube.Dimension;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.*;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportAccessorOracle extends AbstractAccessor {
    private static Logger logger = LoggerFactory.getLogger(ReportAccessorOracle.class);
    private static String TABLE_NAME = "TABLE_NAME";
    private static String COMMENTS = "COMMENTS";
    @Override
    public List<RptDataField> queryColumns(Connection conn, String tableName) throws DbException {
        List<RptDataField> fieldList = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select  upper(t1.column_name), lower(t1.data_type),  t2.comments " +
                    " from  user_col_comments  t2  left  join  user_tab_columns  t1 " +
                    " on  t1.table_name  =  t2.table_name  and  t1.column_name  =  t2.column_name " +
                    " where  t1.table_name  =  ?";
            //
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tableName);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RptDataField field = new RptDataField();

                field.setFieldCode(rs.getString(1));
                field.setFieldType(typesConvert(rs.getString(2)));
                field.setFieldName(rs.getString(3));

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
     * oracle 整体和mysql一样  就是as 不加 ''
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

        List<Dimension> line = reportDynamicParam.getLine();  //行维度

        List<Dimension> column = reportDynamicParam.getColumn();  //列维度

        String filterSql = toWhereSqlFtiler(reportDatasource);

        //这个拼指标的sql 如果没有指标 就是
        String selectSql = toSelectMeasures(reportDatasource);

        if(SubjectTypeEnum.DATATABLE.getCode().equals(subjectType)){
            StringBuilder builderSelect = new StringBuilder(" select ");

            StringBuilder builderWhe = new StringBuilder(" where 1 = 1 ");

            StringBuilder builderGroup = new StringBuilder(" group by ");
            ReportTable reportTable = reportDatasource.getReportTables().get(0);

            //行维度转换
            conversionFoSql(dbTypeEnumByName, line, builderSelect, builderWhe, builderGroup);

            //列维度转换
            conversionFoSql(dbTypeEnumByName, column, builderSelect, builderWhe, builderGroup);

            builderSelect.append(selectSql);
            builderSelect.append(" from " + reportTable.getTableCode() );
            builderSelect.append(builderWhe).append(filterSql).append(builderGroup.substring(0,builderGroup.lastIndexOf(",")));

            sql = builderSelect.toString();

        }else if(SubjectTypeEnum.SUBJECT.getMsg().equals(subjectType)){
            //主题 （多张表）

        }
        return sql;
    }


    /**
     * 转换sql
     * @param dbTypeEnumByName
     * @param line
     * @param builderSelect
     * @param builderWhe
     * @param builderGroup
     */
    private void conversionFoSql(String dbTypeEnumByName, List<Dimension> line, StringBuilder builderSelect, StringBuilder builderWhe, StringBuilder builderGroup) {
        line.forEach(x -> {
            DemandType demandType = x.getDemandType();

            if(DemandType.DIMENSION.equals(demandType)){
                //维  度
                String code = x.getFieldCode(); //name age as f124
                builderSelect.append(code + " as " + x.getKey() + ",");
                builderGroup.append(code + " ,");
                String sql = x.toWHereSql(dbTypeEnumByName);
                builderWhe.append(sql);
            }else{
                //度量值
            }

        });
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

            StringBuilder builder = new StringBuilder(select);
            builder.append("  from " + reportTable.getTableCode() );
            builder.append(builderWhe).append(filterSql);

            StringBuilder builderCount = new StringBuilder(" select COUNT(1) ");
            builderCount.append("  from " + reportTable.getTableCode() );
            builderCount.append(builderWhe).append(filterSql);

            dataDetailSQL.setSql(builder.toString());
            dataDetailSQL.setSqlCount(builderCount.toString());

        }else if(SubjectTypeEnum.SUBJECT.getMsg().equals(subjectType)){
            //主题 （多张表）

        }
        return dataDetailSQL;


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
                builderSelect.append(code + " as " + x.getKey() + ",");
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
        List<File> files = new ArrayList<>();


        //看数据量是否过大
        if(count < LENGTH){
            generateTmpMap(conn, files, sql);
        }else{

            StringBuilder builder = new StringBuilder("SELECT * FROM ( SELECT A.*, ROWNUM AS RN FROM ( ");
            builder.append(sql);
            builder.append(") A");

            String prefix = " WHERE ROWNUM <= ";
            String suffix = " ) WHERE RN >= ";

            int size = count / LENGTH;
            size = count % LENGTH == 0 ? size - 1 : size;
            for(int i = 0; i <= size ; i ++){
                String sqlP = prefix + (i + 1) * LENGTH ;
                String sqlS = suffix + (i * LENGTH + 1) ;

                StringBuilder s = new StringBuilder(builder);
                String str = s.append(sqlP).append(sqlS).toString();

                generateTmpMap(conn, files, str);
            }
        }

        return files;
    }


    /**
     * 根据数据库类型 得sqlwhere条件过滤sql
     * @param reportDatasource
     * @return
     */
    public String toWhereSqlFtiler(ReportDatasource reportDatasource){
        String dbTypeEnum = reportDatasource.getDatabaseType();
        List<FilterArea> filter = reportDatasource.getReportDynamicParam().getFilter();
        String sqlFtiler = "";

        StringBuilder builder = new StringBuilder();
        //如果是时间类型 可能有范围
        filter.forEach(x -> {

            //是时间
            List<FilterAreaValue> tmp = x.getTmp();
            tmp.stream().filter(z -> z.getIsChecked() == 1).forEach(y -> {
                Map<FilterTypeEnum, String> fieldValue = y.getFieldVal();
                fieldValue.keySet().forEach(f -> {
                    String value = fieldValue.get(f);
                    if(FilterTypeEnum.isScope(f) && !StringUtil.isEmpty(value)){
                        builder.append(" and " + x.getCode() + " " + f.getName() + " '" + value + "' ");
                    }else if(FilterTypeEnum.RG.equals(f) && !StringUtil.isEmpty(value)){
                        builder.append(" and " + x.getCode() + " " + f.getName() + " '%" + value + "%' ");
                    }else if(!StringUtil.isEmpty(value)){
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

    /**
     * 返回 select sql
     * @param reportDatasource
     * @return
     */
    public String toSelectMeasures(ReportDatasource reportDatasource){
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
                    builder.append(measureType + "(" + x.getFieldCode() + ") as " + x.getKey() + ",");
                }

            });
            selectSql = builder.substring(0,builder.lastIndexOf(","));
        }

        return selectSql;
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
                    builder.append( x.getFieldCode() + " as " + x.getKey() + ",");
                }
//

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
            List<Long> fieldIds2 = conversionDetailFoSql(dbTypeEnumByName, reportDynamicParam.getColumn(), builderSelect, builderWhe);
            fieldIds.addAll(fieldIds2);

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

    /**
     * 查询oracle数据库中表的名称+表的注释
     * @param conn
     * @param dataBaseType
     * @return
     * @throws DbException
     */
    @Override
    public List<RptDataTable> queryAllTables(Connection conn, DataBaseType dataBaseType) throws DbException {
        List<RptDataTable> tables = new ArrayList<RptDataTable>();
        //用于表示sql中参数的位置
        int i = 1;
        //查询表名的语句执行对象
        PreparedStatement pTableName = null;

        //装有表名的结果集
        ResultSet rsTN = null;
        //装有注释的结果集
        ResultSet rsCT = null;

        String tableName = null;
        Object comment = null;
        try {

            DatabaseMetaData dbMetaData = conn.getMetaData();
            String schemaPattern = null;// meta.getUserName();

            if (DataBaseType.DB_TYPE_ORACLE.equals(dataBaseType)) {
                schemaPattern = dbMetaData.getUserName().toUpperCase();
            } else if (DataBaseType.DB_TYPE_MYSQL.equals(dataBaseType)) {

            }else {
                throw new RuntimeException("不认识的数据库类型!");
            }
            //获取数据库中所有的表<不包括系统表>
            String sqlTableName = "SELECT TABLE_NAME FROM USER_TABLES";
            String sqlComment   = "SELECT COMMENTS FROM USER_TAB_COMMENTS WHERE TABLE_NAME=?";

            pTableName = conn.prepareStatement(sqlTableName);
            rsTN = pTableName.executeQuery();
            while (rsTN.next()) {

                tableName = rsTN.getObject(TABLE_NAME).toString();

                //查询注释的语句执行对象
                PreparedStatement pConment  = conn.prepareStatement(sqlComment);
                pConment.setString(i , tableName);
                rsCT = pConment.executeQuery();
                while (rsCT.next()){
                    comment = rsCT.getObject(COMMENTS);
                    RptDataTable rptDataTable = new RptDataTable();
                    rptDataTable.setTableCode(tableName);
                    if(comment == null){
                        String str = "此表描述为空,若需要,请去对应的表写入";
                        rptDataTable.setComment(str);
                    }else {
                        rptDataTable.setComment(comment.toString());
                    }
                    tables.add(rptDataTable);
                }

                DataBaseUtil.closeStatment(pConment);
            }
        } catch (SQLException e) {
            logger.error("获取数据字段描述异常：", e);
            throw new DbException(e);
        } finally {
            DataBaseUtil.closeResultSet(rsTN);
            DataBaseUtil.closeResultSet(rsCT);
            DataBaseUtil.closeStatment(pTableName);
        }
        return tables;
    }
}
