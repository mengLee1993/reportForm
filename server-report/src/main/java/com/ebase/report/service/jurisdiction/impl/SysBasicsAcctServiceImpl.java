package com.ebase.report.service.jurisdiction.impl;

import com.ebase.report.core.MD5Util;
import com.ebase.report.core.ParamType;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.DateUtil;
import com.ebase.report.service.jurisdiction.SysBasicsAcctService;
import com.ebase.report.vo.jurisdiction.*;
import com.ebase.report.dao.jurisdiction.*;
import com.ebase.report.model.jurisdiction.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wangyu
 */
@Service
public class SysBasicsAcctServiceImpl implements SysBasicsAcctService {

    private static Logger LOG = LoggerFactory.getLogger(SysBasicsAcctServiceImpl.class);



    @Autowired
    private AcctInfoMapper acctInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private AcctRoleRealMapper acctRoleRealMapper;

    @Autowired
    private AcctInfoMapper AcctInfoMapper;

    @Autowired
    private OrgInfoMapper orgInfoMapper;

    @Autowired
    private AcctUserSysMapper acctUserSysMapper;


    /**
     *  模拟登录  zyh
     * @param reqBody
     * @return
     * @throws RuntimeException
     */
    @Override
    public AcctInfoVO LoginAcct(AcctInfoVO reqBody)throws RuntimeException{
        AcctInfo acctInfo= BeanCopyUtil.copy(reqBody,AcctInfo.class);
        AcctInfo resultData = acctInfoMapper.LoginAcct(acctInfo);
        AcctInfoVO acctRoleRealVO=BeanCopyUtil.copy(resultData,AcctInfoVO.class);
        return acctRoleRealVO;

    }

    /**
     * 根据角色查询用户   zyh
     * @param reqBody
     * @return
     * @throws RuntimeException
     */
    @Override
    public List<AcctInfoVO> selectRoleIdAcctInfo(AcctInfoVO reqBody)throws RuntimeException{
        AcctInfo acctInfo=BeanCopyUtil.copy(reqBody,AcctInfo.class);
        List<AcctInfo> resultData = acctInfoMapper.selectRoleIdAcctInfo(acctInfo);
        return BeanCopyUtil.copyList(resultData,AcctInfoVO.class);
    }


    @Override
    public List<AcctInfoExcel> sysAcctListExcel(AcctInfoVO reqBody)throws RuntimeException{
        //先按 page 版查询
//        AcctInfoVO reqBody = jsonRequest.getReqBody();
        if(reqBody == null){
            reqBody = new AcctInfoVO();
        }
//            PageDTOUtil.startPage(reqBody);
        //可能还有更多参数
        List<AcctInfo> resultData = acctInfoMapper.find(reqBody);
//            PageDTO<AcctInfo> page = PageDTOUtil.transform(list);

        //转成 vo 对象
//            List<AcctInfo> resultData = page.getResultData();

        List<AcctInfoExcel> acctInfoExcels = BeanCopyUtil.copyList(resultData, AcctInfoExcel.class);

        int i = 0;
        for(AcctInfo acctInfo:resultData){

            AcctInfoExcel acctInfoExcel = acctInfoExcels.get(i);
            acctInfoExcel.setLine(i + 1);
            if(acctInfo.getEntTime() != null){
                String view = DateUtil.formatDate(acctInfo.getEntTime(), DateUtil.DATE_PATTERN);
                acctInfoExcel.setEntTimeView(view);
            }

            if(acctInfo.getStartTime() != null){
                String view = DateUtil.formatDate(acctInfo.getStartTime(), DateUtil.DATE_PATTERN);
                acctInfoExcel.setStartTimeView(view);
            }
            i ++;
        }
        return acctInfoExcels;

//            PageDTOUtil.endPage();


    }

