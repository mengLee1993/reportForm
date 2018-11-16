package com.ebase.report.vo;

import com.ebase.report.cube.Dimension;

import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class ReportQueryParam {
    // 数据库名
    private String schema;
    private String tableName;

    // 行维度集合
    private List<Dimension> line;

    // 列维度集合
    private List<Dimension> column; //列

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

    public List<Dimension> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Dimension> measures) {
        this.measures = measures;
    }
}
