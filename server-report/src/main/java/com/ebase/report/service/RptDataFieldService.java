package com.ebase.report.service;

import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.model.ReportDetail;
import com.ebase.report.vo.RptDataDictVO;
import com.ebase.report.vo.RptDataFieldVO;

import java.util.List;
import java.util.Map;

public interface RptDataFieldService {

    List<RptDataFieldVO> queryForList(RptDataFieldVO vo);

    ServiceResponse<Integer> add(RptDataFieldVO vo);

    ServiceResponse<Integer> removeByPrimaryKey(RptDataFieldVO vo);

    ServiceResponse<Integer> removeByIds(RptDataFieldVO vo);

    ServiceResponse<Integer> updateByPrimaryKey(RptDataFieldVO vo);

    List<RptDataDictVO> findSelective(RptDataFieldVO vo);

    ServiceResponse<Integer> updateBatch(List<RptDataFieldVO> list);

    /**
     * @param:
     * @return:
     * @description:  分页
     * @author: lirunze
     * @Date: 2018/11/3
     */
    List<RptDataFieldVO> queryList(RptDataFieldVO vo);

    /**
     * @param:
     * @return:
     * @description:  元数据分页
     * @author: lirunze
     * @Date: 2018/11/3
     */
    List<RptDataFieldVO> queryMetadataList(RptDataFieldVO vo);

    /**
     * @param:
     * @return:
     * @description:  抽取元数据
     * @author: lirunze
     * @Date: 2018/11/5
     */
    Boolean extractMetadata(List<RptDataFieldVO> list);


    Integer extractMetaDataCount(RptDataFieldVO vo) throws DbException;

    //获得fields
    ReportDetail getFieldsByMap(Map<String, Object> tmpMap);
}
