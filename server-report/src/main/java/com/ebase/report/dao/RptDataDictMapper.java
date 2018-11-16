package com.ebase.report.dao;

import com.ebase.report.model.RptDataDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RptDataDictMapper {
    int deleteByPrimaryKey(Long dataDictId);

    int insert(RptDataDict record);

    int insertSelective(RptDataDict record);

    RptDataDict selectByPrimaryKey(Long dataDictId);

    int updateByPrimaryKeySelective(RptDataDict record);

    int updateByPrimaryKey(RptDataDict record);

    List<RptDataDict> select(RptDataDict record);

    List<RptDataDict> queryForList(RptDataDict rptDs);

    List<RptDataDict> selectByFieldId(Long fieldId);

    int insertBatch(@Param("dataDicts") List<RptDataDict> dataDicts);

    int deleteByFieldId(Long fieldId);
}