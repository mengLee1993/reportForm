package com.ebase.report.service.jurisdiction.impl;


import com.ebase.report.core.ParamType;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.vo.jurisdiction.RoleGroupVO;
import com.ebase.report.common.IsDelete;
import com.ebase.report.common.Status;
import com.ebase.report.dao.jurisdiction.*;
import com.ebase.report.model.jurisdiction.RoleGroup;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.jurisdiction.RoleGroupService;
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
public class RoleGroupServiceImpl implements RoleGroupService {

    private static Logger LOG = LoggerFactory.getLogger(RoleGroupServiceImpl.class);

    @Autowired
    private RoleGroupMapper roleGroupMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AcctRoleGroupRoleMapper acctRoleGroupRoleMapper;

    @Autowired
    private AcctOperPrivRelaMapper acctOperPrivRelaMapper;

    @Autowired
    private AcctRoleRealMapper acctRoleRealMapper;



    @Override
    public List<RoleGroupVO> findAll(RoleGroupVO jsonRequest) {
        RoleGroup roleGroup=new RoleGroup();
        BeanCopyUtil.copy(jsonRequest,roleGroup);

        roleGroup.setStatus(Status.START.getCode());
        roleGroup.setIsDelete(IsDelete.NO.getCode());
        List<String> idall = new ArrayList<>();
        if(StringUtils.isEmpty(roleGroup.getOrgIdAll())){
        }else{
            String[]  orgid=roleGroup.getOrgIdAll().split(",");
            for(int i=0;i<orgid.length;i++){
                idall.add(orgid[i]);
            }
            roleGroup.setOrgIdAlls(idall);
        }
        List<RoleGroup> list = roleGroupMapper.find(roleGroup);

        List<RoleGroupVO> result = BeanCopyUtil.copyList(list, RoleGroupVO.class);
        return result;
    }


    @Override
    public String verificationDeleteRoleGroup(RoleGroupVO jsonRequest) {
        RoleGroup roleGroup=new RoleGroup();
        BeanCopyUtil.copy(jsonRequest,roleGroup);
//        String idall="";
//        RoleGroup group=new RoleGroup();
//        group.setParentApplicationId(roleGroup.getRoleGroupId());
//
//        List<RoleGroup> roleGroups=roleGroupMapper.findARoleGroupTree(group);
//
//        if(CollectionUtils.isEmpty(roleGroups)){
//            idall=roleGroup.getRoleGroupId().toString();
//        }else{
//            idall=roleGroup.getRoleGroupId().toString();
//            for(int i=0;i<roleGroups.size();i++){
//                if(StringUtils.isEmpty(idall)){
//                    idall=roleGroups.get(i).getRoleGroupId().toString();
//                }else{
//                    idall=idall+","+roleGroups.get(i).getRoleGroupId().toString();
//                }
//                roleGroups.get(i).setParentApplicationId(roleGroups.get(i).getRoleGroupId());
//                idall=menuChildRole(roleGroups.get(i),idall);
//            }
//        }
//        String[]  orgid=idall.split(",");
//        List<String> idallst = new ArrayList<>();
//        for(int o=0;o<orgid.length;o++){
//            idallst.add(orgid[o]);
//        }
//        group.setOrgIdAlls(idallst);
//
//        List<RoleGroup> acctId=roleGroupMapper.findAcctId(group);
        List<RoleGroup> acctId=roleGroupMapper.verificationDeleteRoleGroup(roleGroup);
        if(CollectionUtils.isEmpty(acctId)) {
            return "是否删除";
        }else{
            return "角色组与角色有关联，请先解除关联再删除";
        }
    }

    @Override
    public PageInfo<RoleGroupVO> roleGroupList(RoleGroupVO jsonRequest) {
        RoleGroup roleGroup=new RoleGroup();
        BeanCopyUtil.copy(jsonRequest,roleGroup);
        try {

            //roleInfo.setRoleTitle(roleInfo.getRoleCode());
            roleGroup.setIsDelete(IsDelete.NO.getCode());
            List<String> idall = new ArrayList<>();
            if(StringUtils.isEmpty(roleGroup.getOrgIdAll())){

            }else{
                String[]  orgid=roleGroup.getOrgIdAll().split(",");

                for(int i=0;i<orgid.length;i++){
                    idall.add(orgid[i]);
                }
                roleGroup.setOrgIdAlls(idall);
            }
            //查询分页数据
            List<RoleGroup> list = roleGroupMapper.find(roleGroup);

            PageInfo<RoleGroup> pageInfo = new PageInfo<>(list);

            List<RoleGroupVO> roleInfoVo= BeanCopyUtil.copyList(list, RoleGroupVO.class);
            PageInfo<RoleGroupVO> pageVo = new PageInfo(roleInfoVo);
            pageVo.setTotal(pageInfo.getTotal());
            pageVo.setPages(pageInfo.getPages());
            pageVo.setPageNum(pageInfo.getPageNum());
            pageVo.setPageSize(pageInfo.getPageSize());
            return pageVo;
        } finally {
        }
    }