    //角色  账号 关系  保存
    @Override
    public JsonResponse keepSysAcct(JsonRequest<List<AcctRoleRealVO>> jsonRequest)throws RuntimeException {
        JsonResponse jsonResponse = new JsonResponse();

        List<AcctRoleRealVO> reqBody = jsonRequest.getReqBody();

        for(AcctRoleRealVO acctRoleRealVO:reqBody){
            String opt = acctRoleRealVO.getOpt();
            if(ParamType.DELETE.getMsg().equals(opt)){

                acctRoleRealMapper.deleteByPrimaryKey(acctRoleRealVO.getRelaId());
            }else if(ParamType.UPDATE.getMsg().equals(opt)){
                AcctRoleReal acctRoleReal = new AcctRoleReal();

                BeanCopyUtil.copy(acctRoleRealVO,acctRoleReal);
                acctRoleRealMapper.updateByPrimaryKeySelective(acctRoleReal);
            }else if(ParamType.INSERT.getMsg().equals(opt)){

                AcctRoleReal acctRoleReal = new AcctRoleReal();

                BeanCopyUtil.copy(acctRoleRealVO,acctRoleReal);

                acctRoleRealMapper.insertSelective(acctRoleReal);
            }

        }

        return jsonResponse;
    }

    /**
     * 保存接口
     * @param jsonRequest
     * @return
     */
    @Override
    public JsonResponse addSysAcct(JsonRequest<AcctInfoVO> jsonRequest) throws RuntimeException{

        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);

        if(ParamType.INSERT.getMsg().equals(reqBody.getOpt())){
            acctInfo.setAcctId(null);
            acctInfoMapper.insertSelective(acctInfo);
        }else if(ParamType.UPDATE.getMsg().equals(reqBody.getOpt())){
            acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
        }


        return jsonResponse;
    }

    /**
     * 获得详情接口
     * @param reqBody
     * @return
     */
    @Override
    public AcctInfoRoleVO getSysAcct(AcctInfoVO reqBody)throws RuntimeException {

        AcctInfoRoleVO acctInfoRoleVO = new AcctInfoRoleVO();

        Long acctId = reqBody.getAcctId(); //用这个id 去库里 楼数据



        List<AcctInfo> acctInfos = acctInfoMapper.find(reqBody);

        if(CollectionUtils.isEmpty(acctInfos)){
            LOG.info("未查询到数据！！");
            return acctInfoRoleVO;
        }
        AcctInfo acctInfo = acctInfos.get(0);

        BeanCopyUtil.copy(acctInfo,acctInfoRoleVO);
        //所有的角色
        List<RoleInfo> roleInfos = roleInfoMapper.find(new RoleInfo());
        if(CollectionUtils.isNotEmpty(acctInfos)){
            List<RoleInfoVO> roleInfoVOS = BeanCopyUtil.copyList(roleInfos, RoleInfoVO.class);
            acctInfoRoleVO.setRoleInfoVOs(roleInfoVOS);
        }

        return acctInfoRoleVO;
    }

    @Override
    public JsonResponse getSysAcctInfo(JsonRequest<AcctInfoVO> jsonRequest) throws RuntimeException{
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = acctInfoMapper.selectByPrimaryKey(reqBody.getAcctId());

        if(acctInfo != null){
            AcctInfoVO acctInfoVO = new AcctInfoVO();
            BeanCopyUtil.copy(acctInfo,acctInfoVO);

            jsonResponse.setRspBody(acctInfoVO);
        }
        return jsonResponse;
    }




    //用户下角色管理 - 查询
    @Override
    public List<AcctInfoVO> listSysAcct2Role(JsonRequest<AcctInfoVO> jsonRequest) {
        AcctInfoVO  reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);
        //原始数据
        List<AcctInfo> acctInfoList = AcctInfoMapper.listSysAcct2Role(acctInfo);

