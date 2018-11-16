package com.ebase.report.model.dynamic;


import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.FilterTypeEnum;
import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.cube.Dimension;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 动态参数
 *
 */

public class ReportDynamicParam {

    private Long personalSubjectId; //主题 id  给一个主题 别都数据都能获取

    //注：如果是度量 可能有自定义度量值

    // 行维度/度量 集合
    private List<Dimension> line = new ArrayList<>();

    // 列维度/度量 集合
    private List<Dimension> column = new ArrayList<>();

//    // 过滤条件  度量不能拖到 过滤区
    private List<FilterArea> filter = new ArrayList<>();

    // 这都是最细维度都就行了  自定义指标的时候 存里面最细指标
    private Set<ReportMeasure> measures = new HashSet<>();

    // 带选取
    private List<Dimension> tbs = new ArrayList<>();

    private Boolean dimension = false; //是否有度量值

    public Long getPersonalSubjectId() {
        return personalSubjectId;
    }

    public void setPersonalSubjectId(Long personalSubjectId) {
        this.personalSubjectId = personalSubjectId;
    }

    public List<Dimension> getLine() {
        return line;
    }

    public void setLine(List<Dimension> line) {
        this.line = line;
    }

    public List<Dimension> getColumn() {
        return column;
    }

    public void setColumn(List<Dimension> column) {
        this.column = column;
    }

    public List<FilterArea> getFilter() {
        return filter;
    }

    public void setFilter(List<FilterArea> filter) {
        this.filter = filter;
    }

//    /**
//     * 根据数据库类型 得sqlwhere条件过滤sql
//     * @param dbTypeEnum
//     * @return
//     */
//    public String toWhereSqlFtiler(String dbTypeEnum){
//        String sqlFtiler = "";
//        if(DataBaseType.TYPE_NAME_MYSQL.equals(dbTypeEnum)){
////            if(personalSubjectId == null){  //主题id 为null 默认没有
//
//                StringBuilder builder = new StringBuilder();
//                //如果是时间类型 可能有范围
//                filter.forEach(x -> {
//                    DBFieldTypeEnum dbFieldTypeEnum = x.getDbFieldTypeEnum();
//
//
//                    //是时间
//                    Map<FilterTypeEnum, List<String>> tmp = x.getTmp();
//                    for(FilterTypeEnum cond:tmp.keySet()){
//                        //是否是范围
//                        if(FilterTypeEnum.isScope(cond)){
//                            //是范围
//
//                            builder.append(" and " + x.getCode() + cond.getName() + " '" + tmp.get(cond).get(0) + "' ");
//                        }else{
//                            String whw = "";
//                            if(cond.equals(FilterTypeEnum.EQ)){
//                                whw = "in";
//                            }else{
//                                whw = "not in";
//                            }
//
//                            StringBuilder stringBuilder = new StringBuilder();
//                            stringBuilder.append(" and " + x.getCode() + " "+ whw + " (");
//                            tmp.get(cond).forEach(y -> {
//                                stringBuilder.append(" '" + y + "',");
//                            });
//                            String s = stringBuilder.substring(0,stringBuilder.lastIndexOf(","));
//                            builder.append(s);
//                            builder.append(") ");
//                        }
//
//                    }
//
//
//                });
//
//                sqlFtiler = builder.toString();
////            }
//
//        }else if(DataBaseType.TYPE_NAME_ORACLE.equals(dbTypeEnum)){
//
//        }
//
//        return sqlFtiler;
//    }


    public Set<ReportMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(Set<ReportMeasure> measures) {
        this.measures = measures;
    }

    public List<Dimension> getTbs() {
        return tbs;
    }

    public void setTbs(List<Dimension> tbs) {
        this.tbs = tbs;
    }

    public Boolean getDimension() {
        return dimension;
    }

    public void setDimension(Boolean dimension) {
        this.dimension = dimension;
    }

//    /**
//     * 返回 select sql
//     * @param dbTypeEnum
//     * @return
//     */
//    public String toSelectMeasures(String dbTypeEnum) {
//        String selectSql = "";
//
//        if(DataBaseType.TYPE_NAME_MYSQL.equals(dbTypeEnum)){
//            if(CollectionUtils.isNotEmpty(measures)){
//                StringBuilder builder = new StringBuilder();
//
//                measures.forEach(x -> {
//                    MeasureTypeEnum measureEnum = x.getMeasureEnum();
//
//                    if (!MeasureTypeEnum.CUSTOM.equals(measureEnum)) {
//                        //系统级
//                        String measureType = measureEnum.getMeasureType();
//                        builder.append(measureType + "(" + x.getCode() + ") as '" + x.getKey() + "',");
//                    }else{
//                        //自定义
//                        List<CustomIndex> customIndexTmp = x.getCustomIndexTmp();
//                        customIndexTmp.forEach(z -> {
//                            builder.append(z.getFieldCode() + " as '" + x.getKey() + "',");
//                        });
//                    }
//
//                });
//                selectSql = builder.substring(0,builder.lastIndexOf(","));
//            }
//
//        }else if(DataBaseType.TYPE_NAME_ORACLE.equals(dbTypeEnum)){
//
//        }
//
//        return selectSql;
//
//    }

//    /**
//     * 返回详细select 数据
//     * @param dbTypeEnum
//     * @return
//     */
//    public String toSelectDetailMeasures(String dbTypeEnum) {
//        String selectSql = "";
//
//        if(DataBaseType.TYPE_NAME_MYSQL.equals(dbTypeEnum)){
//            if(CollectionUtils.isNotEmpty(measures)){
//                StringBuilder builder = new StringBuilder();
//
//                measures.forEach(x -> {
//                    MeasureTypeEnum measureEnum = x.getMeasureEnum();
//
//                    if (!MeasureTypeEnum.CUSTOM.equals(measureEnum)) {
//                        //系统级
//                        String measureType = measureEnum.getMeasureType();
//                        builder.append( x.getCode() + " as '" + x.getName() + "',");
//                    }else{
//                        //自定义
//                        List<CustomIndex> customIndexTmp = x.getCustomIndexTmp();
//                        customIndexTmp.forEach(z -> {
//                            builder.append(z.getFieldCode() + " as '" + z.getMeasuresName() + "',");
//                        });
//                    }
//
//                });
//                selectSql = builder.substring(0,builder.lastIndexOf(","));
//            }
//
//        }else if(DataBaseType.TYPE_NAME_ORACLE.equals(dbTypeEnum)){
//
//        }
//
//        return selectSql;
//
//    }
}
