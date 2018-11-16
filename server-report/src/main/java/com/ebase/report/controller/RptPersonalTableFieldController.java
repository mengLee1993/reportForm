package com.ebase.report.controller;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.RptPersonalTableFieldService;
import com.ebase.report.vo.RptPersonalTableFieldVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**个人数据表字段
 * controller :RptPersonalTableField
 * @author 
 * @date 2018-10-25
 */
 
@RestController
@RequestMapping("/report")
public class RptPersonalTableFieldController {
	private static Logger logger = LoggerFactory.getLogger(RptPersonalTableFieldController.class);

	@Autowired
    private RptPersonalTableFieldService rptPersonalTableFieldService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalTableField/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersonalTableFieldVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalTableFieldVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalTableFieldService.insertSelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else{
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return jsonResponse;
	}
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalTableField/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersonalTableFieldVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalTableFieldVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalTableFieldService.updateByPrimaryKeySelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		return jsonResponse;
	}
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalTableField/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersonalTableFieldVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalTableFieldVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalTableFieldService.deleteByPrimaryKey(vo.getPersionalTableFieldId());
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
		jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		return jsonResponse;
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalTableField/getdetails")
	public JsonResponse<RptPersonalTableFieldVO> getDetails(@RequestBody JsonRequest<RptPersonalTableFieldVO> jsonRequest) {
		JsonResponse<RptPersonalTableFieldVO> JsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalTableFieldVO vo = jsonRequest.getReqBody();
		RptPersonalTableFieldVO rptPersonalTableFieldVO = rptPersonalTableFieldService.getByPrimaryKey(vo.getPersionalTableFieldId());
		JsonResponse.setRspBody(rptPersonalTableFieldVO);
		return JsonResponse;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("rptPersonalTableField/findpageresult")
	public JsonResponse<PageInfo<RptPersonalTableFieldVO>> findPageResult(@RequestBody JsonRequest<RptPersonalTableFieldVO> jsonRequest) {
		JsonResponse<PageInfo<RptPersonalTableFieldVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptPersonalTableFieldVO vo = jsonRequest.getReqBody();
			PageHelper.startPage(1, 10);
			List<RptPersonalTableFieldVO> acctOrgSysVOs = rptPersonalTableFieldService.findSelective(vo);
			PageInfo<RptPersonalTableFieldVO> pages = new PageInfo(acctOrgSysVOs);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}
	
	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	/*@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<RptPersonalTableFieldVO>> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
		List<RptPersonalTableFieldVO> rptPersonalTableFieldVOs = jsonRequest.getReqBody();
		if (CollectionUtils.isEmpty(rptPersonalTableFieldVOs)) {
			JsonResponse.setRetCode("");
			return JsonResponse;
		}
		Integer result =  rptPersonalTableFieldService.keep(rptPersonalTableFieldVOs);
		if(result > 0)
			JsonResponse.setRetContent(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}*/
    
}