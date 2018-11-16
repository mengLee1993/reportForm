package com.ebase.report.dao;

import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.dynamic.ReportTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RptPersonalAnalysisMapper {
    int deleteByPrimaryKey(Long personalAnalysisId);

    int insert(RptPersonalAnalysis record);

    int insertSelective(RptPersonalAnalysis record);

    RptPersonalAnalysis selectByPrimaryKey(Long personalAnalysisId);

    int updateByPrimaryKeySelective(RptPersonalAnalysis record);

    int updateByPrimaryKeyWithBLOBs(RptPersonalAnalysis record);

    int updateByPrimaryKey(RptPersonalAnalysis record);

    List<RptPersonalAnalysis> select(RptPersonalAnalysis record);

    List<RptPersonalAnalysis> getByUserId(String userId);

    Integer listReportFormCount(Map<String, Object> tmp);

    List<ReportTable> listReportForm(Map<String, Object> tmp);

    Integer selectCount(RptPersonalAnalysis model);

    Integer selectByName(String reportName);

    int deleteBySubjectId(@Param("longs") List<Long> longs);
}