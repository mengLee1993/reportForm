package com.ebase.report.model;

import java.util.List;

public class RptDataAuth {

    private Long id;  //角色 或者 账号id

    private Byte type;  //0  角色   1  账号

    private List<RptDataTable> tableIds; // 多个数据表id


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<RptDataTable> getTableIds() {
        return tableIds;
    }

    public void setTableIds(List<RptDataTable> tableIds) {
        this.tableIds = tableIds;
    }
}