//        List<AcctInfoVO> result = BeanCopyUtil.copyList(acctInfoList, AcctInfoVO.class);
        List<AcctInfoVO> result = new ArrayList<>(acctInfoList.size());
        for(AcctInfo acctInfoMd:acctInfoList){
            AcctInfoVO acctInfoVO = new AcctInfoVO();
            BeanCopyUtil.copy(acctInfoMd,acctInfoVO);
            result.add(acctInfoVO);

            acctInfoVO.setRoleInfo(BeanCopyUtil.copyList(acctInfoMd.getRoleInfo(),AcctInfoVO.class));

        }

        return result;
    }


    //用户下角色管理 - 中间表添加 zhaoyichen
    @Override
    public JsonResponse addSysAcct2Role( JsonRequest<AcctRoleRealVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();

        AcctRoleRealVO acctRoleRealVO = jsonRequest.getReqBody();

        AcctRoleReal reqBody = BeanCopyUtil.copy(acctRoleRealVO, AcctRoleReal.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (ParamType.INSERT.getMsg().equals(existence)) {


            reqBody.setAcctId(reqBody.getAcctId());
            reqBody.setRelaId(reqBody.getRoleId());

            acctRoleRealMapper.addSysAcct2Role(reqBody);
        }

        return jsonResponse;
    }

    //用户下角色管理 -  中间表删除
    @Override
    public JsonResponse deleteSysAcct2Role2(JsonRequest<AcctRoleRealVO>jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctRoleRealVO acctRoleRealVO = jsonRequest.getReqBody();

        AcctRoleReal reqBody = BeanCopyUtil.copy(acctRoleRealVO, AcctRoleReal.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (ParamType.DELETE.getMsg().equals(existence)) {

            acctRoleRealMapper.deleteByPrimaryKey3(reqBody);

        }
        return jsonResponse;

    }


    //用户下角色管理 -  全删除
    @Override
    public JsonResponse deleteSysAcct2Role(JsonRequest<AcctInfoVO>jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO acctInfoVO = jsonRequest.getReqBody();

        AcctInfo reqBody = BeanCopyUtil.copy(acctInfoVO, AcctInfo.class);

        String existence = reqBody.getExistence();

        if (StringUtils.isEmpty(existence)) {
            jsonResponse.setRetDesc("acct缺少参数！");
            jsonResponse.setRetDesc("acct字段值不正确！");
            return jsonResponse;
        }
        if (ParamType.DELETE.getMsg().equals(existence)) {

            acctRoleRealMapper.deleteByPrimaryKey(reqBody.getAcctId());

        }
        return jsonResponse;

    }


    @Override
    public JsonResponse sysAcctDiscontinuation(JsonRequest<AcctInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);

        acctInfoMapper.updateByPrimaryKeySelective(acctInfo);

        return jsonResponse;
    }

    @Override
    public JsonResponse sysAcctDeleteById(JsonRequest<AcctInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();

        AcctInfoVO reqBody = jsonRequest.getReqBody();

        AcctInfo acctInfo = new AcctInfo();

        BeanCopyUtil.copy(reqBody,acctInfo);
        AcctRoleReal acctRoleReal = new AcctRoleReal();
        acctRoleReal.setAcctId(acctInfo.getAcctId());
        acctRoleRealMapper.deleteAcctRole(acctRoleReal);

        acctInfo.setIsDelete((byte)1);
        acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
        return jsonResponse;
    }


    /**
     * 用户添加
     * @param jsonRequest
     * @return
     */
    @Override
    public JsonResponse sysAcctAddUser(JsonRequest<AcctToRoleInfoVO> jsonRequest) {
        JsonResponse jsonResponse = new JsonResponse();
        AcctToRoleInfoVO reqBody = jsonRequest.getReqBody();
        AcctInfo acctInfo = new AcctInfo();
        BeanCopyUtil.copy(reqBody,acctInfo);
        if(acctInfo.getAcctId()==null||acctInfo.getAcctId()==' ')
        {
            acctInfo.setStatus((byte)1);
            acctInfo.setIsDelete((byte)0);
            if(StringUtils.isEmpty(acctInfo.getoInfoId())) {
                //组织ID为空创建虚拟组织
                String id=getOrgInfoId();
                OrgInfo orgInfo=new OrgInfo();
                orgInfo.setId(id);
                orgInfo.setStatus("1");
                orgInfo.setCreatedBy("创建人");
                orgInfo.setCreatedTime(new Date());
                orgInfo.setParentId("1");
                orgInfoMapper.insertOrgInfo(orgInfo);
                acctInfo.setoInfoId(id);
            }

            acctInfo.setAcctPassword(MD5Util.encrypByMd5(acctInfo.getAcctPassword()));

            //添加 用户表
            acctInfoMapper.insertAcctInfo(acctInfo);

            if(acctInfo.getAcctType()==1){
                //创建默认角色与用户关联
                AcctRoleReal acctRoleRealSup = new AcctRoleReal();
                acctRoleRealSup.setAcctId(acctInfo.getAcctId());
                acctRoleRealSup.setRoleId(Long.parseLong("-2"));
                acctRoleRealMapper.insertSelective(acctRoleRealSup);
            }else if(acctInfo.getAcctType()==3){
                //创建默认角色与用户关联
                AcctRoleReal acctRoleRealSup = new AcctRoleReal();
                acctRoleRealSup.setAcctId(acctInfo.getAcctId());
                acctRoleRealSup.setRoleId(Long.parseLong("-3"));
                acctRoleRealMapper.insertSelective(acctRoleRealSup);
                AcctUserSys acctUserSys=new AcctUserSys();
                acctUserSys.setAcctId(acctInfo.getAcctId());
                acctUserSys.setSysId(acctInfo.getSysId());
                acctUserSysMapper.insertSelective(acctUserSys);
            }

            List<Long> roleIds = reqBody.getRoleIds();
            for(Long r: roleIds){
                AcctRoleReal acctRoleReal = new AcctRoleReal();
                acctRoleReal.setAcctId(acctInfo.getAcctId());
                acctRoleReal.setRoleId(r);
                acctRoleRealMapper.insertAcctRole(acctRoleReal);
            }
        }else if(acctInfo.getAcctId()!=null){

            //用户表修改
            this.acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
            List<Long> roleIds = reqBody.getRoleIds();
            AcctRoleReal acctRoleReal = new AcctRoleReal();
            acctRoleReal.setAcctId(acctInfo.getAcctId());
            acctRoleRealMapper.deleteAcctRole(acctRoleReal);
//            if(acctInfo.getAcctType()==1){
//                //创建默认角色与用户关联
//                AcctRoleReal acctRoleRealSup = new AcctRoleReal();
//                acctRoleRealSup.setAcctId(acctInfo.getAcctId());
//                acctRoleRealSup.setRoleId(Long.parseLong("-2"));
//                acctRoleRealMapper.insertSelective(acctRoleRealSup);
//            }else if(acctInfo.getAcctType()==3){
//                //创建默认角色与用户关联
//                AcctRoleReal acctRoleRealSup = new AcctRoleReal();
//                acctRoleRealSup.setAcctId(acctInfo.getAcctId());
//                acctRoleRealSup.setRoleId(Long.parseLong("-3"));
//                acctRoleRealMapper.insertSelective(acctRoleRealSup);
//            }
            for(Long r: roleIds){
                AcctRoleReal acctRoleRea2 = new AcctRoleReal();
                acctRoleRea2.setAcctId(acctInfo.getAcctId());
                acctRoleRea2.setRoleId(r);
                acctRoleRealMapper.insertSelective(acctRoleRea2);
            }
        }


        /* acctToRoleInfoVO.setRoleIds(reqBody.getRoleIds());*/
        //添加角色 用户  中间表



        return jsonResponse;
    }


    //创建虚拟组织ID
    public String getOrgInfoId() {
        String id = orgInfoMapper.getOrgInfoId("1");
        //获取最高节点如果存在加一，否则创建一个
        if(id != null) {
            BigInteger result1=new BigInteger(id);
            BigInteger result2=new BigInteger("1");
            id=result1.add(result2).toString();
        }else {
            id = "10000000000";
        }
        return id;
    }

    /**
     * 用户分页查询
     * @param jsonRequest
     * @return
     * @throws RuntimeException
     */
    @Override
    public PageInfo<AcctInfoVO> listSysAcct(JsonRequest<AcctInfoVO> jsonRequest)throws RuntimeException {

        //这个对象 可直接操作数   据库
        AcctInfoVO reqBody = jsonRequest.getReqBody();
        AcctInfo ai=new AcctInfo();
        BeanCopyUtil.copy(reqBody,ai);
        try{
            ai.setAcctType(null);
//            if(ai.getAcctType()==1){
//                ai.setAcctType(Long.parseLong("2"));
//            }
//            if(ai.getAcctType()==0){
//                ai.setAcctType(Long.parseLong("1"));
//                ai.setoInfoId(null);
//            }
            PageHelper.startPage(reqBody.getPageNum(), reqBody.getPageSize());
            //获取用户信息
            List<AcctInfo> list = acctInfoMapper.findPage(ai);
            PageInfo<AcctInfo> pageInfo = new PageInfo<>(list);
            //转成 vo 对象
            PageInfo<AcctInfoVO> pageVo = new PageInfo(BeanCopyUtil.copyList(list, AcctInfoVO.class));
            pageVo.setTotal(pageInfo.getTotal());
            pageVo.setPages(pageInfo.getPages());
            pageVo.setPageNum(pageInfo.getPageNum());
            pageVo.setPageSize(pageInfo.getPageSize());
            List<AcctInfo> resultData = pageInfo.getResultData();

            List<AcctInfoVO> result = new ArrayList<>(resultData.size());

            for(AcctInfo acctInfo:resultData){
                AcctInfoVO acctInfoVO = new AcctInfoVO();

                //时间
                if(acctInfo.getStartTime() != null){
                    acctInfoVO.setStartTimeView(DateFormatUtils.format(acctInfo.getStartTime(),"yyyy-MM-dd"));
                }
                if(acctInfo.getEntTime() != null){
                    acctInfoVO.setEntTimeView(DateFormatUtils.format(acctInfo.getEntTime(),"yyyy-MM-dd"));
                }

                BeanCopyUtil.copy(acctInfo,acctInfoVO);


//                OrgInfoVO orgInfoVO = new OrgInfoVO();
//                //前端需要把树改成List结构渲染
//                List<OrgInfoVO> orgList = new ArrayList<>();
//                copyOrgByTree(orgInfoVO, acctInfo.getOrgInfo(), orgList);
//                acctInfoVO.setOrgInfo(orgInfoVO);
//                acctInfoVO.setOrgArr(orgList);
//                result.add(acctInfoVO);

                result.add(acctInfoVO);
            }

            pageVo.setResultData(result);
            return pageVo;
        } finally {
//            PageDTOUtil.endPage();
            PageHelper.clearPage();
        }
    }


    /**
     * 用户添加验证信息
     * @return
     */
    @Override
    public String verAcctInfo(AcctInfoVO acctInfoVO) {
        String ver="";
        AcctInfo acctInfo=BeanCopyUtil.copy(acctInfoVO,AcctInfo.class);
        List<AcctInfo> list1 = acctInfoMapper.verAcctInfo(acctInfo);
        if(CollectionUtils.isEmpty(list1)){
            ver="0";
        }else {
            ver="1";
            return ver;
        }
        if(acctInfo.getAcctType()==1){
            List<AcctInfo> list = acctInfoMapper.verAcctInfoType(acctInfo);
            if(CollectionUtils.isEmpty(list)){
                ver="0";
            }else {
                ver="2";
                return ver;
            }
        }
        if(acctInfo.getAcctType()==3){
            List<AcctInfo> list = acctInfoMapper.verAcctInfoSysId(acctInfo);
            if(CollectionUtils.isEmpty(list)){
                ver="0";
            }else {
                ver="3";
                return ver;
            }
        }
        return ver;
    }

    /**
     * 根据用户查询此用户关联的角色和组织信息
     * @return
     */
    @Override
    public AcctInfoVO getAcctInfo(AcctInfoVO acctInfoVO) {
        PageHelper.clearPage();
        AcctInfo acctInfo=new AcctInfo();
        BeanCopyUtil.copy(acctInfoVO,acctInfo);
        AcctInfo acctId=new AcctInfo();
        acctId.setAcctId(acctInfo.getAcctId());
        List<AcctInfo> list1 = acctInfoMapper.findAcctRoleInfo(acctId);
        if(!CollectionUtils.isEmpty(list1)){
            acctInfo=list1.get(0);
            List<RoleInfo> roleList =acctInfo.getRoleInfo();
            acctInfo.setRoleArr(roleList);

            OrgInfo org = getOrgInfoByTree(acctInfo.getoInfoId(),acctInfoVO.getLoginOrgId());
            acctInfo.setOrgInfo(org);
        }

        AcctInfoVO acctInfoVO1=BeanCopyUtil.copy(acctInfo,AcctInfoVO.class);

        OrgInfoVO orgInfoVO = new OrgInfoVO();
        //前端需要把树改成List结构渲染
        List<OrgInfoVO> orgList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list1)) {
            copyOrgByTree(orgInfoVO, acctInfo.getOrgInfo(), orgList);
        }
        acctInfoVO1.setOrgInfo(orgInfoVO);
        acctInfoVO1.setOrgArr(orgList);

        return acctInfoVO1;
    }

    private OrgInfoVO copyOrgByTree(OrgInfoVO orgInfoVO, OrgInfo orgInfo, List<OrgInfoVO> orgList) {
        BeanCopyUtil.copy(orgInfo, orgInfoVO);
        if (StringUtils.isEmpty(orgInfo)) {
            OrgInfoVO orgInfoVO1=new OrgInfoVO();
            return orgInfoVO1;
        }else {
            OrgInfo parent = orgInfo.getParent();

            if (parent != null) {
                OrgInfoVO parentVO = copyOrgByTree(new OrgInfoVO(), parent, orgList);
                orgInfoVO.setParent(parentVO);
                orgList.add(parentVO);
            }
            return orgInfoVO;
        }
    }

    /**
     * 递归 子-》父
     * @param orgId
     * @return
     */
    public OrgInfo getOrgInfoByTree(String orgId,String loginOrgId) {
        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setParentId(orgId);
        orgInfo.setId(loginOrgId);
        OrgInfo org = orgInfoMapper.selectOrgInfoAcctInfo(orgInfo);

        getParent(org,loginOrgId);
        return org;
    }

    /**
     *  递归 判断parentId是否为空
     * @param orgInfo
     */
    private void getParent(OrgInfo orgInfo,String loginOrgId) {
        if (StringUtils.isEmpty(orgInfo)) {
            return;
        }else {
            String parentId = orgInfo.getParentId();
            if (parentId == null || parentId == "")
                return;
            OrgInfo orgInfose=new OrgInfo();
            orgInfose.setId(loginOrgId);
            orgInfose.setParentId(parentId);
            OrgInfo parent = orgInfoMapper.selectOrgInfo2(orgInfose);
            orgInfo.setParent(parent);
            getParent(parent,loginOrgId);
        }
    }



