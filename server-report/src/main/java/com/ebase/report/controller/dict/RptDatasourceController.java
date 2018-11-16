package com.ebase.report.controller.dict;

import com.ebase.report.controller.IndexController;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.service.RptDatasourceService;
import com.ebase.report.vo.RptDatasourceVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据源
 * @Auther: lujiawei
 */
@RestController
@RequestMapping("/report")
public class RptDatasourceController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RptDatasourceService rptDsService;

    /**
     * @param:
     * @return:
     * @description:  获取数据库类型
     * @author: lirunze
     * @Date: 2018/11/1
     */
    @PostMapping("/datasource/database/type")
    public JsonResponse<List<RptDatasourceVO>> queryDataBaseType(){
        JsonResponse<List<RptDatasourceVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("获取数据库类型参数 = {}");
            List<RptDatasourceVO> list = rptDsService.queryDataBaseType();
            jsonResponse.setRspBody(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  获取数据连接池类型
     * @author: lirunze
     * @Date: 2018/11/1
     */
    @PostMapping("/datasource/connpool/type")
    public JsonResponse<List<RptDatasourceVO>> queryConnPoolType(){
        JsonResponse<List<RptDatasourceVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("获取数据连接池类型参数 = {}");
            List<RptDatasourceVO> list = rptDsService.queryConnPoolType();
            jsonResponse.setRspBody(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  测试数据库连接
     * @author: lirunze
     * @Date: 2018/11/1
     */
    @PostMapping("/datasource/testConn")
    public JsonResponse<Boolean> testConn(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        try {
            logger.info("测试数据库连接参数 = {}", JsonUtil.toJson(jsonRequest));
            Boolean flag = rptDsService.testConn(jsonRequest.getReqBody());
            jsonResponse.setRspBody(flag);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            e.printStackTrace();
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  查看详情
     * @author: lirunze
     * @Date: 2018/11/1
     */
    @PostMapping("/datasource/detail")
    public JsonResponse<RptDatasourceVO> getDetail(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        JsonResponse<RptDatasourceVO> jsonResponse = new JsonResponse<>();
        try {
            logger.info("查看详情参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDatasourceVO reqBody = jsonRequest.getReqBody();
            RptDatasourceVO vo = rptDsService.selectByPrimaryKey(reqBody.getDatasourceId());
            jsonResponse.setRspBody(vo);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  获取所有数据源
     * @author: lirunze
     * @Date: 2018/11/2
     */
    @RequestMapping(value = "/datasource/all" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDatasourceVO>> queryAll(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        JsonResponse<PageInfo<RptDatasourceVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDatasourceVO vo = jsonRequest.getReqBody();
            List<RptDatasourceVO> acctOrgSysVOs = rptDsService.selectAll();
            PageInfo<RptDatasourceVO> pages = new PageInfo(acctOrgSysVOs);
            jsonResponse.setRspBody(pages);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * @param:
     * @return:
     * @description:  获取所有数据源 no resultData
     * @author: lirunze
     * @Date: 2018/11/2
     */
    @RequestMapping(value = "/datasource/down/all" , method = RequestMethod.POST)
    public JsonResponse<List<RptDatasourceVO>> queryDownAll(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        JsonResponse<List<RptDatasourceVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDatasourceVO vo = jsonRequest.getReqBody();
            List<RptDatasourceVO> acctOrgSysVOs = rptDsService.selectAll();
            jsonResponse.setRspBody(acctOrgSysVOs);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 分页
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/datasource/list" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDatasourceVO>> queryForList(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        JsonResponse<PageInfo<RptDatasourceVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDatasourceVO vo = jsonRequest.getReqBody();
            PageInfo<RptDatasourceVO> pages = rptDsService.queryByPage(vo);
            jsonResponse.setRspBody(pages);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }


    /**
     * 新增
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/datasource/add" , method = RequestMethod.POST)
    public JsonResponse<Integer> insert(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){
        logger.info("新增 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDatasourceVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rptDsService.add(vo);
            if (sR.getRetContent() <= 0) {
                jsonResponse.setRetCode(sR.getRetCode());
                return jsonResponse;
            }

            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            logger.error("新增失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("新增失败");
        }
        return jR;
    }

    /**
     * 删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/datasource/remove" , method = RequestMethod.POST)
    public JsonResponse<Integer> deleteById(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){

        logger.info("删除 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDatasourceVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rptDsService.changeStatus(vo);
            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            logger.error("删除失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("删除失败");
        }
        return jR;
    }

    /**
     * 数据源更新
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/datasource/update" , method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<RptDatasourceVO> jsonRequest){

        logger.info("数据源更新 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            RptDatasourceVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rptDsService.updateByPrimaryKeySelective(vo);
            Integer num = sR.getRetContent();
            if (num <= 0) {
                jsonResponse.setRetCode(sR.getRetCode());
                return jsonResponse;
            }
            jR.setRspBody(num);
        }catch (Exception e){
            logger.error("更新失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("更新失败");
        }
        return jR;
    }
}
