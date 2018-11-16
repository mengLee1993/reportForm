package com.ebase.report.controller.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.AcctFunctionSysService;
import com.ebase.report.vo.jurisdiction.AcctFunctionSysVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :AcctFunctionSys
 * @author 
 * @date 2018-10-10
 */
 
@RestController
@RequestMapping("/acctFunctionSys")
public class AcctFunctionSysController {
	private static Logger logger = LoggerFactory.getLogger(AcctFunctionSysController.class);

	@Autowired
    private AcctFunctionSysService acctFunctionSysService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/save")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<AcctFunctionSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctFunctionSysVO vo = jsonRequest.getReqBody();
			Integer result = acctFunctionSysService.insertSelective(vo);
			if (result > 0) {
				jsonResponse.setRspBody(result);
			}else{
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}

		return jsonResponse;

	}
	
	/**
	 * 更新
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/update")
	public JsonResponse<Integer> update(@RequestBody JsonRequest<AcctFunctionSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctFunctionSysVO vo = jsonRequest.getReqBody();
			Integer result = acctFunctionSysService.updateByPrimaryKeySelective(vo);
			if (result > 0) {
				jsonResponse.setRspBody(result);
			}else{
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}

		return jsonResponse;

	}
	
	/**
	 * 删除
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/delete")
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<AcctFunctionSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctFunctionSysVO vo = jsonRequest.getReqBody();
			Integer result = acctFunctionSysService.deleteByPrimaryKey(vo.getRelaId());
			if (result > 0) {
				jsonResponse.setRspBody(result);
			}else{
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}

		return jsonResponse;

	}
	
	/**
	 * 查询单条记录
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/queryDetails")
	public JsonResponse<AcctFunctionSysVO> queryDetails(@RequestBody JsonRequest<AcctFunctionSysVO> jsonRequest) {
		JsonResponse<AcctFunctionSysVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctFunctionSysVO vo = jsonRequest.getReqBody();
			AcctFunctionSysVO acctFunctionSysVO = acctFunctionSysService.selectByPrimaryKey(vo.getRelaId());
			jsonResponse.setRspBody(acctFunctionSysVO);
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
	@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<AcctFunctionSysVO>> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<AcctFunctionSysVO> acctFunctionSysVOs = jsonRequest.getReqBody();
			if (CollectionUtils.isEmpty(acctFunctionSysVOs)) {
				jsonResponse.setRetCode("");
				return jsonResponse;
			}
			Integer result =  acctFunctionSysService.keep(acctFunctionSysVOs);
			if (result > 0) {
				jsonResponse.setRspBody(result);
			}else{
				jsonResponse.setRetCode("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}
    
    
}