//    public AcctInfoVO insertNoseAcctInfo(AcctInfoVO jsonRequest) {
//        AcctInfoVO vo=jsonRequest;
//        if(vo.getRegisterEnum()==Byte.parseByte("0")){
//            vo.setoInfoId("19000000000");
//            vo.setRoleId(Long.parseLong("-1"));
//            vo.setAcctType(Long.parseLong("1"));
//        }
//        if(vo.getRegisterEnum()==Byte.parseByte("1")){
//            RoleInfo roleInfo=new RoleInfo();
//            roleInfo.setOrgId("0");
//            roleInfo.setRoleTitle("供应商角色");
//            RoleInfo ri=roleInfoMapper.selectInitializationRole(roleInfo);
//            vo.setRoleId(ri.getRoleId());
//            vo.setAcctType(Long.parseLong("1"));
//        }
//
//        AcctInfo acctInfo=new AcctInfo();
////        BeanCopyUtil.copy(jsonRequest,AcctInfo.class);
//        BeanCopyUtil.copy(jsonRequest,acctInfo);
//        acctInfo.setStatus(Byte.parseByte("1"));
//        acctInfo.setIsDelete(Byte.parseByte("0"));
//        acctInfoMapper.insertAcctInfo(acctInfo);
//        //创建默认角色与用户关联
//        AcctRoleReal acctRoleReal = new AcctRoleReal();
//        acctRoleReal.setAcctId(acctInfo.getAcctId());
//        acctRoleReal.setRoleId(Long.parseLong("-1"));
//        acctRoleRealMapper.insertSelective(acctRoleReal);
//        vo.setAcctId(acctInfo.getAcctId());
//        return vo;
//    }
//
//
//    public void updateNoseAcctInfo(AcctInfoVO jsonRequest) {
//        AcctInfo acctInfo=new AcctInfo();
//        BeanCopyUtil.copy(jsonRequest,acctInfo);
//        acctInfoMapper.updateByPrimaryKeySelective(acctInfo);
//        //删除角色关联
//
//        AcctRoleReal acctRoleReal = new AcctRoleReal();
//        acctRoleReal.setAcctId(acctInfo.getAcctId());
//        acctRoleRealMapper.deleteAcctRole(acctRoleReal);
//
//        //添加角色关联
////        AcctRoleReal acctRoleRea2 = new AcctRoleReal();
////        acctRoleRea2.setAcctId(acctInfo.getAcctId());
////        acctRoleRea2.setRoleId(Long.parseLong("-1"));
////        acctRoleRealMapper.insertSelective(acctRoleReal);
//    }
}
