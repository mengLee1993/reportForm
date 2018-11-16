package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.ParamType;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.RoleGroupService;
import com.ebase.report.vo.jurisdiction.RoleGroupVO;
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
 * 系统基础模块-  系统角色定义
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/roleGroup")
public class RoleGroupController {

    private static Logger LOG = LoggerFactory.getLogger(RoleGroupController.class);

    @Autowired
    private RoleGroupService roleGroupService;


    /**
     * 系统参数 list 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleGroupList")
    public JsonResponse<PageInfo<RoleGroupVO>> roleGroupList(@RequestBody JsonRequest<RoleGroupVO>  jsonRequest){
        JsonResponse<PageInfo<RoleGroupVO>> jsonResponse = new JsonResponse<>();
       try {
           RoleGroupVO roleGroup=jsonRequest.getReqBody();
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
           PageInfo<RoleGroupVO> page = roleGroupService.roleGroupList(roleGroup);
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
     *  系统角色组添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/InsertRoleGroup",method = RequestMethod.POST)
    public JsonResponse<RoleGroupVO> InsertRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<RoleGroupVO> jsonResponse = new JsonResponse();
        try {
            RoleGroupVO roleGroup=jsonRequest.getReqBody();
            //opt update
            if(StringUtils.isEmpty(roleGroup.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.INSERT.getMsg().equals(roleGroup.getOpt())) {
                    //分类名称
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleGroupVO roleGroupVO = roleGroupService.keepRoleGroup(roleGroup);
            jsonResponse.setRspBody(roleGroupVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统角色组删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/DeleteRoleGroup",method = RequestMethod.POST)
    public JsonResponse<RoleGroupVO> DeleteRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<RoleGroupVO> jsonResponse = new JsonResponse();
        try {
            RoleGroupVO roleGroup=jsonRequest.getReqBody();
            if(StringUtils.isEmpty(roleGroup.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.DELETE.getMsg().equals(roleGroup.getOpt())) {
                    //分类ID
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleGroupVO roleGroupVO = roleGroupService.keepRoleGroup(roleGroup);
            jsonResponse.setRspBody(roleGroupVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统参数 所有可用角色组 接口
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/roleGroupAll")
    public JsonResponse<List<RoleGroupVO>> roleGroupAll(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<List<RoleGroupVO>> jsonResponse = new JsonResponse();
        try {
            RoleGroupVO roleGroup=jsonRequest.getReqBody();
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            List<RoleGroupVO> roleGroupVOS = roleGroupService.findAll(roleGroup);
            jsonResponse.setRspBody(roleGroupVOS);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    @RequestMapping("/verificationDeleteRoleGroup")
    public JsonResponse<String> verificationDeleteRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<String> jsonResponse = new JsonResponse();
        try {
            RoleGroupVO roleGroup=jsonRequest.getReqBody();
            if(StringUtils.isEmpty(roleGroup.getRoleGroupId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            String ver = roleGroupService.verificationDeleteRoleGroup(roleGroup);
            jsonResponse.setRspBody(ver);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    @RequestMapping("/keepRoleGroup")
    public JsonResponse<RoleGroupVO> keepRoleGroup(@RequestBody JsonRequest<RoleGroupVO> jsonRequest){
        JsonResponse<RoleGroupVO> jsonResponse = new JsonResponse();
        try {
            RoleGroupVO roleGroup=jsonRequest.getReqBody();
            //opt update
            if(StringUtils.isEmpty(roleGroup.getOpt())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }else{
                if(ParamType.DELETE.getMsg().equals(roleGroup.getOpt())) {
                    //分类ID
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                }
                if(ParamType.UPDATE.getMsg().equals(roleGroup.getOpt())) {
                    //分类ID
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupId())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
                    //分类名称
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                }
                if(ParamType.INSERT.getMsg().equals(roleGroup.getOpt())) {
                    //分类名称
                    if(StringUtils.isEmpty(roleGroup.getRoleGroupTitle())){
                        jsonResponse.setRetCode("0102005");
                        return jsonResponse;
                    }
//                    //组织ID
//                    if(StringUtils.isEmpty(jsonRequest.getOrgId())){
//                        jsonResponse.setRetCode("0102005");
//                        return jsonResponse;
//                    }
                }
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            RoleGroupVO roleGroupVO = roleGroupService.keepRoleGroup(roleGroup);
            jsonResponse.setRspBody(roleGroupVO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
}
