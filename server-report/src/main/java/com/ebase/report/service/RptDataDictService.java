package com.ebase.report.service;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.vo.RptDataDictVO;
import com.ebase.report.vo.RptDataFieldVO;

import java.util.List;

public interface RptDataDictService {
    /**
     * 分页查询
     * @param vo
     * @return
     */
    List<RptDataDictVO> queryForList(RptDataDictVO vo);

    ServiceResponse<Integer> add(RptDataDictVO vo);

    ServiceResponse<Integer> removeByPrimaryKey(RptDataDictVO vo);

    ServiceResponse<Integer> updateByPrimaryKey(RptDataDictVO vo);

}
