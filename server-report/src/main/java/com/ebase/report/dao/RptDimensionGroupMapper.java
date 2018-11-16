package com.ebase.report.dao;

import com.ebase.report.model.RptDimensionGroup;
import java.util.List;

public interface RptDimensionGroupMapper {
    int insert(RptDimensionGroup record);

    int insertSelective(RptDimensionGroup record);

    List<RptDimensionGroup> select(RptDimensionGroup record);
}