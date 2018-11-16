package com.ebase.report.vo;

import com.ebase.report.cube.Dimension;

import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class DimensionResponse {
    // 数据库名
    private String schema;
    private String tableName;

    // 行维度集合
    private List<Dimension> dimensionList;

    // 度量值 measures
    List<Dimension> measures;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Dimension> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<Dimension> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public List<Dimension> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Dimension> measures) {
        this.measures = measures;
    }
}
