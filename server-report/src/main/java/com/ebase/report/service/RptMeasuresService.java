package com.ebase.report.service;

import com.ebase.report.model.RptMeasures;

public interface RptMeasuresService {

    /**
     * 根据指标id 删除指标
     * @param reqBody
     * @return
     */
    int delRptMeasures(RptMeasures reqBody);

    /**
     * 验证同个主题下，的同类型指标是否重复
     * @param reqBody
     * @return
     */
    Integer getRptMeasuresByService(RptMeasures reqBody);
}
