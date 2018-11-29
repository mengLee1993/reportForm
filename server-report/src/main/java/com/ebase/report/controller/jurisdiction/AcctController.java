package com.ebase.report.controller.jurisdiction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebase.report.core.HTTPUtil;
import com.ebase.report.core.TokenUtil;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.session.AcctLogin;
import com.ebase.report.core.session.AcctSession;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.CookieUtil;
import com.ebase.report.core.utils.secret.Md5Util;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.service.jurisdiction.AcctService;
import com.ebase.report.service.jurisdiction.FunctionManageService;
import com.ebase.report.vo.jurisdiction.FunctionManageVO;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: wangyu
 */
@RestController
@RequestMapping("/acct")
public class AcctController {

    private static Logger LOG = LoggerFactory.getLogger(AcctController.class);

    @Autowired
    private AcctService acctService;

    @Value("${juri.type}")
    private String type;

    @Value("${juri.auth.ip}")
    private String authIp;

    @Autowired
    private FunctionManageService functionManageService;

//    @Autowired
//    private CacheService cacheService;

//    /**
//     * 根据当前用户查询出，当前用户的全部功能
//     * @return
//     */
//    @RequestMapping("/getUserFunctionAll")
//    public JsonResponse<List<FunctionManageVO>> getUserFunctionAll(@RequestBody AcctInfoVO acctInfoVO){
//        JsonResponse<List<FunctionManageVO>> jsonResponse = new JsonResponse<>();
//        AcctInfo acctInfo = BeanCopyUtil.copy(acctInfoVO, AcctInfo.class);
//        /*Long i = (long)123;
//        acctInfo.setAcctId(i);*/
//        try{
//        	List<FunctionManage> sunctionManageAll = acctService.getUserFunctionAll(acctInfo);
//        	List<FunctionManageVO> functionManageVO = BeanCopyUtil.copyList(sunctionManageAll, FunctionManageVO.class);
//            jsonResponse.setRspBody(functionManageVO);
//
//        }catch (Exception e){
//            LOG.error(e.getMessage());
//            e.printStackTrace();
//            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
//            return jsonResponse;
//        }
//
//        return jsonResponse;
//    }
//
//    /**
//     * 注册
//     * @return
//     */
//    @RequestMapping("/register")
//    public JsonResponse<Integer> userRegister(@RequestBody AcctInfoVO acctInfoVO){
//        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
//
//        try{
//
//            //校验
////            ReturnMessageVO returnMessageVO = custInfoService.getUniqueResult(custInfoVO);
//
////            if(returnMessageVO.getFlag()){
//                // 注册用户
//                Integer integer = acctService.userRegister(acctInfoVO);
//                jsonResponse.setRspBody(integer);
////            }else{
////                serviceResponse.setRetCode(returnMessageVO.getErrorCode());
////                serviceResponse.setRetMessage(returnMessageVO.getMessage());
////            }
//
//        }catch (Exception e){
//            LOG.error(e.getMessage());
//            e.printStackTrace();
//            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
//            return jsonResponse;
//        }
//
//        return jsonResponse;
//    }

