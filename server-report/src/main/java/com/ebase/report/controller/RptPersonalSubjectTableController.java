package com.ebase.report.controller;


import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.RptPersonalSubjectTableService;
import com.ebase.report.vo.RptPersonalSubjectTableVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**个人主题数据表
 * controller :RptPersonalSubjectTable
 * @author 
 * @date 2018-10-25
 */
 
@RestController
@RequestMapping("/report")
public class RptPersonalSubjectTableController {
	private static Logger logger = LoggerFactory.getLogger(RptPersonalSubjectTableController.class);

	@Autowired
    private RptPersonalSubjectTableService rptPersonalSubjectTableService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTable/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersonalSubjectTableVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableService.insertSelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTable/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersonalSubjectTableVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableService.updateByPrimaryKeySelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTable/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersonalSubjectTableVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableService.deleteByPrimaryKey(vo.getPersionalSubjectTableId());
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTable/getdetails")
	public JsonResponse<RptPersonalSubjectTableVO> getDetails(@RequestBody JsonRequest<RptPersonalSubjectTableVO> jsonRequest) {
		JsonResponse<RptPersonalSubjectTableVO> jsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableVO vo = jsonRequest.getReqBody();
		RptPersonalSubjectTableVO rptPersonalSubjectTableVO = rptPersonalSubjectTableService.getByPrimaryKey(vo.getPersionalSubjectTableId());
		jsonResponse.setRspBody(rptPersonalSubjectTableVO);
		return jsonResponse;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTable/findpageresult")
	public JsonResponse<PageInfo<RptPersonalSubjectTableVO>> findPageResult(@RequestBody JsonRequest<RptPersonalSubjectTableVO> jsonRequest) {
		JsonResponse<PageInfo<RptPersonalSubjectTableVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptPersonalSubjectTableVO vo = jsonRequest.getReqBody();
			PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
			List<RptPersonalSubjectTableVO> acctOrgSysVOs = rptPersonalSubjectTableService.findSelective(vo);
			PageInfo<RptPersonalSubjectTableVO> pages = new PageInfo(acctOrgSysVOs);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}
/*
	*//**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 *//*
	@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<RptPersonalSubjectTableVO>> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
		List<RptPersonalSubjectTableVO> rptPersonalSubjectTableVOs = jsonRequest.getReqBody();
		if (CollectionUtils.isEmpty(rptPersonalSubjectTableVOs)) {
			JsonResponse.setRetCode("");
			return JsonResponse;
		}
		Integer result =  rptPersonalSubjectTableService.keep(rptPersonalSubjectTableVOs);
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}*/
    
}