package com.ebase.report.core.db.handler;

import com.ebase.report.common.*;
import com.ebase.report.core.db.DataBaseUtil;
import com.ebase.report.core.db.DataDetailSQL;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.utils.StringUtil;
import com.ebase.report.cube.Dimension;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.dynamic.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportAccessorMySql extends AbstractAccessor {
    private static Logger logger = LoggerFactory.getLogger(ReportAccessorOracle.class);

    private final String LIMIT = " limit ";

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
            builderSelect.append(" from " + reportTable.getTableCode() );
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
                builderSelect.append(code + " as '" + x.getKey() + "',");
                builderGroup.append(code + " ,");
                String sql = x.toWHereSql(dbTypeEnumByName);
                builderWhe.append(sql);
//                boo = false;
            }else{

            }
        }

//        if(boo){
//            builderGroup = new StringBuilder("");
//        }else{
//            String substring = builderGroup.substring(0, builderGroup.lastIndexOf(","));
//            builderGroup = new StringBuilder(substring);
//        }
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

    @Override
    public String typesConvert(String type) {
        if (type.startsWith("varchar") || type.startsWith("longtext")) {
            return "String";
        } else if (type.startsWith("int") || type.startsWith("bigint")) {
            // return "Integer";Byte
            return "Long";
        } else if (type.startsWith("tinyint")) {
            return "Byte";
        } else if (type.startsWith("double")) {
            return "Double";
        } else if (type.startsWith("timestamp")) {
            return "Date";
        } else if (type.startsWith("tinyint")) {
            return "Boolean";
        } else if (type.startsWith("date")) {
            return "Date";
        } else if (type.startsWith("decimal")) {
            return "BigDecimal";
        }
        return type;
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
    private void conversionDetailFoSql(String dbTypeEnumByName, List<Dimension> line, StringBuilder builderSelect, StringBuilder builderWhe) {
        line.forEach(x -> {
            DemandType demandType = x.getDemandType();

            if(DemandType.DIMENSION.equals(demandType)){
                //维  度
                String code = x.getFieldCode(); //name age as f124
                builderSelect.append(code + " as '" + x.getFieldName() + "',");
                String s = x.toWHereSql(dbTypeEnumByName);
                builderWhe.append(s);
            }else{
                //度量值

//                List<Dimension> dimensions = x.getMeasures();
//                for (Dimension y : dimensions) {//函数的类型
//                    MeasureTypeEnum measureEnum = y.getMeasureEnum();
//
//                    if (!MeasureTypeEnum.CUSTOM.equals(measureEnum)) {
//
//                        builderSelect.append( y.getCode() + " as '" + y.getName() + "',");
//
//                        //度量值不需要分组的
//                    } else {
//                        //自定义的表达式  变量都用Field 分割
//
//                        List<CustomIndex> customIndexTmp = y.getCustomIndexTmp();
//                        customIndexTmp.forEach(z -> {
//                            builderSelect.append(z.getCode() + " as '" + z.getName() + "',");
//                        });
//
//                    }
//                }
            }

        });
    }

    @Override
    public Map<String, List<Object>> queryFromDetail(Integer count,String sql, Connection conn, Map<String, List<Object>> tmpMap) {
        //看数据量是否过大
        if(count < LENGTH){
            tmpMap = super.queryFromDetail(count,sql,conn,tmpMap);
        }else{
            int size = count / LENGTH;
            size = count % LENGTH == 0 ? size - 1 : size;
            for(int i = 0; i <= size ; i ++){
                String s = LIMIT + (i * LENGTH) + "," + LENGTH;

                String sqlDetail = sql + s;

                tmpMap = super.queryDateil(sqlDetail,conn,tmpMap);
            }
        }

        return tmpMap;
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
                    //系统级 都是系统级的
                    String measureType = measureEnum.getMeasureType();
                    builder.append(measureType + "(" + x.getFieldCode() + ") as '" + x.getKey() + "',");
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
                    builder.append( x.getFieldCode() + " as '" + x.getFieldName() + "',");
                }
//                else{
//                    //自定义
//                    List<ReportMeasure> customIndexTmp = x.getCustomIndexTmp();
//                    customIndexTmp.forEach(z -> {
//                        builder.append(z.getFieldCode() + " as '" + z.getKey() + "',");
//                    });
//                }

            });
            selectSql = builder.substring(0,builder.lastIndexOf(","));
        }


        return selectSql;

    }
}
