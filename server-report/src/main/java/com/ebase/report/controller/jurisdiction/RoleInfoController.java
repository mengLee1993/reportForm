package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.ParamType;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.RoleInfoService;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;
import com.ebase.report.vo.jurisdiction.RoleInfoVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统基础模块-  系统角色定义
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/roleInfo")
public class RoleInfoController {

    private static Logger LOG = LoggerFactory.getLogger(RoleInfoController.class);

    @Autowired
    private RoleInfoService roleInfoService;


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
    public JsonResponse<PageInfo<RoleInfoVO>> findPageResult(@RequestBody JsonRequest<RoleInfoVO> jsonRequest) {
        JsonResponse<PageInfo<RoleInfoVO>> jsonResponse = new JsonResponse<>();
        try {
            LOG.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RoleInfoVO vo = jsonRequest.getReqBody();
            PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
            List<RoleInfoVO> acctOrgSysVOs = roleInfoService.queryForList(vo);
            PageInfo<RoleInfoVO> pages = new PageInfo(acctOrgSysVOs);
            jsonResponse.setRspBody(pages);
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
