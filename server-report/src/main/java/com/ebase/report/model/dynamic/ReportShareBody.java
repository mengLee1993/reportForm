package com.ebase.report.model.dynamic;

import java.util.List;

public class ReportShareBody {

    private Long personalAnalysisId; //自定义报表id

    private Byte type;  //0  角色   1  账号

    private List<String> list;  //N个多个 ID

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Long getPersonalAnalysisId() {
        return personalAnalysisId;
    }

    public void setPersonalAnalysisId(Long personalAnalysisId) {
        this.personalAnalysisId = personalAnalysisId;
    }
}
