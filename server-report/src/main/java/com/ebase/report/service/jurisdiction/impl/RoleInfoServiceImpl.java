package com.ebase.report.service.jurisdiction.impl;


import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;
import com.ebase.report.core.ParamType;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.dao.jurisdiction.AcctOperPrivRelaMapper;
import com.ebase.report.dao.jurisdiction.AcctRoleGroupRoleMapper;
import com.ebase.report.dao.jurisdiction.AcctRoleRealMapper;
import com.ebase.report.dao.jurisdiction.RoleInfoMapper;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.AcctRoleGroupRole;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.jurisdiction.RoleInfoService;
import com.ebase.report.vo.jurisdiction.RoleInfoVO;
import com.github.pagehelper.PageHelper;
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
public class RoleInfoServiceImpl implements RoleInfoService {

    private static Logger LOG = LoggerFactory.getLogger(RoleInfoServiceImpl.class);
    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AcctRoleGroupRoleMapper acctRoleGroupRoleMapper;

    @Autowired
    private AcctOperPrivRelaMapper acctOperPrivRelaMapper;

    @Autowired
    private AcctRoleRealMapper acctRoleRealMapper;

    @Override
    public List<RoleInfoVO> roleInfoTree(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        List<String> idall = new ArrayList<>();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        if(StringUtils.isEmpty(roleInfo.getOrgIdAll())){

        }else{
            String[]  orgid=roleInfo.getOrgIdAll().split(",");

            for(int i=0;i<orgid.length;i++){
                idall.add(orgid[i]);
            }
            roleInfo.setOrgIdAlls(idall);
        }

        List<RoleInfo> list = roleInfoMapper.roleGroupTree(roleInfo);
        List<RoleInfo> listRo=new ArrayList<>();
        for(int j=0;j<list.size();j++){
            listRo.add(list.get(j));
            roleInfo.setParentApplicationId(list.get(j).getRoleGroupId());
            roleInfo.setRoleGroupId(list.get(j).getRoleGroupId());
            List<RoleInfo> listRoleInfo =roleInfoMapper.findRoleDetailed(roleInfo);
            listRo.get(j).setChildren(menuChild(roleInfo,listRoleInfo,roleInfo.getRoleGroupTitle()));
        }
        roleInfo.setRoleGroupId(null);
        List<RoleInfo> listRoleInfo = roleInfoMapper.roleInfoTree(roleInfo);

        listRo.addAll(listRoleInfo);

        List<RoleInfoVO> result = BeanCopyUtil.copyList(listRo, RoleInfoVO.class);
        return result;
    }

    public List<RoleInfo> menuChild(RoleInfo roleInfo,List<RoleInfo> listRoleInfo,String roleGroupTitle){
        String parentApplicationName=roleGroupTitle;
        //根据父Id查询数据
        List<RoleInfo> list = roleInfoMapper.roleGroupParentTree(roleInfo);

        for(int i=0;i<list.size();i++){
            List<RoleInfo> listTreeNull=new ArrayList<>();
            list.get(i).setChildren(listTreeNull);
            roleInfo.setParentApplicationId(list.get(i).getRoleGroupId());
            list.get(i).setParentApplicationName(parentApplicationName);
            //查询当前级别的下一级是否有数据
            List<RoleInfo> listTree = roleInfoMapper.roleGroupParentTree(roleInfo);
            RoleInfo role=new RoleInfo();
            role.setRoleGroupId(list.get(i).getRoleGroupId());
            //roleInfo.setOrgIdAlls(list.get(i).getOrgIdAlls());
            List<RoleInfo> ListRole =roleInfoMapper.findRoleDetailed(role);
            if(listTree.size()>0){
                list.get(i).setChildren(menuChild(roleInfo,ListRole,list.get(i).getRoleGroupTitle()));
            }else{
                if(ListRole.size()>0){
                    for(int z=0;z<ListRole.size();z++){
                        listTreeNull.add(ListRole.get(z));
                    }
                }
                list.get(i).setChildren(listTreeNull);
            }
        }
        for(int z=0;z<listRoleInfo.size();z++){
            list.add(listRoleInfo.get(z));
        }
        return list;
    }


