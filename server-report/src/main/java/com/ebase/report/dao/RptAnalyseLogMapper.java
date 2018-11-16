package com.ebase.report.dao;

import com.ebase.report.model.RptAnalyseLog;

import java.util.List;

public interface RptAnalyseLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(RptAnalyseLog record);

    int insertSelective(RptAnalyseLog record);

    RptAnalyseLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(RptAnalyseLog record);

    int updateByPrimaryKeyWithBLOBs(RptAnalyseLog record);

    int updateByPrimaryKey(RptAnalyseLog record);

    List<RptAnalyseLog> select(RptAnalyseLog record);

    RptAnalyseLog selectReportSql(String opUserid);

    Integer selectCount(RptAnalyseLog model);
}