package com.ebase.report.dao;

import com.ebase.report.model.RptMeasures;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RptMeasuresMapper {
    int deleteByPrimaryKey(Long measureId);

    int insert(RptMeasures record);

    int insertSelective(RptMeasures record);

    RptMeasures selectByPrimaryKey(Long measureId);

    int updateByPrimaryKeySelective(RptMeasures record);

    int updateByPrimaryKeyWithBLOBs(RptMeasures record);

    int updateByPrimaryKey(RptMeasures record);

    List<RptMeasures> select(RptMeasures record);

    List<RptMeasures> selectBySubjectId(Long personalSubjectId);

    List<RptMeasures> selectSystemBySubjectId(Long personalSubjectId);

    List<RptMeasures> selectBySubjectIdAcctId(Long personalSubjectId, Long acctId);

    int deleteBySubjectId(@Param("longs") List<Long> longs);

    Integer selectCountByService(RptMeasures reqBody);
}