package com.ebase.report.dao;

import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.model.dynamic.ReportEchoBody;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RptPersonalSubjectMapper {
    int deleteByPrimaryKey(Long personalSubjectId);

    int insert(RptPersonalSubject record);

    int insertSelective(RptPersonalSubject record);

    RptPersonalSubject selectByPrimaryKey(Long personalSubjectId);

    int updateByPrimaryKeySelective(RptPersonalSubject record);

    int updateByPrimaryKey(RptPersonalSubject record);

    List<RptPersonalSubject> select(RptPersonalSubject record);

    ReportEchoBody getThemeDataSource(Long personalSubjectId);

    List<RptPersonalSubject> selectAll();

    List<RptPersonalSubject> selectByCreatedBy(Map map);

    RptPersonalSubject selectDataByPrimaryKey(Long personalSubjectId);

    int deleteByRoleId(Long id);

    int deleteByUserId(Long id);

    int insertSelectiveList(@Param("rptPersonalSubjects") List<RptPersonalSubject> rptPersonalSubjects);

    List<Long> selectIdByRoleId(Long id);

    List<Long> selectIdByUserId(Long id);

    Integer selectCountByTypeId(Map<String, Object> tmp);

    List<Long> selectIdByTableId(Long tableId);

    int deleteByTableId(Long tableId);
}