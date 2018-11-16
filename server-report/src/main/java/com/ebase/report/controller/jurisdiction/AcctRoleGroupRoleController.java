package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.AcctRoleGroupRoleService;
import com.ebase.report.vo.jurisdiction.AcctRoleGroupRoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统基础模块-  角色组与角色关联
 * @Auther: zhaoyuhang
 */
@RestController
@RequestMapping("/acctRoleGroupRole")
public class AcctRoleGroupRoleController {

    private static Logger LOG = LoggerFactory.getLogger(AcctRoleGroupRoleController.class);

    @Autowired
    private AcctRoleGroupRoleService acctRoleGroupRoleService;


    /**
     * 系统功能管理 角色与角色组关联添加
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/addAcctRoleGroupRole")
    public JsonResponse<Integer> addAcctRoleGroupRole(@RequestBody JsonRequest<AcctRoleGroupRoleVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse();
        try {
            //角色组ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleGroupId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            //角色ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            LOG.info("list 参数 = {}", JsonUtil.toJson(jsonRequest));
            Integer acctRoleGroupRole = acctRoleGroupRoleService.addAcctRoleGroupRole(jsonRequest.getReqBody());
            jsonResponse.setRspBody(acctRoleGroupRole);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 系统功能管理 角色与角色组关联删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping("/delAcctRoleGroupRole")
    public JsonResponse<Integer> delAcctRoleGroupRole(@RequestBody JsonRequest<AcctRoleGroupRoleVO> jsonRequest){
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        try {
            //角色组ID
            if(StringUtils.isEmpty(jsonRequest.getReqBody().getRoleGroupId())){
                jsonResponse.setRetCode("0102005");
                return jsonResponse;
            }
            LOG.info("list 参数 = {}",JsonUtil.toJson(jsonRequest));
            Integer acctRoleGroupRole = acctRoleGroupRoleService.delAcctRoleGroupRole(jsonRequest.getReqBody());
            jsonResponse.setRspBody(acctRoleGroupRole);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
}
