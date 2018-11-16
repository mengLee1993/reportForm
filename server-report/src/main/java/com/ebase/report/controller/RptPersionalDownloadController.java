package com.ebase.report.controller;

import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.model.RptPersionalDownload;
import com.ebase.report.service.RptPersionalDownloadService;
import com.ebase.report.vo.RptPersionalDownloadVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :RptPersionalDownload
 * @author 
 * @date 2018-11-1
 */
 
@RestController
@RequestMapping("/report")
public class RptPersionalDownloadController {
	private static Logger logger = LoggerFactory.getLogger(RptPersionalDownloadController.class);

	@Autowired
    private RptPersionalDownloadService rptPersionalDownloadService;
    
    /**
	 * 保存
	 * 
	 * @param JsonResponse
	 * @return
	 */
	@RequestMapping("/rptPersionalDownload/add")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<RptPersionalDownloadVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
		try {

		RptPersionalDownloadVO vo = jsonRequest.getReqBody();
		Integer result = rptPersionalDownloadService.insertSelective(vo);
		jsonResponse.setRspBody(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	/**
	 * 更新
	 * 
	 * @param JsonResponse
	 * @return
	 */
	@RequestMapping("/rptPersionalDownload/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<RptPersionalDownloadVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersionalDownloadVO vo = jsonRequest.getReqBody();
		Integer result = rptPersionalDownloadService.updateByPrimaryKeySelective(vo);
		if(result > 0)
			jsonResponse.setRspBody(result);
		else
			throw new BusinessException("204");
		return jsonResponse;
	}
	
	/**
	 * 删除
	 * 
	 * @param JsonResponse
	 * @return
	 */
	@RequestMapping("/rptPersionalDownload/remove")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<RptPersionalDownloadVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersionalDownloadVO vo = jsonRequest.getReqBody();
		try {
		Integer result = rptPersionalDownloadService.deleteByPrimaryKey(vo.getDownloadId());
			jsonResponse.setRspBody(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return  jsonResponse;
	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersionalDownload/getdetails")
	public JsonResponse<RptPersionalDownloadVO> getDetails(@RequestBody JsonRequest<RptPersionalDownloadVO> jsonRequest) {
		JsonResponse<RptPersionalDownloadVO> jsonResponse = new JsonResponse<>();
		logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
		RptPersionalDownloadVO vo = jsonRequest.getReqBody();
		RptPersionalDownloadVO rptPersionalDownloadVO = rptPersionalDownloadService.getByPrimaryKey(vo.getDownloadId());
		jsonResponse.setRspBody(rptPersionalDownloadVO);
		return jsonResponse;
	}
	
	/**
	 * 我的数据下载  -- 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/rptPersionalDownload/findpageresult")
	public JsonResponse<PageDTO<RptPersionalDownloadVO>> findPageResult(@RequestBody JsonRequest<RptPersionalDownloadVO> jsonRequest) {
		JsonResponse<PageDTO<RptPersionalDownloadVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));


			RptPersionalDownloadVO vo = jsonRequest.getReqBody();
//			PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
			PageDTO<RptPersionalDownloadVO> pages = rptPersionalDownloadService.findSelective(vo);
//			PageInfo<RptPersionalDownloadVO> pages = new PageInfo(acctOrgSysVOs);
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
	 * 批量 保存、修改、删除
	 * 参数：opt insert、update、delete
	 * @param JsonResponse
	 * @return
	 *//*
	@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonResponse<List<RptPersionalDownloadVO>> JsonResponse) {
		JsonResponse<Integer> JsonResponse = new JsonResponse<>();
		logger.info("keep 参数 = {}", JsonUtil.toJson(JsonResponse));
		List<RptPersionalDownloadVO> rptPersionalDownloadVOs = JsonResponse.getReqBody();
		if (CollectionUtils.isEmpty(rptPersionalDownloadVOs)) {
			JsonResponse.setRetCode("");
			return JsonResponse;
		}
		Integer result =  rptPersionalDownloadService.keep(rptPersionalDownloadVOs);
		if(result > 0)
			JsonResponse.setRetContent(result);
		else
			throw new BusinessException("204");
		return JsonResponse;
	}*/


	//下载接口
	@RequestMapping("/downloadReportFile")
	public JsonResponse<Boolean> downloadReportFile(@RequestParam("name")String filePath){

		JsonResponse<Boolean> jsonResponse = new JsonResponse<>();
		try {

			Boolean boo = rptPersionalDownloadService.downloadReportFile(filePath);
			jsonResponse.setRspBody(boo);
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

}