    @Override
    public List<RoleInfoVO> orgQuoteRoleInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);

        roleInfo.setIsDelete(IsDelete.NO.getCode());
        roleInfo.setStatus(Status.START.getCode());

        //查询所有数据
        List<RoleInfo> list = roleInfoMapper.orgQuoteRoleInfo(roleInfo);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }

    @Override
    public List<RoleInfoVO> roleInfoAll(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        if(StringUtils.isEmpty(roleInfo.getOrgIdAll())){

        }else{
            String[]  strs=roleInfo.getOrgIdAll().split(",");
            List<String> idall = new ArrayList<>();
            for(int i=0;i<strs.length;i++){
                idall.add(strs[i]);
            }

            roleInfo.setOrgIdAlls(idall);
        }


        roleInfo.setIsDelete(IsDelete.NO.getCode());
        roleInfo.setStatus(Status.START.getCode());

        //查询所有数据
        List<RoleInfo> list = roleInfoMapper.findAll(roleInfo);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }



    @Override
    public List<RoleInfoVO> roleInfoAllLike(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);

         roleInfo.setIsDelete(IsDelete.NO.getCode());
         roleInfo.setStatus(Status.START.getCode());

         //查询所有数据
         List<RoleInfo> list = roleInfoMapper.findAllLike(roleInfo);
         List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
         return result;
    }

    @Override
    public List<RoleInfoVO> roleRoleAcctInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);

        //查询所有数据
        List<RoleInfo> list = roleInfoMapper.roleRoleAcctInfo(roleInfo);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }


    @Override
    public PageInfo<RoleInfoVO> roleInfoList(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        roleInfo.setRoleTitle(roleInfo.getRoleCode());
        roleInfo.setIsDelete(IsDelete.NO.getCode());
        //查询分页数据
        PageHelper.startPage(jsonRequest.getPageNum(), jsonRequest.getPageSize());
        List<RoleInfo> list = roleInfoMapper.find(roleInfo);

        PageInfo<RoleInfo> pageInfo = new PageInfo<>(list);

        List<RoleInfoVO> roleInfoVo= BeanCopyUtil.copyList(list, RoleInfoVO.class);
        PageInfo<RoleInfoVO> pageVo = new PageInfo(roleInfoVo);
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setPages(pageInfo.getPages());
        pageVo.setPageNum(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        return pageVo;
    }


    @Override
    public String verificationDeleteRoelInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        List<RoleInfo> list=roleInfoMapper.verificationDeleteRoelInfo(roleInfo);
        if(CollectionUtils.isEmpty(list)){
            return "是否删除";
        }else{
            return "角色与用户有关联，请先解除关联再删除";
        }
    }


    @Override
    public String verQuoteRoleTitle(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        List<RoleInfo> list=roleInfoMapper.verQuoteRoleTitle(roleInfo);
        if(CollectionUtils.isEmpty(list)){
            return "0";
        }else{
            return "1";
        }
    }

    @Override
    public String verQuoteRoleIds(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        List<RoleInfo> list=roleInfoMapper.verQuoteRoleIds(roleInfo);
        if(CollectionUtils.isEmpty(list)){
            return "0";
        }else{
            return "1";
        }
    }


    /**
     * 复制引用的角色
     * @param jsonRequest
     * @return
     */
    @Override
    public RoleInfoVO saveCopyRole(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=BeanCopyUtil.copy(jsonRequest,RoleInfo.class);
        roleInfo.setIsDelete(IsDelete.NO.getCode());
        roleInfo.setStatus(Status.START.getCode());
        roleInfoMapper.insertSelective(roleInfo);
        acctOperPrivRelaMapper.insertCopy(roleInfo);
        RoleInfoVO roleInfoVO=BeanCopyUtil.copy(roleInfo,RoleInfoVO.class);
        return roleInfoVO;
    }

    @Override
    public RoleInfoVO keepRoleInfo(RoleInfoVO jsonRequest) {
        RoleInfo roleInfo=new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,roleInfo);
        //int i = roleInfoMapper.insertSelective(roleInfo);
        String opt = roleInfo.getOpt();

        if(ParamType.DELETE.getMsg().equals(opt)){
            //删除
            //roleInfoMapper.deleteByPrimaryKey(roleInfo.get(i).getRoleId());
            /*RoleInfo roleInfo1IsDelete=new RoleInfo();
            roleInfo1IsDelete.setRoleId(roleInfo.get(i).getRoleId());
            roleInfo1IsDelete.setIsDelete(IsDelete.YES.getCode());*/
            roleInfo.setIsDelete(IsDelete.YES.getCode());
            RoleInfo roleInfo1=roleInfoMapper.findGroupId(roleInfo);
            if(StringUtils.isEmpty(roleInfo1)){

            }else{
                roleInfo.setRoleGroupId(roleInfo1.getRoleGroupId());
            }
            roleInfoMapper.updateByPrimaryKeySelective(roleInfo);

            //删除角色与用户关联表
            acctRoleRealMapper.deleteByPrimaryKey2(roleInfo.getRoleId());
            acctOperPrivRelaMapper.deleteRoleId(roleInfo.getRoleId());
        }else if(ParamType.UPDATE.getMsg().equals(opt)){
            roleInfo.setUpdatedBy("修改人");
            roleInfo.setUpdatedTime(new Date());
            //修改
            AcctRoleGroupRole acctRoleGroupRole = new AcctRoleGroupRole();
            if(StringUtils.isEmpty(roleInfo.getIsStatus())){
                if(StringUtils.isEmpty(roleInfo.getRoleGroupId())){
                    //删除角色关联的角色组关系
                    acctRoleGroupRoleMapper.deleteRoleId(acctRoleGroupRole.getRoleId());
                }else{
                    acctRoleGroupRole.setRoleId(roleInfo.getRoleId());
                    acctRoleGroupRole.setRoleGroupId(roleInfo.getRoleGroupId());
                    acctRoleGroupRoleMapper.deleteRoleId(acctRoleGroupRole.getRoleId());
                    //添加角色和角色组的关联关系
                    acctRoleGroupRoleMapper.insertSelective(acctRoleGroupRole);
                }
                roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
            }else{
                roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
            }
        }else if(ParamType.INSERT.getMsg().equals(opt)){
            roleInfo.setRoleId(null);

            roleInfo.setIsDelete(IsDelete.NO.getCode());
            roleInfo.setCreatedBy("创建人");
            roleInfo.setCreatedTime(new Date());
            roleInfoMapper.insertSelective(roleInfo);
            //添加
            if(StringUtils.isEmpty(roleInfo.getRoleGroupId())){

            }else{
                //添加角色和角色组的关联关系
                AcctRoleGroupRole acctRoleGroupRole = new AcctRoleGroupRole();
                acctRoleGroupRole.setRoleId(roleInfo.getRoleId());
                acctRoleGroupRole.setRoleGroupId(roleInfo.getRoleGroupId());
                acctRoleGroupRoleMapper.insertSelective(acctRoleGroupRole);
            }
//            }else{
//                jsonResponse.setRetCode("0000002");
//                jsonResponse.setRetDesc("角色名称重复!");
//            }
        }
        roleInfo.setOrRole("角色");
        RoleInfoVO roleInfoVO=BeanCopyUtil.copy(roleInfo, RoleInfoVO.class);
        return roleInfoVO;
    }

    @Override
    public List<RoleInfoVO> verificationIsTtitleRole(RoleInfoVO jsonRequest) {

        RoleInfo reqBody = new RoleInfo();
        BeanCopyUtil.copy(jsonRequest,reqBody);
        List<RoleInfo> list=roleInfoMapper.verificationIsTtitle(reqBody);
        List<RoleInfoVO> result = BeanCopyUtil.copyList(list, RoleInfoVO.class);
        return result;
    }


    @Override
    public PageDTO<RoleInfo> queryForList(RoleInfo roleInfo) {
        String reAcctId = AssertContext.getReAcctId();

        List<String> reRoleId = AssertContext.getReRoleId();

//        roleInfo.setReRoleIds(AssertContext.getReRoleId());  //暂时注调，能给自己的所属角色分享
        /*List<RoleInfo> roleInfos = roleInfoMapper.queryForList(vo);
        List<RoleInfoVO> rptPersonalTableFieldVOs = BeanCopyUtil.copyList(roleInfos, RoleInfoVO.class);
        return rptPersonalTableFieldVOs;*/
        roleInfo.setAcctShare(reAcctId);
        PageDTO<RoleInfo> pageDTO = new PageDTO<>(roleInfo.getPageNum(),roleInfo.getPageSize());
        if(roleInfo.getType() != null && roleInfo.getType() == (byte)1){ //是否排除自己
            roleInfo.setReRoleIds(AssertContext.getReRoleId());
        }

        Integer count = roleInfoMapper.queryForCount(roleInfo);;

        pageDTO.setTotal(count);

        roleInfo.setStartRow(pageDTO.getStartRow());
        List<RoleInfo> list = roleInfoMapper.queryForList(roleInfo);
        for(int i=0;i<list.size();i++){
            list.get(i).setReRoleId(String.valueOf(list.get(i).getRoleId()));
        }
        pageDTO.setResultData(list);
        return pageDTO;
    }

    @Override
    public RoleInfo getRoleById(String x) {
        return roleInfoMapper.selectByAcctName(x);
    }

}
