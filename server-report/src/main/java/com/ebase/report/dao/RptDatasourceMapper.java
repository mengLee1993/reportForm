package com.ebase.report.dao;

import com.ebase.report.model.RptDatasource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RptDatasourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RptDatasource record);

    RptDatasource selectByPrimaryKey(Long id);

    List<RptDatasource> selectAll();

    int updateByPrimaryKey(RptDatasource record);

    int updateByPrimaryKeySelective(RptDatasource record);

    List<RptDatasource> queryForList(RptDatasource roleGroup);

    List<RptDatasource> selectAllByDatasourceName(RptDatasource rptDatasource);

    RptDatasource selectByTableId(Long tableId);

    List<RptDatasource> selectByTableIds(@Param("tableIds") List<Long> tableIds);

    int deleteById(Long datasourceId);

    List<RptDatasource> selectByAuth(String acctId, String orgId);
}