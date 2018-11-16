package com.ebase.report.dao;

import com.ebase.report.model.RptPersonalSubjectTable;
import java.util.List;

public interface RptPersonalSubjectTableMapper {
    int deleteByPrimaryKey(Long persionalSubjectTableId);

    int insert(RptPersonalSubjectTable record);

    int insertSelective(RptPersonalSubjectTable record);

    RptPersonalSubjectTable selectByPrimaryKey(Long persionalSubjectTableId);

    int updateByPrimaryKeySelective(RptPersonalSubjectTable record);

    int updateByPrimaryKey(RptPersonalSubjectTable record);

    List<RptPersonalSubjectTable> select(RptPersonalSubjectTable record);

    List<RptPersonalSubjectTable> selectAll();


}