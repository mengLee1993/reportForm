package com.ebase.report.cube;

import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.DemandType;
import com.ebase.report.common.FilterTypeEnum;
import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.model.dynamic.CustomIndex;
import com.ebase.report.model.dynamic.ReportDimension;
import com.ebase.report.model.dynamic.ReportMeasure;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@JsonIgnoreProperties(value = {"key"},ignoreUnknown = true)
public class Dimension {

    //字段id
    private String fieldId;  //f1

    // 维度code               //自定义指标的时候没有这个值
    private String fieldCode;

    // 维度名称
    private String fieldName;

    // 行区还是列区
    private String position;

    // 是否小计
    private boolean subTotal = false;

    // 是维度 还是度量值 (dimension measures)
    protected DemandType demandType = DemandType.DIMENSION;

    // lev
    private int lev;

    //新增的
//    private List<Dimension> measures; //度量值  多个 人家可能勾选几个   如果这里面的  那么name 就是 多个库里 的  value
    private List<ReportMeasure> rptMeasures; //多个度量值 在里面

    //这下都是维度才有的值
    private DBFieldTypeEnum dbFieldTypeEnum = DBFieldTypeEnum.VARCHAR;  // 当前字段的类型 给我一个类型

    //是否行级别授权   0 否 1 是    行级授权下才能多个维度查询
    private Byte rowLevelAuth = 0;

    //!! 维度  key: < > != ==    value:值
    private Map<FilterTypeEnum, List<String>> expressionTmp = new HashMap<>(); //表达式 维度时

    private List<ReportDimension> rptDataDicts; //多个维度  /还得类型1   rptDataDicts

    //是否拖到过过滤区
    private Boolean searchTrue = false;
    // !度量值
//    private MeasureTypeEnum measureEnum = MeasureTypeEnum.COUNT;   //如果是指标 是什么指标

//    private String expressionMetric; //表达式   度量值时

    //  key: 字段id， 字段code码
//    private List<CustomIndex> customIndexTmp = new ArrayList<>();  //自定义度量值时的变量

    public String getKey() {
        String suffix = "";
//        if (DemandType.MEASURES.equals(demandType)) {
//            suffix = "_" + measureEnum.getCode();
//        }
        return demandType.getPrefix() + getFieldId() + suffix;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPosition() {
        return position;
    }

    public Dimension setPosition(String position) {
        this.position = position;
        return this;
    }

    public boolean isSubTotal() {
        return subTotal;
    }

    public Dimension setSubTotal(boolean subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public DemandType getDemandType() {
        return demandType;
    }

    public Dimension setDemandType(DemandType demandType) {
        this.demandType = demandType;
        return this;
    }

    public int getLev() {
        return lev;
    }

    public Dimension setLev(int lev) {
        this.lev = lev;
        return this;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

//    public List<Dimension> getMeasures() {
//        return measures;
//    }

//    public void setMeasures(List<Dimension> measures) {
//        this.measures = measures;
//    }

    public DBFieldTypeEnum getDbFieldTypeEnum() {
        return dbFieldTypeEnum;
    }

    public void setDbFieldTypeEnum(DBFieldTypeEnum dbFieldTypeEnum) {
        this.dbFieldTypeEnum = dbFieldTypeEnum;
    }

    public Byte getRowLevelAuth() {
        return rowLevelAuth;
    }

    public void setRowLevelAuth(Byte rowLevelAuth) {
        this.rowLevelAuth = rowLevelAuth;
    }

    public Map<FilterTypeEnum, List<String>> getExpressionTmp() {
        return expressionTmp;
    }

    public void setExpressionTmp(Map<FilterTypeEnum, List<String>> expressionTmp) {
        this.expressionTmp = expressionTmp;
    }

    public List<ReportDimension> getRptDataDicts() {
        return rptDataDicts;
    }

    public void setRptDataDicts(List<ReportDimension> rptDataDicts) {
        this.rptDataDicts = rptDataDicts;
    }

    //    public MeasureTypeEnum getMeasureEnum() {
//        return measureEnum;
//    }
//
//    public void setMeasureEnum(MeasureTypeEnum measureEnum) {
//        this.measureEnum = measureEnum;
//    }

//    public String getExpressionMetric() {
//        return expressionMetric;
//    }

//    public void setExpressionMetric(String expressionMetric) {
//        this.expressionMetric = expressionMetric;
//    }

//    public List<CustomIndex> getCustomIndexTmp() {
//        return customIndexTmp;
//    }

//    public void setCustomIndexTmp(List<CustomIndex> customIndexTmp) {
//        this.customIndexTmp = customIndexTmp;
//    }


    public Boolean getSearchTrue() {
        return searchTrue;
    }

    public void setSearchTrue(Boolean searchTrue) {
        this.searchTrue = searchTrue;
    }

    public List<ReportMeasure> getRptMeasures() {
        return rptMeasures;
    }

    public void setRptMeasures(List<ReportMeasure> rptMeasures) {
        this.rptMeasures = rptMeasures;
    }

    /**
     * 告诉我 数据库类型
     */
    public String toWHereSql(String dbTypeEnum) {

        String wSql = null;
        if (demandType.equals(DemandType.DIMENSION)) {
            if (DataBaseType.TYPE_NAME_MYSQL.equals(dbTypeEnum)) {
                //mysql
                StringBuilder builder = new StringBuilder("");
                //是行级别授权
                if ((rowLevelAuth == (byte) 1)) {
                    if (CollectionUtils.isNotEmpty(rptDataDicts)) {
                        StringBuilder stringBuilder = new StringBuilder(" and " + fieldCode + " in ( ");

                        if (DBFieldTypeEnum.isNumber(dbFieldTypeEnum)) {
                            rptDataDicts.stream().filter(x -> x.getIsChecked() == 1).forEach(z -> {
                                stringBuilder.append(z.getFieldValue() + ",");
                            });
                        } else {
                            rptDataDicts.stream().filter(x -> x.getIsChecked() == 1).forEach(z -> {
                                stringBuilder.append(" '" + z.getFieldValue() + "', ");
                            });
                        }
                        String s = stringBuilder.substring(0, stringBuilder.lastIndexOf(",")).toString();
                        builder.append(s);
                        builder.append(" ) ");
                    }
                }

                for (FilterTypeEnum cond : expressionTmp.keySet()) {
                    //是否是范围
                    if (FilterTypeEnum.isScope(cond)) {
                        //是范围
                        builder.append(" and " + fieldCode + cond.getName() + " '" + expressionTmp.get(cond).get(0) + "' ");
                    } else {
                        String whw = "";
                        if (cond.equals(FilterTypeEnum.EQ)) {
                            whw = "in";
                        } else {
                            whw = "not in";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(" and " + fieldCode + " " + whw + " (");
                        expressionTmp.get(cond).forEach(y -> {
                            stringBuilder.append(" '" + y + "',");
                        });
                        String s = stringBuilder.substring(0, stringBuilder.lastIndexOf(","));
                        builder.append(s);
                        builder.append(") ");
                    }
                }
                wSql = builder.toString();
            } else if (DataBaseType.TYPE_NAME_ORACLE.equals(dbTypeEnum)) {
                //oracle

            }
        } else if (demandType.equals(DemandType.MEASURES)) {
            //度量值  不算where 条件的
        }

        return wSql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimension dimension = (Dimension) o;
        return Objects.equals(fieldId, dimension.fieldId) &&
                Objects.equals(fieldCode, dimension.fieldCode) &&
                Objects.equals(fieldName, dimension.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId, fieldCode, fieldName);
    }
}
