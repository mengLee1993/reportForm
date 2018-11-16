package com.ebase.report.service;

import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.RptDataAuth;
import com.ebase.report.model.RptDatasource;

import java.util.List;

public interface RptDataAuthService {

    /**
     * 初始化 数据授权树
     * @return
     */
    List<RptDatasource> dataAuthTree(AcctInfo acctInfo);

    /**
     * 分配数据
     * @param rptDataAuth
     * @return
     */
    Boolean keepDataAuth(RptDataAuth rptDataAuth);
}
