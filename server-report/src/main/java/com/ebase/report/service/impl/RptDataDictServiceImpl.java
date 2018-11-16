package com.ebase.report.service.impl;

import com.ebase.report.core.pageUtil.PageDTOUtil;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.RptDataDictMapper;
import com.ebase.report.model.RptDataDict;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.service.RptDataDictService;
import com.ebase.report.vo.RptDataDictVO;
import com.ebase.report.vo.RptPersonalSubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RptDataDictServiceImpl implements RptDataDictService {

    @Autowired
    private RptDataDictMapper rddMapper;


    @Transactional(readOnly = true)
    public List<RptDataDictVO> queryForList(RptDataDictVO vo) {
        RptDataDict rptDs=new RptDataDict();
        BeanCopyUtil.copy(vo,rptDs);
        List<RptDataDict> list = rddMapper.queryForList(rptDs);
        List<RptDataDictVO> roleInfoVo= BeanCopyUtil.copyList(list, RptDataDictVO.class);
        return roleInfoVo;
    }

    public ServiceResponse<Integer> add(RptDataDictVO vo) {
        RptDataDict rdd = BeanCopyUtil.copy(vo, RptDataDict.class);
        Date date = new Date();
        rdd.setCreatedDt(date);
        int num = rddMapper.insert(rdd);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(num);
        return sR;
    }


    public ServiceResponse<Integer> updateByPrimaryKey(RptDataDictVO vo) {
        RptDataDict rdd = BeanCopyUtil.copy(vo, RptDataDict.class);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        Date date = new Date();
        rdd.setUpdatedDt(date);
        sR.setRetContent(rddMapper.updateByPrimaryKey(rdd));
        return sR;
    }

    public ServiceResponse<Integer> removeByPrimaryKey(RptDataDictVO vo) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(rddMapper.deleteByPrimaryKey(vo.getFieldId()));
        return sR;
    }

}
