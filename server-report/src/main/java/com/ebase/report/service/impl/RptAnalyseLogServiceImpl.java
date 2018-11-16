package com.ebase.report.service.impl;


import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.AssertContext;

import com.ebase.report.core.utils.BeanCopyUtil;

import com.ebase.report.core.utils.HttpUtils;
import com.ebase.report.dao.RptAnalyseLogMapper;
import com.ebase.report.dao.RptPersonalSubjectMapper;
import com.ebase.report.model.RptAnalyseLog;
import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.service.RptAnalyseLogService;
import com.ebase.report.vo.RptAnalyseLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RptAnalyseLogServiceImpl implements RptAnalyseLogService {


    @Autowired
    private RptAnalyseLogMapper rptAnalyseLogMapper;

    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper;

    //插入一条日志
    @Override
    public int addReportLog(RptAnalyseLog  rptAnalyseLog) {
        Long acctId = AssertContext.getAcctId();
        if(acctId == null){
            acctId = 1l;
        }

        rptAnalyseLog.setLogTime(new Date());
        rptAnalyseLog.setOpIp(HttpUtils.getRemoteHost());
        rptAnalyseLog.setOpUserid(acctId.toString());  //当前操作人id
        String acctName = AssertContext.getAcctName();
        rptAnalyseLog.setOpUserName(acctName);

        Long personalSubjectId = rptAnalyseLog.getPersonalSubjectId();
        //根据主题id 获得数据源id 和 主题名称

        RptPersonalSubject rptPersonalSubject = rptPersonalSubjectMapper.selectDataByPrimaryKey(personalSubjectId);

        rptAnalyseLog.setSubjectName(rptPersonalSubject.getSubjectName());
        rptAnalyseLog.setDatasourceId(rptPersonalSubject.getDatasourceId());
//        rptAnalyseLog.setLogTime(new);
        return rptAnalyseLogMapper.insert(rptAnalyseLog);

    }

    @Override
    public RptAnalyseLog getReportSql() {
        Long acctId = AssertContext.getAcctId();
        if(acctId == null){
            acctId = 1L;
        }
        return rptAnalyseLogMapper.selectReportSql(acctId.toString());
    }


    @Override
    public PageDTO<RptAnalyseLogVO> findSelective(RptAnalyseLogVO record){
        RptAnalyseLog model = BeanCopyUtil.copy(record, RptAnalyseLog.class);

        PageDTO<RptAnalyseLogVO> pageDTO = new PageDTO<>(record.getPageNum(),record.getPageSize());

        Integer count = rptAnalyseLogMapper.selectCount(model);
        pageDTO.setTotal(count);

        model.setStartRow(pageDTO.getStartRow());
        List<RptAnalyseLog> rptAnalyseLogs = rptAnalyseLogMapper.select(model);
        List<RptAnalyseLogVO> rptAnalyseLogVOs = BeanCopyUtil.copyList(rptAnalyseLogs, RptAnalyseLogVO.class);

        pageDTO.setResultData(rptAnalyseLogVOs);
        return pageDTO;
    }

    @Override
    public RptAnalyseLogVO getByPrimaryKey(Long key){
        RptAnalyseLog rptAnalyseLog = rptAnalyseLogMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptAnalyseLog, RptAnalyseLogVO.class);
    }

    @Override
    public Integer insert(RptAnalyseLogVO record){
        RptAnalyseLog rptAnalyseLog = BeanCopyUtil.copy(record, RptAnalyseLog.class);
        return rptAnalyseLogMapper.insert(rptAnalyseLog);

    }

    @Override
    public Integer insertSelective(RptAnalyseLogVO record){
        RptAnalyseLog rptAnalyseLog = BeanCopyUtil.copy(record, RptAnalyseLog.class);
        return rptAnalyseLogMapper.insertSelective(rptAnalyseLog);
    }

    @Override
    public Integer updateByPrimaryKey(RptAnalyseLogVO record){
        RptAnalyseLog rptAnalyseLog = BeanCopyUtil.copy(record, RptAnalyseLog.class);
        return rptAnalyseLogMapper.updateByPrimaryKey(rptAnalyseLog);
    }

    @Override
    public Integer updateByPrimaryKeySelective(RptAnalyseLogVO record){
        RptAnalyseLog rptAnalyseLog = BeanCopyUtil.copy(record, RptAnalyseLog.class);
        return rptAnalyseLogMapper.updateByPrimaryKeySelective(rptAnalyseLog);
    }

    @Override
    public Integer deleteByPrimaryKey(Long key){
        return rptAnalyseLogMapper.deleteByPrimaryKey(key);
    }

//    @Override
//    public PageDTO<RptAnalyseLogBody> listAnalyseLog(RptAnalyseLogBody vo) {
//
//        PageDTO<RptAnalyseLogBody> pageDTO = new PageDTO<>(vo.getPageNum(),vo.getPageSize());
//
//        Integer COUNT = rptAnalyseLogMapper.listAnalyseLogCount(vo);
//        pageDTO.setTotal(COUNT);
//
//        vo.setStartRow(pageDTO.getStartRow());
//
//        List<RptAnalyseLogBody> list = rptAnalyseLogMapper.listAnalyseLog(vo);
//
//        pageDTO.setResultData(list);
//        return pageDTO;
//    }

    /**
     *  IN
     *	<foreach collection="keys" open="(" close=")" item="key" separator=",">
     *		{key}
     *	</foreach>
     */


}
