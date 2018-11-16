package com.ebase.report.dao;

import com.ebase.report.model.RptPersonalTableField;
import java.util.List;

public interface RptPersonalTableFieldMapper {
    int deleteByPrimaryKey(Long persionalTableFieldId);

    int insert(RptPersonalTableField record);

    int insertSelective(RptPersonalTableField record);

    RptPersonalTableField selectByPrimaryKey(Long persionalTableFieldId);

    int updateByPrimaryKeySelective(RptPersonalTableField record);

    int updateByPrimaryKey(RptPersonalTableField record);

    List<RptPersonalTableField> select(RptPersonalTableField record);

    List<RptPersonalTableField> selectAll();
}