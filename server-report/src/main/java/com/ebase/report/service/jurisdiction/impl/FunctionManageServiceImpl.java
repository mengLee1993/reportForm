package com.ebase.report.service.jurisdiction.impl;


import com.ebase.report.common.IsDelete;
import com.ebase.report.core.MathHelper;
import com.ebase.report.core.ParamType;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.jurisdiction.*;
import com.ebase.report.model.jurisdiction.AcctFunctionSys;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.FunctionManage;
import com.ebase.report.service.jurisdiction.FunctionManageService;
import com.ebase.report.vo.jurisdiction.FunctionManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhaoyuhang
 */
@Service
public class FunctionManageServiceImpl implements FunctionManageService {

    private static Logger LOG = LoggerFactory.getLogger(FunctionManageServiceImpl.class);

    @Autowired
    private FunctionManageMapper functionManageMapper;

    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Autowired
    private AcctOperPrivRelaMapper acctOperPrivRelaMapper;

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private AcctFunctionSysMapper acctFunctionSysMapper;


    /**
     * 管理员查询树状图
     * @param functionManageVO
     * @return
     */
    @Override
    public List<FunctionManageVO> functionManageList(FunctionManageVO functionManageVO) {

        FunctionManage functionManage=new FunctionManage();
        BeanCopyUtil.copy(functionManageVO,functionManage);
        //查询一级资源树状图
        List<FunctionManage> list = functionManageMapper.find(functionManage);

        List<FunctionManage> listTreeNull=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            list.get(i).setChildren(listTreeNull);
            if (list.get(i).getFunctionId() != null) {
                functionManage.setParentApplicationId(list.get(i).getFunctionId());
                //添加数据
                list.get(i).setChildren(menuChild(functionManage, list.get(i).getFunctionTitle()));
            }
        }

