package com.ebase.report.service.impl;

import com.ebase.report.common.MeasureTypeEnum;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.dao.RptDataFieldMapper;
import com.ebase.report.dao.RptPersonalAnalysisMapper;
import com.ebase.report.dao.jurisdiction.AcctInfoMapper;
import com.ebase.report.dao.jurisdiction.RoleInfoMapper;
import com.ebase.report.model.AnalysisShareBody;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptMeasures;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.dynamic.ReportDynamicParam;
import com.ebase.report.model.dynamic.ReportEchoBody;
import com.ebase.report.model.dynamic.ReportShareBody;
import com.ebase.report.model.dynamic.ReportTable;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.ReportService;
import com.ebase.report.service.RptPersonalAnalysisService;
import com.ebase.report.vo.RptPersonalAnalysisVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RptPersonalAnalysisServiceImpl implements RptPersonalAnalysisService {

    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper;

    //字段表
    @Autowired
    private RptDataFieldMapper rptDataFieldMapper;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AcctInfoMapper acctInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public List<ReportEchoBody> getPersonalSubject(Long personalAnalysisId) {
        //报表对象
        RptPersonalAnalysis rptPersonalAnalysis =  rptPersonalAnalysisMapper.selectByPrimaryKey(personalAnalysisId);

        //主题id
        Long personalSubjectId = rptPersonalAnalysis.getPersonalSubjectId();

        //获得主题数据
//        ReportEchoBody reportEchoBody = reportService.getReportEchoBody(personalSubjectId);

        String configJson = rptPersonalAnalysis.getConfigJson();

        ReportEchoBody reportEchoBody = JsonUtil.fromJson(configJson, ReportEchoBody.class);

//        reportEchoBody.setReportDynamicParam(reportDynamicParam);
        //生成 系统生的代码
//        List<ReportTable> reportTables = reportEchoBody.getReportTables(); //数据表
//
//        List<RptDataField> rptDataIndexs = rptDataFieldMapper.selectMeasuresByTable(reportTables);
//
//        Long acctId = 1L;
//        List<RptMeasures> list = new ArrayList<>(rptDataIndexs.size());
//        //初始化一些指标
//        int i = 0;
//        for(RptDataField x:rptDataIndexs){
//            RptMeasures rptMeasures1 = new RptMeasures();
//            rptMeasures1.setMeasureId(new Date().getTime() + acctId + i ++);
//            String measuresName = x.getFieldName() + MeasureTypeEnum.sum.getName();
//            rptMeasures1.setMeasuresName(measuresName);
//            rptMeasures1.setFieldId(x.getFieldId());
//            rptMeasures1.setFieldCode(x.getFieldCode());
//            rptMeasures1.setFieldName(x.getFieldName());
//            rptMeasures1.setMeasureType(MeasureTypeEnum.sum.getCode());
//            rptMeasures1.setSubjectId(personalSubjectId);
//            rptMeasures1.setIsSystem((byte)1);
//            list.add(rptMeasures1);
//        };
//
//
//        reportEchoBody.getRptMeasures().addAll(list)


        List<ReportEchoBody> objects = new ArrayList<>();
        objects.add(reportEchoBody);
        return objects;
    }



    @Override
    public PageDTO<RptPersonalAnalysisVO> findSelective(RptPersonalAnalysisVO record){
        RptPersonalAnalysis model = BeanCopyUtil.copy(record, RptPersonalAnalysis.class);
//        PageHelper.startPage(record.getPageNum(), record.getPageSize());


        PageDTO<RptPersonalAnalysisVO> pageDto = new PageDTO<>(record.getPageNum(),record.getPageSize());

        Integer count = rptPersonalAnalysisMapper.selectCount(model);

        pageDto.setTotal(count);

        model.setStartRow(pageDto.getStartRow());
        List<RptPersonalAnalysis> rptPersonalAnalysiss = rptPersonalAnalysisMapper.select(model);
        List<RptPersonalAnalysisVO> rptPersonalAnalysisVOs = BeanCopyUtil.copyList(rptPersonalAnalysiss, RptPersonalAnalysisVO.class);

        pageDto.setResultData(rptPersonalAnalysisVOs);
        return pageDto;
    }

    @Override
    public RptPersonalAnalysisVO getByPrimaryKey(Long key){
        RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(rptPersonalAnalysis, RptPersonalAnalysisVO.class);
    }

    @Override
    public Integer insert(RptPersonalAnalysisVO record){
        RptPersonalAnalysis rptPersonalAnalysis = BeanCopyUtil.copy(record, RptPersonalAnalysis.class);
        return rptPersonalAnalysisMapper.insert(rptPersonalAnalysis);

    }

    @Override
    public Integer insertSelective(RptPersonalAnalysisVO record){
        RptPersonalAnalysis rptPersonalAnalysis = BeanCopyUtil.copy(record, RptPersonalAnalysis.class);
        return rptPersonalAnalysisMapper.insertSelective(rptPersonalAnalysis);
    }

    @Override
    public Integer updateByPrimaryKey(RptPersonalAnalysisVO record){
        RptPersonalAnalysis rptPersonalAnalysis = BeanCopyUtil.copy(record, RptPersonalAnalysis.class);
        return rptPersonalAnalysisMapper.updateByPrimaryKey(rptPersonalAnalysis);
    }

    @Override
    public Integer updateByPrimaryKeySelective(RptPersonalAnalysisVO record){
        RptPersonalAnalysis rptPersonalAnalysis = BeanCopyUtil.copy(record, RptPersonalAnalysis.class);
        return rptPersonalAnalysisMapper.updateByPrimaryKeySelective(rptPersonalAnalysis);
    }

    @Override
    public Integer deleteByPrimaryKey(RptPersonalAnalysisVO vo){
        Long personalAnalysisId = vo.getPersonalAnalysisId();
        Byte deleteType = vo.getDeleteType();
        if(deleteType != null && deleteType == 0){
            rptPersonalAnalysisMapper.deleteByPrimaryKey(personalAnalysisId);
        }
        rptPersonalAnalysisMapper.deleteByAnalysisSourceId(personalAnalysisId);
        return 1;
    }

    @Override
    public List<RptPersonalAnalysisVO> getByUserId(String userId) {
       List<RptPersonalAnalysis> list = rptPersonalAnalysisMapper.getByUserId(userId);
        List<RptPersonalAnalysisVO> vos = BeanCopyUtil.copyList(list, RptPersonalAnalysisVO.class);
        return vos;
    }

    @Override
    public PageDTO<ReportTable> listReportForm(ReportTable reqBody) {

        //当前登陆人 和组织下所有的 主题
        String acctId = AssertContext.getReAcctId(); //当前登陆人
        if(acctId == null){
            acctId = "1";
        }

        List<String> roleIds = AssertContext.getReRoleId(); //角色id
//        if(roleId == null){
//            roleId = 1L;
//        }

        Map<String,Object> tmp = new HashMap<>();
        tmp.put("acctId",acctId);
        tmp.put("roleId",roleIds);
        String tableName = reqBody.getTableName();
        if(tableName != null){
            tmp.put("term",reqBody.getTableName().trim());
        }else {
            tmp.put("term",reqBody.getTableName());
        }

        tmp.put("datasourceName",reqBody.getDatasourceName());
        Integer count = rptPersonalAnalysisMapper.listReportFormCount(tmp);

        PageDTO<ReportTable> pageDTO = new PageDTO<>(reqBody.getPageNum(),reqBody.getPageSize());

        //所有物料count
        pageDTO.setTotal(count);
        tmp.put("startRow",pageDTO.getStartRow());
//        enquirySupplierListVO.setStartRow(pageDTO.getStartRow());
        tmp.put("pageSize",reqBody.getPageSize());
        List<ReportTable> reportTables = rptPersonalAnalysisMapper.listReportForm(tmp);
        pageDTO.setResultData(reportTables);

        return pageDTO;
    }

    @Override
    public Boolean addShareReport(ReportShareBody reportShareBody) {

        Long personalAnalysisId = reportShareBody.getPersonalAnalysisId();
        String acctId = AssertContext.getReAcctId();  //当前登陆人id
        if(acctId == null){
            acctId = "1";
        }
        //要复制的数据
        String acctName = AssertContext.getAcctName();

        RptPersonalAnalysis rptPersonalAnalysis1 = rptPersonalAnalysisMapper.selectByPrimaryKey(personalAnalysisId);
        Byte type = reportShareBody.getType();


        if((byte)0 == type){

            //根据分享人和报表id所有角色类型的 数据
                //角色
            for(String x:reportShareBody.getList()){

                RptPersonalAnalysis copy = BeanCopyUtil.copy(rptPersonalAnalysis1, RptPersonalAnalysis.class);
                copy.setUserId(null);
                copy.setRoleId(x);
                copy.setSharingPersonId(acctId);
                copy.setSharingPersonName(acctName);
                copy.setAnalysisSourceId(personalAnalysisId);
                rptPersonalAnalysisMapper.insertSelective(copy);
            };
        }else{
            for(String x:reportShareBody.getList()){
                RptPersonalAnalysis copy = BeanCopyUtil.copy(rptPersonalAnalysis1, RptPersonalAnalysis.class);
                copy.setUserId(x);
                copy.setRoleId(null);
                copy.setSharingPersonId(acctId);
                copy.setSharingPersonName(acctName);
                copy.setAnalysisSourceId(personalAnalysisId);
                rptPersonalAnalysisMapper.insertSelective(copy);
            };

        }
        return true;
    }

    @Override
    public Integer getReportByName(String reportName,String acctId) {

        return rptPersonalAnalysisMapper.selectByName(reportName,acctId);
    }

    @Override
    public PageDTO<AnalysisShareBody> listAnalysisShareBody(AnalysisShareBody analysisShareBody) {
        PageDTO<AnalysisShareBody> pageDTO = new PageDTO<>(analysisShareBody.getPageNum(),analysisShareBody.getPageSize());

        Integer count = rptPersonalAnalysisMapper.listAnalysisShareBodyCount(analysisShareBody);
        pageDTO.setTotal(count);
        analysisShareBody.setStartRow(pageDTO.getStartRow());

        List<RptPersonalAnalysis> list = rptPersonalAnalysisMapper.listAnalysisShareBody(analysisShareBody);

        List<AnalysisShareBody> resultList = new ArrayList<>(list.size());
        for(RptPersonalAnalysis analysis:list){
            String userId = analysis.getUserId();

            AnalysisShareBody analysisShareBody1 = new AnalysisShareBody();
            if(userId != null){
                analysisShareBody1.setType((byte)1);
//                AcctInfo acctInfo = acctInfoMapper.selectByPrimaryKey(Long.parseLong(userId));
                AcctInfo acctInfo = acctInfoMapper.selectByLogin(userId);
                if(acctInfo == null){
                    analysisShareBody1.setName(userId);
                }else{
                    analysisShareBody1.setName(acctInfo.getName());
                }


            }else{
                analysisShareBody1.setType((byte)0);

                String roleName = analysis.getRoleId();

                RoleInfo roleInfo = roleInfoMapper.selectByAcctCode(roleName);
                if(roleInfo == null){
                    analysisShareBody1.setName(roleName);
                }else{
                    analysisShareBody1.setName(roleInfo.getRoleTitle());
                }
            }
            analysisShareBody1.setId(analysisShareBody.getId());
            resultList.add(analysisShareBody1);
        }


        pageDTO.setResultData(resultList);
        return pageDTO;
    }

    @Override
    public RptPersonalAnalysis getByUserId(String userId, Long sysId) {
        RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisMapper.selectByUserAndId(userId,sysId);
        return rptPersonalAnalysis;
    }

    @Override
    public RptPersonalAnalysis getByRoleId(String roleId, Long sysId) {
        RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisMapper.selectByRoleAndId(roleId,sysId);
        return rptPersonalAnalysis;
    }


}
