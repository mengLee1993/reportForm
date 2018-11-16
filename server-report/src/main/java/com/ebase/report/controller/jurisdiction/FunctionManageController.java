package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.ParamType;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.FunctionManageService;
import com.ebase.report.vo.jurisdiction.FunctionManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 系统基础模块-  系统功能定义
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/functionManage")
public class FunctionManageController {

    private static Logger LOG = LoggerFactory.getLogger(FunctionManageController.class);


    @Autowired
    private FunctionManageService functionManageService;



    /**
     * 系统功能删除验证
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/verificationDeleteFunction")
    public JsonResponse<String> verificationDeleteFunction(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            String ver = functionManageService.verificationDeleteFunction(jsonRequest.getReqBody());
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
     * 系统功能管理 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/functionManageList")
    public JsonResponse<HashMap<String, List<FunctionManageVO>>> functionManageList(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<HashMap<String, List<FunctionManageVO>>> result = new JsonResponse<>();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            functionManageVO.setOrgId(AssertContext.getOrgId());
            functionManageVO.setAcctType(AssertContext.getAcctType());
            List<FunctionManageVO> page = functionManageService.functionManageList(functionManageVO);

            HashMap<String, List<FunctionManageVO>> objData = new HashMap<>();
            objData.put("resultData", page);
            result.setRspBody(objData);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            result.setRetCode(JsonResponse.SYS_EXCEPTION);
            return result;
        }
        return result;
    }

    /**
     * 角色功能查询功能管理树状图 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/functionManageRoleList")
    public JsonResponse<HashMap<String, List<FunctionManageVO>>> functionManageRoleList(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<HashMap<String, List<FunctionManageVO>>> jsonResponse = new JsonResponse<>();
		try {
			LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
//            if(StringUtils.isEmpty(functionManageVO.getOrgId())){
//                response.setRetCode("0102005");
//                return response;
//            }
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
			List<FunctionManageVO> functionManageVOList = functionManageService.functionManageRoleList(jsonRequest.getReqBody());
            HashMap<String, List<FunctionManageVO>> objData = new HashMap<>();
            objData.put("resultData", functionManageVOList);
            jsonResponse.setRspBody(objData);
		} catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
		}
        return jsonResponse;
    }


    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/updateFunctionManageStatus")
    public JsonResponse<Integer> updateFunctionManageStatus(@RequestBody JsonRequest<List<FunctionManageVO>> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            Integer updateStatus = functionManageService.updateFunctionManageStatus(jsonRequest.getReqBody());
            jsonResponse.setRspBody(updateStatus);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/InsertFunctionManage")
    public JsonResponse<FunctionManageVO> InsertFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest) {
        JsonResponse<FunctionManageVO> jsonResponse = new JsonResponse<>();
        try {
            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            //opt update
            if(StringUtils.isEmpty(functionManageVO.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.INSERT.getMsg().equals(functionManageVO.getOpt())) {
                    //功能名称
                    if(StringUtils.isEmpty(functionManageVO.getFunctionTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能路径
                    if(StringUtils.isEmpty(functionManageVO.getFunctionPath())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    functionManageVO.setCreatedBy(AssertContext.getAcctName());
                    //功能名称是否重复
                    List<FunctionManageVO> functionManageVOS = functionManageService.verificationFunIsTtitle(functionManageVO);
                    if(CollectionUtils.isEmpty(functionManageVOS)){

                    }else{
                        jsonResponse.setRetCode("0102006");
                        return jsonResponse;
                    }
                }
            }

            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            FunctionManageVO functionManage = functionManageService.keepFunctionManage(functionManageVO);
            jsonResponse.setRspBody(functionManage);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 系统功能管理 修改功能使用状态
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/keepFunctionManage")
    public JsonResponse<FunctionManageVO> keepFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<FunctionManageVO> jsonResponse = new JsonResponse<>();
        try {
            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            //opt update
            if(StringUtils.isEmpty(functionManageVO.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.DELETE.getMsg().equals(functionManageVO.getOpt())) {
                    //功能ID
                    if(StringUtils.isEmpty(functionManageVO.getFunctionId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(ParamType.UPDATE.getMsg().equals(functionManageVO.getOpt())) {
                    //功能ID
                    if(StringUtils.isEmpty(functionManageVO.getFunctionId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                    //功能名称
                    if(StringUtils.isEmpty(functionManageVO.getFunctionTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //功能路径
                    if(StringUtils.isEmpty(functionManageVO.getFunctionPath())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    functionManageVO.setCreatedBy(AssertContext.getAcctName());
                }

                if(ParamType.INSERT.getMsg().equals(functionManageVO.getOpt())) {
                    //功能名称
                    if(StringUtils.isEmpty(functionManageVO.getFunctionTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                    //功能路径
                    if(StringUtils.isEmpty(functionManageVO.getFunctionPath())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    functionManageVO.setCreatedBy(AssertContext.getAcctName());
                    //功能名称是否重复
                    List<FunctionManageVO> functionManageVOS = functionManageService.verificationFunIsTtitle(functionManageVO);
                    if(CollectionUtils.isEmpty(functionManageVOS)){

                    }else{
                        jsonResponse.setRetCode("0102006");
                        return jsonResponse;
                    }
                }
            }

            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            FunctionManageVO functionManage = functionManageService.keepFunctionManage(functionManageVO);
            jsonResponse.setRspBody(functionManage);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }



    /**
     * 系统功能删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteFunctionManage",method = RequestMethod.POST)
    public JsonResponse<FunctionManageVO> DeleteFunctionManage(@RequestBody JsonRequest<FunctionManageVO> jsonRequest){
        JsonResponse<FunctionManageVO> jsonResponse = new JsonResponse<>();
        try {
            FunctionManageVO functionManageVO=jsonRequest.getReqBody();
            //opt update
            if(StringUtils.isEmpty(functionManageVO.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.DELETE.getMsg().equals(functionManageVO.getOpt())) {
                    //功能ID
                    if(StringUtils.isEmpty(functionManageVO.getFunctionId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
            }

            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            FunctionManageVO functionManage = functionManageService.keepFunctionManage(functionManageVO);
            jsonResponse.setRspBody(functionManage);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
}
