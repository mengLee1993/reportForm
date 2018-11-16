package com.ebase.report.dao;

import com.ebase.report.model.RptDimension;
import java.util.List;

public interface RptDimensionMapper {
    int deleteByPrimaryKey(Long dimensionId);

    int insert(RptDimension record);

    int insertSelective(RptDimension record);

    RptDimension selectByPrimaryKey(Long dimensionId);

    int updateByPrimaryKeySelective(RptDimension record);

    int updateByPrimaryKey(RptDimension record);

    List<RptDimension> select(RptDimension record);
}