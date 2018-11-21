package com.ebase.report.controller.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.SysBasicsAcctService;
import com.ebase.report.vo.jurisdiction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  系统基础 平台账号管理
 * @Auther: wangyu zhaotairn zhaoyichen
 */
@RestController
@RequestMapping("/sysAcct")
public class SysBasicsAcctController {

    private static Logger logger = LoggerFactory.getLogger(SysBasicsAcctController.class);

    @Autowired
    private SysBasicsAcctService sysBasicsAcctService;


    /**
     * 用户修改时查询角色和组织信息
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getAcctInfo")
    public JsonResponse<AcctInfoVO> getAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<AcctInfoVO> jsonResponse = new JsonResponse();
        try {
            logger.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));

            AcctInfoVO ver= sysBasicsAcctService.getAcctInfo(jsonRequest.getReqBody());
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 用户修改时查询角色和组织信息
     * @param jsonreq
     * @return
     */
    @RequestMapping("/verAcctInfo")
    public JsonResponse<String> verAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonreq){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            logger.info("list 参数 = {}",JsonUtil.toJson(jsonreq));
            AcctInfoVO jsonRequest=jsonreq.getReqBody();
            String ver= sysBasicsAcctService.verAcctInfo(jsonRequest);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 角色查询用户信息
     * @param jsonRequestVo
     * @return
     */
    @RequestMapping("/selectRoleIdAcctInfo")
    public JsonResponse<List<AcctInfoVO>> selectRoleIdAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequestVo){
        JsonResponse<List<AcctInfoVO>> jsonResponse = new JsonResponse();
        try {
            AcctInfoVO jsonRequest=jsonRequestVo.getReqBody();
            logger.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            if(StringUtils.isEmpty(jsonRequest.getRoleId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            List<AcctInfoVO> ver= sysBasicsAcctService.selectRoleIdAcctInfo(jsonRequest);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 平台账号 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/LoginAcct")
    public JsonResponse<AcctInfoVO> LoginAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<AcctInfoVO> jsonResponse = new JsonResponse();
        logger.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));

        try{
           AcctInfoVO page = sysBasicsAcctService.LoginAcct(jsonRequest.getReqBody());
           jsonResponse.setRspBody(page);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 平台账号 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctList")
    public JsonResponse<PageInfo<AcctInfoVO>> listSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse<PageInfo<AcctInfoVO>> jsonResponse = new JsonResponse();
        logger.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));

        try{
            AcctInfoVO acctInfoVO=jsonRequest.getReqBody();
            acctInfoVO.setoInfoId(AssertContext.getOrgId());
            acctInfoVO.setAcctType(AssertContext.getAcctType());
            jsonRequest.setReqBody(acctInfoVO);
            PageInfo<AcctInfoVO> page = sysBasicsAcctService.listSysAcct(jsonRequest);
            jsonResponse.setRspBody(page);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 平台账号 导出 接口
     * @param acctInfoVO
     * @return
     */
    @RequestMapping("/sysAcctListExcel")
    public JsonResponse excelListSysParam(@RequestBody AcctInfoVO acctInfoVO ){
        JsonResponse jsonResponse = new JsonResponse();
        logger.info("导出 参数 = {}",JsonUtil.toJson(acctInfoVO));

        try{
            List<AcctInfoExcel> page = sysBasicsAcctService.sysAcctListExcel(acctInfoVO);
            jsonResponse.setRspBody(page);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }


    /**
     * 获得详情
     * @param jsonRequest 就有个id
     * @return
     */
    @RequestMapping("/sysAcctGet")
    public JsonResponse getSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        logger.info("详情 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            AcctInfoVO reqBody = jsonRequest.getReqBody();

            AcctInfoRoleVO acctInfoRoleVO = sysBasicsAcctService.getSysAcct(reqBody);
            jsonResponse.setRspBody(acctInfoRoleVO);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }

    /**
     * 平台账号 关系  keep 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctKeep")
    public JsonResponse keepSysAcct(@RequestBody JsonRequest<List<AcctRoleRealVO>> jsonRequest){
        logger.info("保存 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.keepSysAcct(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 新建接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctAdd")
    public JsonResponse addSysAcct(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        logger.info("保存 参数 = {}",JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.addSysAcct(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }

    /**
     * 获得详情接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/getSysAcctInfo")
    public JsonResponse getSysAcctInfo(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        logger.info("获得详情 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.getSysAcctInfo(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }




    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理查询接口 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping( value = "/sysAcctToAcctRoleRealById" ,method = RequestMethod.POST)
    public JsonResponse listSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        JsonResponse jsonResponse = new JsonResponse();
        logger.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));

        try{
            List<AcctInfoVO> list = sysBasicsAcctService.listSysAcct2Role(jsonRequest);
            jsonResponse.setRspBody(list);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  中间表添加 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoAdd")
    public JsonResponse addSysAcct2Role(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        logger.info("keep 参数 = {}",JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.addSysAcct2Role(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }

    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  中间表删除 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoDelete")
    public JsonResponse deleteSysAcct2Role2(@RequestBody JsonRequest<AcctRoleRealVO> jsonRequest){
        logger.info("keep 参数 = {}",JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.deleteSysAcct2Role2(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }

    /**
     * @Auther: zhaoyichen
     * 用户 - 角色管理  删除 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctToRoleInfoDelete1")
    public JsonResponse deleteSysAcct2Role(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        logger.info("keep 参数 = {}",JsonUtil.toJson(jsonRequest));
        JsonResponse jsonResponse = null;

        try{
            jsonResponse = sysBasicsAcctService.deleteSysAcct2Role(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }


    /**
     * 用户管理 停用/启用
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctDiscontinuation")
    public JsonResponse sysAcctDiscontinuation(@RequestBody JsonRequest<AcctInfoVO> jsonRequest) {
        logger.info("www 停用/启用 参数 = {}", JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try {
            jsonResponse = sysBasicsAcctService.sysAcctDiscontinuation(jsonRequest);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 用户管理 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctDeleteById")
    public JsonResponse sysAcctDeleteById(@RequestBody JsonRequest<AcctInfoVO> jsonRequest){
        logger.info("www 删除 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysBasicsAcctService.sysAcctDeleteById(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }

    /**
     * 用户管理 新建用户
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/sysAcctAddUser")
    public JsonResponse sysAcctAddUser(@RequestBody JsonRequest<AcctToRoleInfoVO> jsonRequest){
        logger.info("www 新建用户 参数 = {}",JsonUtil.toJson(jsonRequest));

        JsonResponse jsonResponse = new JsonResponse();
        try{
            jsonResponse = sysBasicsAcctService.sysAcctAddUser(jsonRequest);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }









}
