package com.ebase.report.controller;

import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.RptAnalyseLogService;
import com.ebase.report.vo.RptAnalyseLogVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :RptAnalyseLog
 * @author 
 * @date 2018-11-1
 */
 
@RestController
@RequestMapping("/report")
public class RptAnalyseLogController {
	private static Logger logger = LoggerFactory.getLogger(RptAnalyseLogController.class);

	@Autowired
    private RptAnalyseLogService rptAnalyseLogService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptAnalyseLog/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptAnalyseLogVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptAnalyseLogVO vo = jsonRequest.getReqBody();
		Integer result = rptAnalyseLogService.insertSelective(vo);
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
	@RequestMapping("/rptAnalyseLog/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptAnalyseLogVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptAnalyseLogVO vo = jsonRequest.getReqBody();
		Integer result = rptAnalyseLogService.updateByPrimaryKeySelective(vo);
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
	@RequestMapping("/rptAnalyseLog/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptAnalyseLogVO> jsonRequest) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptAnalyseLogVO vo = jsonRequest.getReqBody();
		Integer result = rptAnalyseLogService.deleteByPrimaryKey(vo.getLogId());
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
	@RequestMapping("/rptAnalyseLog/getdetails")
	public JsonResponse<RptAnalyseLogVO> getDetails(@RequestBody JsonRequest<RptAnalyseLogVO> jsonRequest) {
		JsonResponse<RptAnalyseLogVO> JsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptAnalyseLogVO vo = jsonRequest.getReqBody();
		RptAnalyseLogVO rptAnalyseLogVO = rptAnalyseLogService.getByPrimaryKey(vo.getLogId());
		JsonResponse.setRspBody(rptAnalyseLogVO);
		return JsonResponse;
	}
	
	/**
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptAnalyseLog/findpageresult")
	public JsonResponse<PageDTO<RptAnalyseLogVO>> findPageResult(@RequestBody JsonRequest<RptAnalyseLogVO> jsonRequest) {
		JsonResponse<PageDTO<RptAnalyseLogVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptAnalyseLogVO vo = jsonRequest.getReqBody();

			PageDTO<RptAnalyseLogVO> pages = rptAnalyseLogService.findSelective(vo);

			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

//	//分析日志 -- '
//	@RequestMapping("/listAnalyseLog")
//	public JsonResponse<PageDTO<RptAnalyseLogBody>> listAnalyseLog(@RequestBody JsonRequest<RptAnalyseLogBody> jsonRequest){
//		JsonResponse<PageDTO<RptAnalyseLogBody>> jsonResponse = new JsonResponse<>();
//		try {
//			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
//			RptAnalyseLogBody vo = jsonRequest.getReqBody();
//
//			PageDTO<RptAnalyseLogBody> pages = rptAnalyseLogService.listAnalyseLog(vo);
//
//			jsonResponse.setRspBody(pages);
//		} catch (Exception e) {
//			logger.error(e.getMessage() , e);
//			e.printStackTrace();
//			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
//		}
//		return jsonResponse;
//	}
    
}