    /**
     * 用户登录接口
     * @return
     * **/
    @RequestMapping("/login")
    public JsonResponse<AcctSession> userLogin(@RequestBody JsonRequest<AcctLogin> jsonRequest, HttpServletRequest request){
        JsonResponse<AcctSession> jsonResponse = new JsonResponse();
        try{

            if(type.equals("core")){
                //用户注册，查出用户 并 生成key 放到 cache 中
                ServiceResponse<AcctSession> response = acctService.userLogin(jsonRequest.getReqBody());

                //如果正常查出对象
                if(ServiceResponse.SUCCESS_CODE.equals(response.getRetCode())){

                    //把用户信息放到session中
                    AcctSession retContent = response.getRetContent();
                    retContent.setOrgId(response.getRetContent().getoInfoId());
                    FunctionManageVO functionManageVO=new FunctionManageVO();
                    functionManageVO.setAcctId(retContent.getAcctId());
                    List<FunctionManageVO> list = functionManageService.ListFunctionCode(functionManageVO);
                    List<String> permissions = new ArrayList<>();
                    list.forEach(per -> permissions.add(per.getFunctionCode()));
                    retContent.setPermissions(permissions);

                    HttpSession session = request.getSession();

                    String key = CookieUtil.getSessionId();

                    session.setAttribute(Md5Util.encrpt(key),retContent);

                    //并初始化 threadlocal
                    AssertContext.init(retContent);

                    jsonResponse.setRspBody(retContent);
                }else{
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    jsonResponse.setRetDesc(response.getRetMessage());
                }
            }else if (type.equals("report")){

                String url="http://"+authIp+"/auth/ac/login/dologin.action?username="+jsonRequest.getReqBody().getAcctId()+"&password="+jsonRequest.getReqBody().getPassword();
                //String url="http://192.168.1.100:7070/auth/ac/login/dologin.action?username=liq0416&password=admin123";
                HashMap map=new HashMap();
                JSONObject json=JSON.parseObject(HTTPUtil.postParams(url,null,map));
                String success=JSON.parseObject(json.getString("meta")).getString("success");
                if(success.equals("true") || success=="true"){
                    String token=JSON.parseObject(json.getString("data")).getString("token");
                    Claims claims2 = TokenUtil.parseTokenClaims(token);
                    AcctSession acctSession=new AcctSession();
                    JSONObject json2=JSON.parseObject(claims2.getSubject());
                    acctSession.setOrgId(json2.getString("orgId"));
                    acctSession.setAcctTitle(json2.getString("loginId"));
                    acctSession.setAcctType(Long.parseLong(json2.getString("userType")));
                    acctSession.setAcctId(Long.parseLong(json2.getString("userSid")));
                    acctSession.setReAcctId(json2.getString("loginId"));
                    acctSession.setToken(token);
                    //获取用户资源
                    String fun="http://"+authIp+"/auth/ac/ac-resource/getUserAssignedResourceTree.action?loginId="+jsonRequest.getReqBody().getAcctId()+"&appCode=ZDYBB";
                    JSONObject function=JSON.parseObject(HTTPUtil.postParams(fun,token,map));
                    String funsuccess=JSON.parseObject(function.getString("meta")).getString("success");
                    List<String> permissions = new ArrayList<>();
                    if(funsuccess.equals("true") || funsuccess=="true"){
                        JSONArray jsonObject1= function.getJSONArray("items");
                        for(int i=0;i<jsonObject1.size();i++){
                            permissions.add(JSON.parseObject(jsonObject1.getString(i)).getString("resId"));
                            if(JSON.parseObject(jsonObject1.getString(i)).getString("items")!=null){
                                permissions=permission(JSON.parseObject(jsonObject1.getString(i)),permissions);
                            }
                        }
                    }
                    acctSession.setName(JSON.parseObject(json.getString("data")).getString("usernameCn"));
                    acctSession.setPermissions(permissions);

                    //先写死角色
                    List<String> reRoleId = new ArrayList<>();
                    reRoleId.add("R1543383203888");
                    acctSession.setReRoleId(reRoleId);
                    HttpSession session = request.getSession();
                    String key = CookieUtil.getSessionId();
                    session.setAttribute(Md5Util.encrpt(key),acctSession);
                    AssertContext.init(acctSession);
                    jsonResponse.setRspBody(acctSession);
                }else{
                    jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                    jsonResponse.setRetDesc("登录失败");
                }
            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }


    private List<String> permission(JSONObject jsonObject,List<String> permissions){
        JSONArray jsonObject1= jsonObject.getJSONArray("items");
        if(jsonObject1!=null){
            for(int i=0;i<jsonObject1.size();i++){
                permissions.add(JSON.parseObject(jsonObject1.getString(i)).getString("resId"));
                if(JSON.parseObject(jsonObject1.getString(i)).getString("items")!=null){
                    permissions=permission(JSON.parseObject(jsonObject1.getString(i)),permissions);
                }
            }
        }
        return permissions;
    }

//    /**
//     * 从缓存中 拿 用户登录对象
//     * @return
//     */
//    @RequestMapping("/getCacheUser")
//    public JsonResponse<AcctSession> getUser(@RequestParam(value = "sessionId",required = false) String sessionId){
//        JsonResponse<AcctSession> jsonResponse = new JsonResponse<>();
//
//        LOG.info("sessionId = {}",sessionId);
//        try{
//            if(sessionId == null){
//                jsonResponse.setRetCode("0702005");
//            }else{
//
//                String key = CacheKeyConstant.ACCT_SESSION + Base64Util.decode(sessionId);
//
////                AcctSession acctSession = cacheService.getObject(key,AcctSession.class);
//                AcctSession acctSession =new AcctSession();
//                jsonResponse.setRspBody(acctSession);
//            }
//
//        }catch (Exception e){
//            LOG.error(e.getMessage());
//            e.printStackTrace();
//            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
//            return jsonResponse;
//        }
//
//        return jsonResponse;
//    }
//
//
    /**
     * 从缓存中 删除用户会话信息
     * @return
     */
    @RequestMapping("/delCacheUser")
    public JsonResponse<Boolean> delUser(HttpServletRequest request){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();

        try{
            String sessionId = CookieUtil.getSessionId();

            if(sessionId == null){
                jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
                jsonResponse.setRetDesc("当前用户会话不存在");
            }else{
//                CookieUtil.removeCookie();
                //清空cookie
                request.getSession().removeAttribute(Md5Util.encrpt(sessionId));

                jsonResponse.setRspBody(true);
            }

        }catch (Exception e){
            LOG.error(e.getMessage());
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }

        return jsonResponse;
    }
//
//
//    /**
//     * 获得用户信息
//     * @return
//     */
//    @RequestMapping("/getAcct")
//    public JsonResponse<AcctInfoVO> getAcct(@RequestParam(value = "acctName",required = false) String acctName){
//        JsonResponse<AcctInfoVO> jsonResponse = new JsonResponse<>();
//
//        try{
//            if(acctName == null){
//                jsonResponse.setRetCode("0702005");
//            }else {
//
//                AcctInfoVO response = acctService.getAcct(acctName);
//
//                //如果正常查出对象
//                jsonResponse.setRspBody(response);
//            }
//
//        }catch (Exception e){
//            LOG.error(e.getMessage());
//            e.printStackTrace();
//            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
//            return jsonResponse;
//        }
//
//        return jsonResponse;
//    }
}
