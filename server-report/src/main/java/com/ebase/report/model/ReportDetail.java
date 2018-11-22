package com.ebase.report.model;

import java.util.List;

public class ReportDetail {

    private String sql;

    private List<RptDataField>  fieldList;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<RptDataField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<RptDataField> fieldList) {
        this.fieldList = fieldList;
    }
}