        List<FunctionManageVO> result = BeanCopyUtil.copyList(list, FunctionManageVO.class);
        return result;
    }

    //获取当前用户下的所有组织
    public FunctionManage getFunctionOrgId(FunctionManage functionManage) {
        if (StringUtils.isEmpty(functionManage.getOrgIdAll())) {

        } else {
            String[] orgid = functionManage.getOrgIdAll().split(",");
            List<String> idall = new ArrayList<>();
            for (int i = 0; i < orgid.length; i++) {
                idall.add(orgid[i]);
            }
            functionManage.setOrgIdAlls(idall);
        }
        return functionManage;
    }

    public List<FunctionManage> menuChild(FunctionManage functionManage,String functionTitle){
        String parentApplicationName=functionTitle;
        List<FunctionManage> listTreeNull=new ArrayList<>();
        //根据父Id查询数据
        List<FunctionManage> list = functionManageMapper.findThree(functionManage);
        for(int i=0;i<list.size();i++){
            list.get(i).setChildren(listTreeNull);
            functionManage.setParentApplicationId(list.get(i).getFunctionId());
            list.get(i).setParentApplicationName(parentApplicationName);
            //查询当前级别的下一级是否有数据
            List<FunctionManage> listTree = functionManageMapper.findThree(functionManage);
            if(listTree.size()>0){
                list.get(i).setChildren(menuChild(functionManage,list.get(i).getFunctionTitle()));
            }else{
                list.get(i).setChildren(listTreeNull);
            }
        }
        return list;
    }


    /**
     * 查询角色关联功能树状图
     * @param functionManageVO
     * @return
     */
    @Override
    public List<FunctionManageVO> functionManageRoleList(FunctionManageVO functionManageVO) {

        FunctionManage functionManage=new FunctionManage();
        BeanCopyUtil.copy(functionManageVO,functionManage);
        //获取当前用户下的所有组织
        functionManage=getFunctionOrgId(functionManage);
        Integer relaStatus=0;
        if(functionManageVO.getType()==1){
            relaStatus=1;
        }

        List<FunctionManage> list = functionManageMapper.findRole(functionManage);
        List<FunctionManage> listTreeNull=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            list.get(i).setRelaStatus(relaStatus);
            list.get(i).setChildren(listTreeNull);
            if(list.get(i).getFunctionId()!=null){
                functionManage.setParentApplicationId(list.get(i).getFunctionId());
                list.get(i).setChildren(menuChildRole(relaStatus,functionManage,list.get(i).getFunctionTitle()));
            }
        }
        List<FunctionManageVO> result = BeanCopyUtil.copyList(list, FunctionManageVO.class);

        return result;
    }

    public List<FunctionManage> menuChildRole(Integer relaStatus, FunctionManage functionManage, String functionTitle){
        String parentApplicationName=functionTitle;
        List<FunctionManage> listTreeNull=new ArrayList<>();
        //根据父Id查询数据
        List<FunctionManage> list = functionManageMapper.findRoleThree(functionManage);

        if(list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setRelaStatus(relaStatus);
                list.get(i).setChildren(listTreeNull);
                functionManage.setParentApplicationId(list.get(i).getFunctionId());
                list.get(i).setParentApplicationName(parentApplicationName);
                //查询当前级别的下一级是否有数据
                List<FunctionManage> listTree = functionManageMapper.findRoleThree(functionManage);
                if (listTree.size() > 0) {
                    list.get(i).setChildren(menuChildRole(relaStatus,functionManage, list.get(i).getFunctionTitle()));
                } else {
                    list.get(i).setChildren(listTreeNull);
                }
            }
        }
        return list;
    }



    @Override
    public Integer updateFunctionManageStatus(List<FunctionManageVO> jsonRequest) {
        Integer num=null;
        List<FunctionManage> reqBody = BeanCopyUtil.copyList(jsonRequest, FunctionManage.class);
        for(int i=0;i<reqBody.size();i++){
            num=functionManageMapper.updateByPrimaryKeySelective(reqBody.get(i));
        }
        return num;
    }

    @Override
    public List<FunctionManageVO> ListFunctionCode(FunctionManageVO jsonRequest) {
        FunctionManage reqBody = new FunctionManage();
        BeanCopyUtil.copy(jsonRequest,reqBody);
        AcctInfo acctInfo=new AcctInfo();
        acctInfo.setAcctId(reqBody.getAcctId());
        List<FunctionManage> list=functionManageMapper.selsctUserFunctionAll(acctInfo);
        List<FunctionManageVO> result = BeanCopyUtil.copyList(list, FunctionManageVO.class);
        return result;
    }

    @Override
    public String verificationDeleteFunction(FunctionManageVO jsonRequest) {
        String body="";
        FunctionManage reqBody = new FunctionManage();
        BeanCopyUtil.copy(jsonRequest,reqBody);
        List<FunctionManage> list=functionManageMapper.verificationDeleteFunction(reqBody);
        if(CollectionUtils.isEmpty(list)){
            body="是否删除";
        }else{
            body="功能与角色有关联，是否删除";
        }
        return body;
    }

    @Override
    public FunctionManageVO keepFunctionManage(FunctionManageVO jsonRequest) {
        FunctionManage reqBody = new FunctionManage();
        BeanCopyUtil.copy(jsonRequest,reqBody);

        String opt=reqBody.getOpt();

        if(ParamType.DELETE.getMsg().equals(opt)){

            FunctionManage functionManage=new FunctionManage();
            functionManage.setFunctionId(reqBody.getFunctionId());
            functionManage.setParentApplicationId(reqBody.getFunctionId());
            functionManage.setChildren(menuChild(functionManage,""));
            FunctionManage parId=functionManageMapper.findParentApplicationId(reqBody);
            reqBody.setParentApplicationId(parId.getParentApplicationId());
            if(CollectionUtils.isEmpty(functionManage.getChildren())){
                //删除角色和功能关联
                acctOperPrivRelaMapper.deleteFunctionId(reqBody.getFunctionId());
                //删除
                reqBody.setIsDelete(IsDelete.YES.getCode());

                functionManageMapper.updateByPrimaryKeySelective(reqBody);
            }else{
                List<String> functionIds=new ArrayList<>();
                functionIds.add(reqBody.getFunctionId().toString());
                functionIds=getParent(functionIds,functionManage.getChildren());
                functionManage.setAllIds(functionIds);
                acctOperPrivRelaMapper.deleteFunctionIdAll(functionManage);
                functionManageMapper.updateFunctionIdAll(functionManage);
            }
        }else if(ParamType.UPDATE.getMsg().equals(opt)){
            reqBody.setUpdatedTime(new Date());
            //修改
            FunctionManage parId=functionManageMapper.findParentApplicationId(reqBody);
            functionManageMapper.updateByPrimaryKeySelective(reqBody);

            reqBody.setParentApplicationId(parId.getParentApplicationId());
        }else if(ParamType.INSERT.getMsg().equals(opt)){
            reqBody.setCreatedTime(new Date());
            reqBody.setFunctionId(null);
            String idFullPath = "";
            if (StringUtils.isEmpty(reqBody.getParentApplicationId())) {
                reqBody.setTitleFullPath(reqBody.getFunctionTitle());
                reqBody.setFunctionCode(getFunctionCode(reqBody.getParentApplicationId()));
            } else {
                reqBody.setFunctionCode(getFunctionCode(reqBody.getParentApplicationId()));
                FunctionManage functionManageOne = functionManageMapper.selectByPrimaryKey(reqBody.getParentApplicationId());
                reqBody.setTitleFullPath(functionManageOne.getTitleFullPath() + "," + reqBody.getFunctionTitle());
                idFullPath = functionManageOne.getIdFullPath() + ",";
            }
            reqBody.setIsDelete(IsDelete.NO.getCode());
            //添加
            int a = functionManageMapper.insertSelective(reqBody);
            reqBody.setCreatedTime(null);
            FunctionManage functionManageIdFull = new FunctionManage();
            functionManageIdFull.setFunctionId(reqBody.getFunctionId());
            functionManageIdFull.setIdFullPath(idFullPath + reqBody.getFunctionId());
            functionManageMapper.updateByPrimaryKeySelective(functionManageIdFull);
            AcctFunctionSys acctFunctionSys=new AcctFunctionSys();
            acctFunctionSys.setCreatedTime(new Date());
            acctFunctionSys.setFunctionId(reqBody.getFunctionId());
            acctFunctionSys.setSysId(reqBody.getSysId());
            acctFunctionSys.setStatus(Byte.parseByte("0"));
            Integer num=acctFunctionSysMapper.insertSelective(acctFunctionSys);
        }
        FunctionManageVO functionManageVO1=BeanCopyUtil.copy(reqBody,FunctionManageVO.class);
        return functionManageVO1;
    }

    public String getFunctionCode(Long parentApplicationId) {
        String functionCode = "";
        StringBuilder result = new StringBuilder();

        if (!StringUtils.isEmpty(parentApplicationId)) {
            FunctionManage f = functionManageMapper.selectByPrimaryKey(parentApplicationId);
            result.append(f.getFunctionCode());
        }
        FunctionManage functionEntityCode=functionManageMapper.maxCode(parentApplicationId);
        if (StringUtils.isEmpty(functionEntityCode))
            functionCode = result.append("101").toString();
        else
            functionCode = MathHelper.add(functionEntityCode.getFunctionCode());
        return functionCode;
    }

    @Override
    public List<FunctionManageVO> verificationFunIsTtitle(FunctionManageVO jsonRequest) {
        FunctionManage reqBody = new FunctionManage();
        BeanCopyUtil.copy(jsonRequest,reqBody);
        List<FunctionManage> list=functionManageMapper.verificationFunIsTtitle(reqBody);
        List<FunctionManageVO> result = BeanCopyUtil.copyList(list, FunctionManageVO.class);
        return result;
    }


    private List<String> getParent(List<String> functionIds,List<FunctionManage> functionManageList) {
        //根据父Id查询数据
        for(int i=0;i<functionManageList.size();i++){
            functionIds.add(functionManageList.get(i).getFunctionId().toString());
            //查询当前级别的下一级是否有数据
            if(functionManageList.get(i).getChildren().size()>0){
                getParent(functionIds,functionManageList.get(i).getChildren());
            }
        }
        return functionIds;
    }
}
