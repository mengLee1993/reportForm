package com.ebase.report.dao;

import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.dynamic.ReportTable;
import com.ebase.report.vo.RptDataTableVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RptDataTableMapper {
    int deleteByPrimaryKey(Long tableId);

    int insertSelective(RptDataTable record);

    RptDataTable selectByPrimaryKey(Long tableId);

    int updateByPrimaryKeySelective(RptDataTable record);

    int updateByPrimaryKey(RptDataTable record);

    List<RptDataTable> select(RptDataTable record);

    List<RptDataTable> queryAllByDataSource(RptDataTableVO vo);

    int insert(RptDataTable rdt);

    List<ReportTable> selectReportTableByTableId(Long tableId);

    List<RptDataTable> queryThemeTable(RptDataTable copy);

    List<RptDataTable> selectbyTableName(RptDataTable rpt);

    RptDataTable selectByTableId(Long tableId);

    List<RptDataTable> selectByTableIds(@Param("tableIds") List<Long> tableIds);

    List<RptDataTable> selectByDatasourceId(Long datasourceId);

    //计数
    Integer selectCount(RptDataTable model);
    //使用limit分页
    List<RptDataTable> selectByPage(RptDataTable model);

    int deleteByDatasourceId(Long datasourceId);

    Integer deleteById(Long tableId);
}