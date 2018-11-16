package com.ebase.report.controller;

import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.RptPersonalSubjectService;
import com.ebase.report.vo.RptPersonalSubjectVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :RptPersonalSubject
 * @author
 * @date 2018-10-25
 */
 
@RestController
@RequestMapping("/report")
public class RptPersonalSubjectController {
	private static Logger logger = LoggerFactory.getLogger(RptPersonalSubjectController.class);

	@Autowired
    private RptPersonalSubjectService rptPersonalSubjectService;

	/**
	 * 分页查询
	 *
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubject/findpageresult")
	public JsonResponse<PageInfo<RptPersonalSubjectVO>> findPageResult(@RequestBody JsonRequest<RptPersonalSubjectVO> jsonRequest) {
		JsonResponse<PageInfo<RptPersonalSubjectVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptPersonalSubjectVO vo = jsonRequest.getReqBody();
			PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
			List<RptPersonalSubjectVO> acctOrgSysVOs = rptPersonalSubjectService.findSelective(vo);
			PageInfo<RptPersonalSubjectVO> pages = new PageInfo(acctOrgSysVOs);
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
	 * 保存
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubject/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersonalSubjectVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectService.insertSelective(vo);
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubject/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersonalSubjectVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectService.updateByPrimaryKeySelective(vo);
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubject/renove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersonalSubjectVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectService.deleteByPrimaryKey(vo.getPersonalSubjectId());
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubject/getdetails")
	public JsonResponse<RptPersonalSubjectVO> getDetails(@RequestBody JsonRequest<RptPersonalSubjectVO> jsonRequest) {
		JsonResponse<RptPersonalSubjectVO> JsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectVO vo = jsonRequest.getReqBody();
		RptPersonalSubjectVO rptPersonalSubjectVO = rptPersonalSubjectService.getByPrimaryKey(vo.getPersonalSubjectId());
		JsonResponse.setRspBody(rptPersonalSubjectVO);
		return JsonResponse;
	}
	

	/**
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param jsonRequest
	 * @return
	 */
	/*@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<RptPersonalSubjectVO>> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
		List<RptPersonalSubjectVO> rptPersonalSubjectVOs = jsonRequest.getReqBody();
		if (CollectionUtils.isEmpty(rptPersonalSubjectVOs)) {
			JsonResponse.setRetCode("");
			return JsonResponse;
		}
		Integer result =  rptPersonalSubjectService.keep(rptPersonalSubjectVOs);
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}*/
    
}