package com.ebase.report.service.impl;

import com.ebase.report.common.SubjectTypeEnum;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.dao.*;
import com.ebase.report.model.RptDataAuth;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.service.RptDataAuthService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


//数据授权
@Service
public class RptDataAuthServiceImpl implements RptDataAuthService {

    private static Logger LOG = LoggerFactory.getLogger(RptDataAuthServiceImpl.class);

    //数据源
    @Autowired
    private RptDatasourceMapper rptDatasourceMapper;

    //数据表
    @Autowired
    private RptDataTableMapper rptDataTableMapper;

    //主题表
    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper;

    //指标
    @Autowired
    private RptMeasuresMapper rptMeasuresMapper;

    //报表id
    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper;

    @Override
    public List<RptDatasource> dataAuthTree(AcctInfo acctInfo) {

        //要查询的用户id
        Long acctId = acctInfo.getAcctId();

        //要分配 的角色ID
        Long roleId = acctInfo.getRoleId();

        Map<String,Object> tmp = new HashMap<>();
        tmp.put("acctId",acctId);
        tmp.put("roleId",roleId);

        //获得所勾选的所有的主题表
        List<RptPersonalSubject> rptPersonalSubjects = rptPersonalSubjectMapper.selectByCreatedBy(tmp);
        //放所有存在的table id
        Map<Long,Integer> tmpMap = new HashMap<>();
        rptPersonalSubjects.forEach(x -> {
            tmpMap.put(x.getTableId(),1);
        });

        List<RptDatasource> list = rptDatasourceMapper.selectAll();
        list.forEach(x -> {
            Long datasourceId = x.getDatasourceId();
            List<RptDataTable> dataTables = rptDataTableMapper.selectByDatasourceId(datasourceId);

            //根据当前登陆角色 和 登陆人 打勾
            dataTables.forEach(y -> {
                y.setDatasourceName(y.getTableName());

                Long tableId = y.getTableId();
                if(tmpMap.get(tableId) != null){
                    y.setIsChecked((byte)1);
                }
            });
            x.setRptDataTables(dataTables);
        });

        //赋值给RptDataTable中的datasourceChineseName字段,仅仅是为了便于前端方便渲染
        for (RptDatasource rptDs:list) {
            List<RptDataTable> rptTbs = rptDs.getRptDataTables();
            for(RptDataTable  rptTb : rptTbs){
                rptTb.setDatasourceChineseName(rptTb.getTableName());
                rptTb.setDatasourceName(rptTb.getTableCode());
            }
        }
        return list;
    }

    @Override
    public Boolean keepDataAuth(RptDataAuth rptDataAuth) {
        Byte type = rptDataAuth.getType();

        Long id = rptDataAuth.getId();
        String acctName = AssertContext.getAcctName();
        Long acctId1 = AssertContext.getAcctId();
        String acctId = "1";
        if(acctId1 != null){
            acctId = acctId1.toString();
        }

        List<RptPersonalSubject> rptPersonalSubjects = new ArrayList<>();

        Date date = new Date();
        // 0  角色   1  账号
        if(type == (byte)0){

            //先删除 这个角色下的所有数据 查询这个主题下所有数据

            //所有的主题数据 清空所有指标 和 报表数据
            List<Long> longs = rptPersonalSubjectMapper.selectIdByRoleId(id);
            if(CollectionUtils.isNotEmpty(longs)){
                rptMeasuresMapper.deleteBySubjectId(longs);
                rptPersonalAnalysisMapper.deleteBySubjectId(longs);
            }


            rptPersonalSubjectMapper.deleteByRoleId(id);
            //

            for(RptDataTable x:rptDataAuth.getTableIds()) {
                RptPersonalSubject rptPersonalSubject = new RptPersonalSubject();
                rptPersonalSubject.setSubjectType(SubjectTypeEnum.DATATABLE.getCode());
                rptPersonalSubject.setSubjectSource(SubjectTypeEnum.DATATABLE.getCode());
                rptPersonalSubject.setTableId(x.getTableId());
                rptPersonalSubject.setSubjectName(x.getTableName()); //表的名
                rptPersonalSubject.setRemoveStatus((byte) 0);
                rptPersonalSubject.setCreatedBy(acctId);
                rptPersonalSubject.setCreatedDt(date);
                rptPersonalSubject.setRoleId(id);
                rptPersonalSubjects.add(rptPersonalSubject);
            }
        }else{
            //先删除 这个账户下的所有数据



            //先删除 这个角色下的所有数据 查询这个主题下所有数据

            //所有的主题数据 清空所有指标 和 报表数据
            List<Long> longs = rptPersonalSubjectMapper.selectIdByUserId(id);
            if(CollectionUtils.isNotEmpty(longs)){
                rptMeasuresMapper.deleteBySubjectId(longs);
                rptPersonalAnalysisMapper.deleteBySubjectId(longs);
            }

            rptPersonalSubjectMapper.deleteByUserId(id);

            for(RptDataTable x:rptDataAuth.getTableIds()){
                RptPersonalSubject rptPersonalSubject = new RptPersonalSubject();
                rptPersonalSubject.setSubjectType(SubjectTypeEnum.DATATABLE.getCode());
                rptPersonalSubject.setSubjectSource(SubjectTypeEnum.DATATABLE.getCode());
                rptPersonalSubject.setTableId(x.getTableId());
                rptPersonalSubject.setSubjectName(x.getTableName());
                rptPersonalSubject.setRemoveStatus((byte)0);
                rptPersonalSubject.setCreatedBy(acctId);
                rptPersonalSubject.setCreatedDt(date);
                rptPersonalSubject.setUserId(id.toString());
                rptPersonalSubjects.add(rptPersonalSubject);
            };
        }
        if(CollectionUtils.isNotEmpty(rptPersonalSubjects)){
            //批量插入
            rptPersonalSubjectMapper.insertSelectiveList(rptPersonalSubjects);
        }
        return true;
    }
}
