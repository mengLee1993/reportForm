package com.ebase.report.controller.dict;

import com.ebase.report.common.StateUtil;
import com.ebase.report.controller.IndexController;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.model.RptDataDict;
import com.ebase.report.service.RptDataFieldService;
import com.ebase.report.vo.RptDataFieldVO;
import com.github.pagehelper.PageHelper;
import jdk.nashorn.internal.ir.ReturnNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表字段表
 */
@RestController
@RequestMapping("/report")
public class RptDataFieldController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RptDataFieldService rdfService;

    @Value("${metadataCount}")
    private Integer metadataCount;

    /**
     * 抽取元数据列表
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataDict/extract/list" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataFieldVO>> extractMetadataList(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){
        JsonResponse<PageInfo<RptDataFieldVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("抽取元数据列表 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataFieldVO vo = jsonRequest.getReqBody();
            vo.setPageSize(100);
//            List<RptDataFieldVO> acctOrgSysVOs = rdfService.extractMetadata(vo);
//            PageInfo<RptDataFieldVO> pages = new PageInfo(acctOrgSysVOs);
//            jsonResponse.setRspBody(pages);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }
    /**
     * 抽取元数据
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataDict/extract" , method = RequestMethod.POST)
    public JsonResponse<Boolean> extractMetadata(@RequestBody JsonRequest<List<RptDataFieldVO>> jsonRequest){
        JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
        try {
            logger.info("抽取元数据 参数 = {}", JsonUtil.toJson(jsonRequest));
            List<RptDataFieldVO> list = jsonRequest.getReqBody();
            Boolean flag = rdfService.extractMetadata(list);
            jsonResponse.setRspBody(flag);
        } catch (Exception e) {
            logger.error(e.getMessage() , e);
            e.printStackTrace();
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            return jsonResponse;
        }
        return jsonResponse;
    }




    /**
     * 抽取元数据 实时的 升级版本
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataDict/extract/realTime" , method = RequestMethod.POST)
    public JsonResponse<Object> extractRealTimeMetadata(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){
        JsonResponse<Object> jsonResponse = new JsonResponse<>();
//        jsonResponse.setRetCode("");
        try {
            logger.info("抽取元数据 参数 = {}", JsonUtil.toJson(jsonRequest));

            RptDataFieldVO reqBody = jsonRequest.getReqBody();

            if(reqBody == null || reqBody.getFieldId() == null){
                jsonResponse.setRspBody("参数不正确");
                return jsonResponse;
            }
            Long fieldId = reqBody.getFieldId();
            Integer count = rdfService.extractCount(fieldId);

            if(count > metadataCount){
                jsonResponse.setRspBody("源数据量有"+ count +"个，不建议抽取");
                return jsonResponse;
            }else if(count == 0){
                jsonResponse.setRspBody("该维度没有源数据");
                return jsonResponse;
            }

            List<RptDataDict> list = rdfService.extractRealTimeMetadata(fieldId);

            jsonResponse.setRetCode(JsonResponse.SUCCESS);
            jsonResponse.setRetDesc("成功！");
            jsonResponse.setRspBody(list);

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
    @RequestMapping(value = "/dataField/list" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataFieldVO>> queryForList(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){
        JsonResponse<PageInfo<RptDataFieldVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataFieldVO vo = jsonRequest.getReqBody();
            List<RptDataFieldVO> list = new ArrayList();
            if(vo.getTableId() == null){
                PageInfo<RptDataFieldVO> pages = new PageInfo(list);
                jsonResponse.setRspBody(pages);
                return jsonResponse;
            }
            list = rdfService.queryList(vo);
            PageInfo<RptDataFieldVO> pages = new PageInfo(list);
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
     * 元数据分页
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataField/metadata" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataFieldVO>> queryMetadataList(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){
        JsonResponse<PageInfo<RptDataFieldVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataFieldVO vo = jsonRequest.getReqBody();
            PageHelper.startPage(vo.getPageNum(), 10);
            List<RptDataFieldVO> acctOrgSysVOs = rdfService.queryMetadataList(vo);
            PageInfo<RptDataFieldVO> pages = new PageInfo(acctOrgSysVOs);
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
    @RequestMapping(value = "/dataField/add" , method = RequestMethod.POST)
    public JsonResponse<Integer> insert(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){

        logger.info("新增");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataFieldVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rdfService.add(vo);
            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            e.printStackTrace();
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
    @RequestMapping(value = "/dataField/remove" , method = RequestMethod.POST)
    public JsonResponse<Integer> deleteById(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){

        logger.info("删除");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataFieldVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rdfService.removeByPrimaryKey(vo);
            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            e.printStackTrace();
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("删除失败");
        }
        return jR;
    }

    /**
     * 批量删除
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataField/removeByIds" , method = RequestMethod.POST)
    public JsonResponse<Integer> deleteByIds(@RequestBody JsonRequest<RptDataFieldVO> jsonRequest){

        logger.info("批量刪除");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataFieldVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rdfService.removeByIds(vo);
            //返回1 删除成功
            jR.setRspBody(StateUtil.SUCCESS_DELETE);
        }catch (Exception e){
            e.printStackTrace();
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("删除失败");
            //返回0 刪除失败
            jR.setRspBody(StateUtil.FAILURE_DELETE);
        }
        return jR;
    }

    /**
     * 更新
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataField/update" , method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<List<RptDataFieldVO>> jsonRequest){

        logger.info("更新");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            List<RptDataFieldVO> list = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rdfService.updateBatch(list);
            Integer num = sR.getRetContent();
            jR.setRspBody(num);
        }catch (Exception e){
            logger.error("更新失败", e);
            jR.setRetCode(JsonResponse.SYS_EXCEPTION);
            jR.setRetDesc("更新失败");
        }
        return jR;
    }
}
