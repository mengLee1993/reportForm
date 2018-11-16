package com.ebase.report.controller.dict;

import com.ebase.report.controller.IndexController;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.service.RptDataDictService;
import com.ebase.report.vo.RptDataDictVO;
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
 * 数据字典
 * @Auther: lujiawei
 */
@RestController
@RequestMapping("/report")
public class RptDataDictController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RptDataDictService rddService;

    /**
     * 通过字段ID进行查询
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataDict/list" , method = RequestMethod.POST)
    public JsonResponse<PageInfo<RptDataDictVO>> queryForList(@RequestBody JsonRequest<RptDataDictVO> jsonRequest){
        JsonResponse<PageInfo<RptDataDictVO>> jsonResponse = new JsonResponse<>();
        try {
            logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
            RptDataDictVO vo = jsonRequest.getReqBody();
            PageHelper.startPage(vo.getPageNum(), 100);
            List<RptDataDictVO> acctOrgSysVOs = rddService.queryForList(vo);
            PageInfo<RptDataDictVO> pages = new PageInfo(acctOrgSysVOs);
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
    @RequestMapping(value = "/dataDict/add" , method = RequestMethod.POST)
    public JsonResponse<Integer> insert(@RequestBody JsonRequest<RptDataDictVO> jsonRequest){

        logger.info("新增");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataDictVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rddService.add(vo);
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
    @RequestMapping(value = "/dataDict/remove" , method = RequestMethod.POST)
    public JsonResponse<Integer> removeById(@RequestBody JsonRequest<RptDataDictVO> jsonRequest){

        logger.info("删除");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{

            RptDataDictVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rddService.removeByPrimaryKey(vo);
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
     * 更新
     * @param jsonRequest
     * @return
     */
    @RequestMapping(value = "/dataDict/update" , method = RequestMethod.POST)
    public JsonResponse<Integer> update(@RequestBody JsonRequest<RptDataDictVO> jsonRequest){

        logger.info("更新");
        JsonResponse<Integer> jsonResponse = new JsonResponse<>();
        if(jsonRequest==null || jsonRequest.getReqBody() == null){
            jsonResponse.setRetDesc("非法操作");;
            return jsonResponse;
        }
        JsonResponse<Integer> jR = new JsonResponse<>();
        try{
            RptDataDictVO vo = jsonRequest.getReqBody();
            ServiceResponse<Integer> sR = rddService.updateByPrimaryKey(vo);
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
