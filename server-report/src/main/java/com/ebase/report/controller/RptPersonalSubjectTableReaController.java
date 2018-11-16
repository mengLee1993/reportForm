package com.ebase.report.controller;

import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.RptPersonalSubjectTableReaService;
import com.ebase.report.vo.RptPersonalSubjectTableReaVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**数据表关联关系
 * controller :RptPersonalSubjectTableRea
 * @author 
 * @date 2018-10-25
 */
 
@RestController
@RequestMapping("/report")
public class RptPersonalSubjectTableReaController {
	private static Logger logger = LoggerFactory.getLogger(RptPersonalSubjectTableReaController.class);

	@Autowired
    private RptPersonalSubjectTableReaService rptPersonalSubjectTableReaService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTableRea/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersonalSubjectTableReaVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableReaVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableReaService.insertSelective(vo);
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
	@RequestMapping("/rptPersonalSubjectTableRea/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersonalSubjectTableReaVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableReaVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableReaService.updateByPrimaryKeySelective(vo);
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
	@RequestMapping("/rptPersonalSubjectTableRea/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersonalSubjectTableReaVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableReaVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalSubjectTableReaService.deleteByPrimaryKey(vo.getReaId());
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
	@RequestMapping("/rptPersonalSubjectTableRea/getdetails")
	public JsonResponse<RptPersonalSubjectTableReaVO> getDetails(@RequestBody JsonRequest<RptPersonalSubjectTableReaVO> jsonRequest) {
		JsonResponse<RptPersonalSubjectTableReaVO> JsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalSubjectTableReaVO vo = jsonRequest.getReqBody();
		RptPersonalSubjectTableReaVO rptPersonalSubjectTableReaVO = rptPersonalSubjectTableReaService.getByPrimaryKey(vo.getReaId());
		JsonResponse.setRspBody(rptPersonalSubjectTableReaVO);
		return JsonResponse;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalSubjectTableRea/findpageresult")
	public JsonResponse<PageInfo<RptPersonalSubjectTableReaVO>> findPageResult(@RequestBody JsonRequest<RptPersonalSubjectTableReaVO> jsonRequest) {
		JsonResponse<PageInfo<RptPersonalSubjectTableReaVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptPersonalSubjectTableReaVO vo = jsonRequest.getReqBody();
			PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
			List<RptPersonalSubjectTableReaVO> acctOrgSysVOs = rptPersonalSubjectTableReaService.findSelective(vo);
			PageInfo<RptPersonalSubjectTableReaVO> pages = new PageInfo(acctOrgSysVOs);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
	/*@RequestMapping("/rptPersonalSubjectTableRea/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<RptPersonalSubjectTableReaVO>> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
		List<RptPersonalSubjectTableReaVO> rptPersonalSubjectTableReaVOs = jsonRequest.getReqBody();
		if (CollectionUtils.isEmpty(rptPersonalSubjectTableReaVOs)) {
			JsonResponse.setRetCode("");
			return JsonResponse;
		}
		Integer result =  rptPersonalSubjectTableReaService.keep(rptPersonalSubjectTableReaVOs);
		if(result > 0)
			JsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}*/
    
}