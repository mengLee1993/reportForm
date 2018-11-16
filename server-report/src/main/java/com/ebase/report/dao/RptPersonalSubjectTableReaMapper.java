package com.ebase.report.dao;

import com.ebase.report.model.RptPersonalSubjectTableRea;
import java.util.List;

public interface RptPersonalSubjectTableReaMapper {
    int deleteByPrimaryKey(Long reaId);

    int insert(RptPersonalSubjectTableRea record);

    int insertSelective(RptPersonalSubjectTableRea record);

    RptPersonalSubjectTableRea selectByPrimaryKey(Long reaId);

    int updateByPrimaryKeySelective(RptPersonalSubjectTableRea record);

    int updateByPrimaryKey(RptPersonalSubjectTableRea record);

    List<RptPersonalSubjectTableRea> select(RptPersonalSubjectTableRea record);

    List<RptPersonalSubjectTableRea> selectAll();
}