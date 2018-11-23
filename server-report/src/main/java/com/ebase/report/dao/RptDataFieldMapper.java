package com.ebase.report.dao;

import com.ebase.report.model.RptDataDict;
import com.ebase.report.model.RptDataField;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RptDataFieldMapper {
    Integer deleteByPrimaryKey(Long fieldId);

    int insertSelective(RptDataField record);

    RptDataField selectByPrimaryKey(Long fieldId);

    int updateByPrimaryKeySelective(RptDataField record);

    List<RptDataField> queryForList(RptDataField rptDs);

    //通过表名查询列名
    RptDataField selectByTableName(String tableName);

    List<RptDataField> selectByTableId(Map tmp);

    List<RptDataDict> select(RptDataField model);

    int insertBatch(@Param("fields") List<RptDataField> fields);

    List<RptDataField> selectList(RptDataField copy);

    List<RptDataField> selectByPrimaryKeys(@Param("fields")List<Long> lds);

    List<Long> selectIdByTableid(Long tableId);

    Integer deleteByTableId(Long tableId);
}