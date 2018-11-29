package com.ebase.report.controller.jurisdiction;


import com.ebase.report.common.Status;
import com.ebase.report.core.HTTPUtil;
import com.ebase.report.core.ParamType;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.jurisdiction.RoleInfo;
import com.ebase.report.service.RptPersonalAnalysisService;
import com.ebase.report.service.jurisdiction.RoleInfoService;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;
import com.ebase.report.vo.jurisdiction.RoleInfoVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * 系统基础模块-  系统角色定义
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/roleInfo")
public class RoleInfoController {

    private static Logger LOG = LoggerFactory.getLogger(RoleInfoController.class);

    @Value("${juri.type}")
    private String type;

    @Value("${juri.auth.ip}")
    private String authIp;

    @Autowired
    private RoleInfoService roleInfoService;

    @Autowired
    private RptPersonalAnalysisService rptPersonalAnalysisService;


    /**
     * 系统参数 所有可用角色 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/verificationDeleteRoelInfo")
    public JsonResponse<String> verificationDeleteRoelInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            String ver= roleInfoService.verificationDeleteRoelInfo(roleInfoVO);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统参数 所有可用角色 接口  模糊查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleInfoAllLike")
    public JsonResponse<List<RoleInfoVO>> roleInfoAllLike(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<List<RoleInfoVO>> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            roleInfoVO.setAcctId(AssertContext.getAcctId());
            List<RoleInfoVO> roleInfoVOS = roleInfoService.roleInfoAllLike(roleInfoVO);
            jsonResponse.setRspBody(roleInfoVOS);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 系统参数 所有可用角色 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleInfoAll")
    public JsonResponse<List<RoleInfoVO>> roleInfoAll(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<List<RoleInfoVO>> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            List<RoleInfoVO> roleInfoVOS = roleInfoService.roleInfoAll(roleInfoVO);
            jsonResponse.setRspBody(roleInfoVOS);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 系统引用多个角色
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/orgQuoteRoleInfo")
    public JsonResponse<List<RoleInfoVO>> orgQuoteRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<List<RoleInfoVO>> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            roleInfoVO.setOrgId(AssertContext.getOrgId());
            List<RoleInfoVO> roleInfoVOS = roleInfoService.orgQuoteRoleInfo(roleInfoVO);
            jsonResponse.setRspBody(roleInfoVOS);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 系统参数 角色关联用户查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleRoleAcctInfo")
    public JsonResponse<Map> roleRoleAcctInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<Map> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
//            if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                jsonResponse.setRetCode("0102005");
//                return jsonResponse;
//            }
            if(StringUtils.isEmpty(roleInfoVO.getAcctId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            List<RoleInfoVO> roleInfoVOS = roleInfoService.roleRoleAcctInfo(roleInfoVO);
            Map map=new HashMap();
            map.put("resultData",roleInfoVOS);
            jsonResponse.setRspBody(map);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleInfoList")
    public JsonResponse<PageInfo<RoleInfoVO>> roleInfoList(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<PageInfo<RoleInfoVO>> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            PageInfo<RoleInfoVO> page = roleInfoService.roleInfoList(roleInfoVO);
            jsonResponse.setRspBody(page);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }




    /**
     * 系统参数 树状图 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleInfoTree")
    public JsonResponse<Map> roleInfoTree(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<Map> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO=jsonRequest.getReqBody();
            roleInfoVO.setOrgId(AssertContext.getOrgId());
            List<RoleInfoVO> page = roleInfoService.roleInfoTree(roleInfoVO);
            Map objData = new HashMap();
            objData.put("resultData",page);
            jsonResponse.setRspBody(objData);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    @RequestMapping("/saveCopyRole")
    public JsonResponse<RoleInfoVO> saveCopyRole(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            roleInfo.setCreatedBy(AssertContext.getAcctName());
            roleInfo.setCreatedTime(new Date());
            List<RoleInfoVO> roleInfoVOS = roleInfoService.verificationIsTtitleRole(roleInfo);
            if(CollectionUtils.isEmpty(roleInfoVOS)){

            }else{
                jsonResponse.setRetCode("0103004");
                return jsonResponse;
            }
            RoleInfoVO roleInfoVO = roleInfoService.saveCopyRole(roleInfo);
            jsonResponse.setRspBody(roleInfoVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    @RequestMapping("/keepRoleInfo")
    public JsonResponse<RoleInfoVO> keepRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            if(StringUtils.isEmpty(roleInfo.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else {
                if(ParamType.DELETE.getMsg().equals(roleInfo.getOpt())) {
                    //角色ID
                    if(StringUtils.isEmpty(roleInfo.getRoleId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(ParamType.UPDATE.getMsg().equals(roleInfo.getOpt())) {
                    //角色ID
                    if(StringUtils.isEmpty(roleInfo.getRoleId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                }
                if(ParamType.INSERT.getMsg().equals(roleInfo.getOpt())) {

                    if(StringUtils.isEmpty(roleInfo.getRoleTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                    List<RoleInfoVO> roleInfoVOS = roleInfoService.verificationIsTtitleRole(roleInfo);
                    if(CollectionUtils.isEmpty(roleInfoVOS)){

                    }else{
                        jsonResponse.setRetCode("0103004");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO = roleInfoService.keepRoleInfo(roleInfo);
            jsonResponse.setRspBody(roleInfoVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 引用时角色名是否重复
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/verQuoteRoleTitle")
    public JsonResponse<String> verQuoteRoleTitle(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            String ver= roleInfoService.verQuoteRoleTitle(roleInfo);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 引用时角色名是否重复
     * 一个组织引用多个角色
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/verQuoteRoleIds")
    public JsonResponse<String> verQuoteRoleIds(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            String ver= roleInfoService.verQuoteRoleIds(roleInfo);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 角色表-->分页+条件查询
     */
    @RequestMapping("/findpageresult")
    public JsonResponse<PageDTO<RoleInfo>> findPageResult(@RequestBody JsonRequest<RoleInfo> jsonRequest) {
        JsonResponse<PageDTO<RoleInfo>> jsonResponse = new JsonResponse<>();
        try {
            LOG.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RoleInfo roleInfo = jsonRequest.getReqBody();
            Long sysId = roleInfo.getSysId();
            if(type.equals("core")) {
                PageDTO<RoleInfo> acctOrgSysVOs = roleInfoService.queryForList(roleInfo);
                List<RoleInfo> resultData = acctOrgSysVOs.getResultData();
                for(RoleInfo roleId:resultData){
                    if(sysId != null){
                        roleId.setType((byte)0);
                        //用loginid和报表id差
                        RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisService.getByRoleId(roleId.getRoleId().toString(),sysId);
                        if(rptPersonalAnalysis != null){
                            roleId.setType((byte)1);
                        }
                    }
                }

                jsonResponse.setRspBody(acctOrgSysVOs);
            }else if (type.equals("report")) {
                if(roleInfo.getRoleTitle()==null){
                    roleInfo.setRoleTitle("");
                }
                if(roleInfo.getRoleCode()==null){
                    roleInfo.setRoleCode("");
                }
                String url = "http://" + authIp + "/auth/ac/ac-role/findUsingProjectRoles.action?_dc=1542681842909&page=" + roleInfo.getPageNum() + "&limit=" + roleInfo.getPageSize() +
                        "&qm.projectId=ZDYBB" +
                        "&qm.roleName="+roleInfo.getRoleTitle()+
                        "&qm.roleId="+roleInfo.getRoleCode();
                //String url="http://192.168.1.100:7070/auth/ac/login/dologin.action?username=liq0416&password=admin123";
                HashMap map = new HashMap();
                JSONObject json = JSON.parseObject(HTTPUtil.postParams(url, AssertContext.getToken(), map));
                System.out.println(json);
                String success = json.getString("success");
                if (success.equals("true") || success == "true") {
                    List<RoleInfo> roleInfoVOS = new ArrayList<>();
                    PageDTO<RoleInfo> pages = new PageDTO();
                    if (JSON.parseObject(json.getString("items")).get("records") != null) {
                        JSONArray jsonObject1 = JSON.parseObject(json.getString("items")).getJSONArray("records");
                        for (int i = 0; i < jsonObject1.size(); i++) {
                            RoleInfo roleInfo1 = new RoleInfo();
                            String roleId = JSON.parseObject(jsonObject1.getString(i)).getString("roleId");
                            roleInfo1.setRoleDesc(JSON.parseObject(jsonObject1.getString(i)).getString("roleDesc"));
                            roleInfo1.setReRoleId(roleId);
                            roleInfo1.setRoleCode(roleId);
                            roleInfo1.setRoleTitle(JSON.parseObject(jsonObject1.getString(i)).getString("roleName"));
                            roleInfo1.setOrgId(JSON.parseObject(jsonObject1.getString(i)).getString("orgId"));
                            roleInfo1.setStatus(Status.START.getCode());

                            if(sysId != null){
                                roleInfo1.setType((byte)0);
                                //用loginid和报表id差
                                RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisService.getByRoleId(roleId,sysId);
                                if(rptPersonalAnalysis != null){
                                    roleInfo1.setType((byte)1);
                                }
                            }

                            roleInfoVOS.add(roleInfo1);
                        }
                    }
                    pages.setResultData(roleInfoVOS);
                    pages.setPageSize(roleInfo.getPageSize());
                    pages.setPageNum(roleInfo.getPageNum());
                    pages.setTotal(Integer.parseInt(JSON.parseObject(json.getString("items")).getString("total")));
                    jsonResponse.setRspBody(pages);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage() ,e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }




    /**
     *  系统角色定义 添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/InsertRoleInfo",method = RequestMethod.POST)
    public JsonResponse<RoleInfoVO> InsertRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            if(StringUtils.isEmpty(roleInfo.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else {
                if(ParamType.INSERT.getMsg().equals(roleInfo.getOpt())) {

                    if(StringUtils.isEmpty(roleInfo.getRoleTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    List<RoleInfoVO> roleInfoVOS = roleInfoService.verificationIsTtitleRole(roleInfo);
                    if(!CollectionUtils.isEmpty(roleInfoVOS)){
                        jsonResponse.setRetCode("0103004");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO = roleInfoService.keepRoleInfo(roleInfo);
            jsonResponse.setRspBody(roleInfoVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }




    /**
     *  系统角色定义 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteRoleInfo",method = RequestMethod.POST)
    public JsonResponse<RoleInfoVO> DeleteRoleInfo(@RequestBody JsonRequest<RoleInfoVO> jsonRequest){
        JsonResponse<RoleInfoVO> jsonResponse = new JsonResponse();
        try {
            RoleInfoVO roleInfo=jsonRequest.getReqBody();
            if(StringUtils.isEmpty(roleInfo.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else {
                if(ParamType.DELETE.getMsg().equals(roleInfo.getOpt())) {
                    //角色ID
                    if(StringUtils.isEmpty(roleInfo.getRoleId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleInfoVO roleInfoVO = roleInfoService.keepRoleInfo(roleInfo);
            jsonResponse.setRspBody(roleInfoVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

}
