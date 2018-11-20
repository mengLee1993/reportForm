package com.ebase.report.controller;

import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.model.AnalysisShareBody;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.service.RptPersonalAnalysisService;
import com.ebase.report.vo.RptPersonalAnalysisVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :RptPersonalAnalysis
 * @author 
 * @date 2018-11-1
 */
 
@RestController
@RequestMapping("/report")
public class RptPersonalAnalysisController {
	private static Logger logger = LoggerFactory.getLogger(RptPersonalAnalysisController.class);

	@Autowired
    private RptPersonalAnalysisService rptPersonalAnalysisService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalAnalysis/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalAnalysisVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalAnalysisService.insertSelective(vo);
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
	@RequestMapping("/rptPersonalAnalysis/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalAnalysisVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalAnalysisService.updateByPrimaryKeySelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}
	
	/**
	 * 自定义报表 - 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalAnalysis/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalAnalysisVO vo = jsonRequest.getReqBody();
		Integer result = rptPersonalAnalysisService.deleteByPrimaryKey(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}


	/**
	 * 查看分享列表
	 */
	@RequestMapping("/listAnalysisShareBody")
	public JsonResponse<PageDTO<AnalysisShareBody>> listAnalysisShareBody(@RequestBody JsonRequest<AnalysisShareBody> jsonRequest){

		JsonResponse<PageDTO<AnalysisShareBody>> jsonResponse = new JsonResponse<>();

		try{
			AnalysisShareBody analysisShareBody = jsonRequest.getReqBody();

			PageDTO<AnalysisShareBody> pageDTO = rptPersonalAnalysisService.listAnalysisShareBody(analysisShareBody);

			jsonResponse.setRspBody(pageDTO);
		}catch (Exception e){
			logger.error("error = {}",e);
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}

		return jsonResponse;
	}


	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalAnalysis/getdetails")
	public JsonResponse<RptPersonalAnalysisVO> getDetails(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<RptPersonalAnalysisVO> jsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalAnalysisVO vo = jsonRequest.getReqBody();
		RptPersonalAnalysisVO rptPersonalAnalysisVO = rptPersonalAnalysisService.getByPrimaryKey(vo.getPersonalAnalysisId());
		jsonResponse.setRspBody(rptPersonalAnalysisVO);
		return jsonResponse;
	}
	
	/**
	 * 分页查询
	 * 自定义报表 - 查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersonalAnalysis/findpageresult")
	public JsonResponse<PageDTO<RptPersonalAnalysisVO>> findPageResult(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<PageDTO<RptPersonalAnalysisVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			RptPersonalAnalysisVO vo = jsonRequest.getReqBody();

			Long acctId = AssertContext.getAcctId();
			if(acctId == null){
				acctId = 1L;
			}
			vo.setUserId(acctId.toString());

			PageDTO<RptPersonalAnalysisVO> acctOrgSysVOs = rptPersonalAnalysisService.findSelective(vo);
//			PageInfo<RptPersonalAnalysisVO> pages = new PageInfo(acctOrgSysVOs);
			jsonResponse.setRspBody(acctOrgSysVOs);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
		}
		return jsonResponse;
	}

	/**
	 * 根据当前用户查询个人的主题
	 *
	 * @param jsonRequest userId
	 * @return
	 */
	@RequestMapping("/rptPersonalAnalysis/getDetailsByUserId")
	public JsonResponse<List<RptPersonalAnalysisVO>> getDetailsByUserId(@RequestBody JsonRequest<RptPersonalAnalysisVO> jsonRequest) {
		JsonResponse<List<RptPersonalAnalysisVO>> jsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersonalAnalysisVO vo = jsonRequest.getReqBody();
		List<RptPersonalAnalysisVO> rptPersonalAnalysisVO = rptPersonalAnalysisService.getByUserId(vo.getUserId());
		jsonResponse.setRspBody(rptPersonalAnalysisVO);
		return jsonResponse;
	}



	
}