    @Override
    public RoleGroupVO keepRoleGroup(RoleGroupVO jsonRequest) {
        RoleGroup roleGroup=new RoleGroup();
        BeanCopyUtil.copy(jsonRequest,roleGroup);

        String opt=roleGroup.getOpt();
        if(ParamType.DELETE.getMsg().equals(opt)){

            String idall="";
            RoleGroup group=new RoleGroup();
            group.setParentApplicationId(roleGroup.getRoleGroupId());

            //查询父级ID
            RoleGroup group1=roleGroupMapper.selectByPrimaryKey(roleGroup.getRoleGroupId());

            List<RoleGroup> roleGroups=roleGroupMapper.findARoleGroupTree(group);
            if(CollectionUtils.isEmpty(roleGroups)){
                idall=roleGroup.getRoleGroupId().toString();
            }else{
                idall=roleGroup.getRoleGroupId().toString();
                for(int i=0;i<roleGroups.size();i++){
                    if(StringUtils.isEmpty(idall)){
                        idall=roleGroups.get(i).getRoleGroupId().toString();
                    }else{
                        idall=idall+","+roleGroups.get(i).getRoleGroupId().toString();
                    }
                    roleGroups.get(i).setParentApplicationId(roleGroups.get(i).getRoleGroupId());
                    idall=menuChildRole(roleGroups.get(i),idall);
                }
            }
            String[]  orgid=idall.split(",");
            List<String> idallst = new ArrayList<>();
            for(int o=0;o<orgid.length;o++){
                idallst.add(orgid[o]);
            }
            group.setOrgIdAlls(idallst);
            List<RoleGroup> roleId=roleGroupMapper.findRoleInfoId(group);
            for(RoleGroup roleInId:roleId){
                RoleInfo roleInfo=new RoleInfo();
                roleInfo.setIsDelete(IsDelete.YES.getCode());
                roleInfo.setRoleId(roleInId.getRoleId());
                roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
                acctOperPrivRelaMapper.deleteRoleId(roleInId.getRoleId());
                //删除角色与用户关联表
                acctRoleRealMapper.deleteByPrimaryKey2(roleInfo.getRoleId());
            }
            roleGroup.setOrgIdAlls(idallst);
            //删除
            roleGroup.setIsDelete(IsDelete.YES.getCode());

            for(int ac=0;ac<idallst.size();ac++){
                acctRoleGroupRoleMapper.deleteRoleGroupId(Long.parseLong(idallst.get(ac)));
            }
            roleGroupMapper.updateIsStatus(roleGroup);
            //返回删除此数据的父级ID
            roleGroup.setRoleGroupId(group1.getParentApplicationId());
        }else if(ParamType.UPDATE.getMsg().equals(opt)){
            roleGroup.setUpdatedBy("修改人");
            roleGroup.setUpdatedTime(new Date());
            //修改
            roleGroupMapper.updateByPrimaryKeySelective(roleGroup);
        }else if(ParamType.INSERT.getMsg().equals(opt)){
            roleGroup.setCreatedBy("创建人");
            roleGroup.setCreatedTime(new Date());
            roleGroup.setIsDelete(IsDelete.NO.getCode());
            String idFullPath="";
//            List<RoleGroup> listTitle=roleGroupMapper.findRoleGroupTitle(roleGroup);
//            if(CollectionUtils.isEmpty(listTitle)){
                if(StringUtils.isEmpty(roleGroup.getParentApplicationId())){
                    roleGroup.setTitleFullPath(roleGroup.getRoleGroupTitle());
                } else {
                    RoleGroup roleGroup2=roleGroupMapper.selectByPrimaryKey(roleGroup.getParentApplicationId());
                    roleGroup.setTitleFullPath(roleGroup2.getTitleFullPath()+","+roleGroup.getRoleGroupTitle());
                    idFullPath=roleGroup2.getIdFullPath()+",";
                }
                roleGroupMapper.insertSelective(roleGroup);
                roleGroup.setCreatedTime(null);
                RoleGroup roleGroup1=new RoleGroup();
                roleGroup1.setRoleGroupId(roleGroup.getRoleGroupId());
                roleGroup1.setIdFullPath(idFullPath+roleGroup.getRoleGroupId());
                roleGroupMapper.updateByPrimaryKeySelective(roleGroup);
//            }else{
//                jsonResponse.setRetCode("0000002");
//                jsonResponse.setRetDesc("角色组名称重复!");
//            }
        }
        roleGroup.setOrRole("角色组");
        RoleGroupVO roleGroupVO=BeanCopyUtil.copy(roleGroup, RoleGroupVO.class);
        return roleGroupVO;
    }

    public String menuChildRole(RoleGroup roleGroups,String idall){

        //根据父Id查询数据
        System.out.println(roleGroups.getParentApplicationId());
        List<RoleGroup> list=roleGroupMapper.findARoleGroupTree(roleGroups);
        for(int i=0;i<list.size();i++){
            idall=idall+","+list.get(i).getRoleGroupId().toString();

            roleGroups.setParentApplicationId(list.get(i).getRoleGroupId());
            System.out.println(roleGroups.getParentApplicationId());
            //查询当前级别的下一级是否有数据
            List<RoleGroup> listTree=roleGroupMapper.findARoleGroupTree(roleGroups);
            if(listTree.size()>0){
                roleGroups.setParentApplicationId(list.get(i).getRoleGroupId());
                idall=menuChildRole(roleGroups,idall);
            }
        }
        return idall;
    }
}
