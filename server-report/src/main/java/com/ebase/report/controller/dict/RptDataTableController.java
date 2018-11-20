package com.ebase.report.controller.dict;

import com.ebase.report.common.StateUtil;
import com.ebase.report.controller.IndexController;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.service.RptDataTableService;
import com.ebase.report.vo.RptDataTableVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 某个数据库中所有的表
 * @Auther: lujiawei
 */
@RestController
@RequestMapping("/report")
public class RptDataTableController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RptDataTableService dataTableService;

    /**
     * @param:
     * @return:
     * @description:  查看主题表
     * @author: lirunze
     * @Date: 2018/11/2
     */
    @RequestMapping(value = "/dataTable/themeTable" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataTableVO>> queryThemeTable(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){
        JsonResponse<PageInfo<RptDataTableVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("查看主题表 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataTableVO vo = jsonRequest.getReqBody();
            PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
            List<RptDataTableVO> acctOrgSysVOs = dataTableService.queryThemeTable(vo);
            PageInfo<RptDataTableVO> pages = new PageInfo(acctOrgSysVOs);
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
     * @description:  查看主题表
     * @author: lirunze
     * @Date: 2018/11/2
     */
    @RequestMapping(value = "/dataTable/all" , method = RequestMethod.POST)
    public JsonResponse<List<RptDataTableVO>> queryAllThemeTable(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){
        JsonResponse<List<RptDataTableVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("查看主题表 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataTableVO vo = jsonRequest.getReqBody();
            List<RptDataTableVO> acctOrgSysVOs = dataTableService.queryThemeTable(vo);
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
     * @param:
     * @return:
     * @description:  查看主题表详情
     * @author: lirunze
     * @Date: 2018/11/2
     */
    @RequestMapping(value = "/dataTable/detail" , method = RequestMethod.POST)
    public JsonResponse<RptDataTableVO> getDetail(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){
        JsonResponse<RptDataTableVO> jsonResponse = new JsonResponse<>();
        try {
            logger.info("查看主题表详情 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataTableVO vo = jsonRequest.getReqBody();
            RptDataTableVO tableVO = dataTableService.getDetailByTableId(vo);
            jsonResponse.setRspBody(tableVO);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }

    /**
     * 根据选择数据库查询出数据库下所有的表
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataTable/queryAllTableForDataSource" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataTableVO>> queryAll(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){
        JsonResponse<PageInfo<RptDataTableVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataTableVO vo = jsonRequest.getReqBody();
            List<RptDataTableVO> acctOrgSysVOs = dataTableService.queryAllByDataSourceName(vo);
            PageInfo<RptDataTableVO> pages = new PageInfo(acctOrgSysVOs);
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
     * 业务表保存为主题表
     * @param jsonRequest,
     * 数据源ID datasourceId,数据表名称 tableName
     * @return
     */
    @RequestMapping(value = "/dataTable/saveInDataTbleAndFieldTable" , method = RequestMethod.POST)
    public JsonResponse<Integer> save(@RequestBody JsonRequest<List<RptDataTableVO>> jsonRequest){

        logger.info("业务表保存为主题表参数 = {}", JsonUtil.toJson(jsonRequest));
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            List<RptDataTableVO> vos = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = dataTableService.insertBusinessTableToThemo(vos);
            jR.setRspBody(StateUtil.SUCCESS_INSERT);
        }catch (Exception e){
            e.printStackTrace();
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("新增失败");
            jR.setRspBody(StateUtil.FAILURE_INSERT);
        }
        return jR;
    }

    /**
     * 新增
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataTable/add" , method = RequestMethod.POST)
    public JsonResponse<Integer> insert(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){
        logger.info("新增");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            RptDataTableVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = dataTableService.add(vo);
            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            logger.error("新增失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("新增失败");
        }
        return jR;
    }

    /**删除,假删除改变removeState-->1
     * 界面:数据字典-->数据字典管理-->主题表-->删除操作
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataTable/remove" , method = RequestMethod.POST)
    public JsonResponse<Integer> deleteById(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){

        logger.info("删除");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataTableVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = dataTableService.removeByPrimaryKey(vo.getTableId());
            Integer num = sR.getRetContent();
            if(num == null || num == 0){
               jR.setRspBody(0);
            }else{
                jR.setRspBody(num);
            }
        }catch (Exception e){
            logger.error("删除失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("删除失败");
        }
        return jR;
    }

    /**
     * 主题表更新
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataTable/update" , method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<RptDataTableVO> jsonRequest){

        logger.info("主题表更新");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            RptDataTableVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = dataTableService.updateByPrimaryKeySelective(vo);
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
