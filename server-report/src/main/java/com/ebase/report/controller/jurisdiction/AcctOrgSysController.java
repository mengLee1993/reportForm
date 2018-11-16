package com.ebase.report.controller.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.AcctOrgSysService;
import com.ebase.report.vo.jurisdiction.AcctOrgSysVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :AcctOrgSys
 * @author 
 * @date 2018-10-10
 */
 
@RestController
@RequestMapping("/acctOrgSys")
public class AcctOrgSysController {
	private static Logger logger = LoggerFactory.getLogger(AcctOrgSysController.class);

	@Autowired
    private AcctOrgSysService acctOrgSysService;
    
    /**
	 * 保存
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/save")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<AcctOrgSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctOrgSysVO vo = jsonRequest.getReqBody();
			Integer result = acctOrgSysService.insertSelective(vo);
			jsonResponse.setRspBody(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}

		return jsonResponse;

	}


	/**
	 * 保存
	 *
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/saveOrgInfo")
	public JsonResponse<Integer> saveOrgInfo(@RequestBody JsonRequest<AcctOrgSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctOrgSysVO vo = jsonRequest.getReqBody();
			Integer result = acctOrgSysService.saveOrgInfo(vo);
			jsonResponse.setRspBody(result);
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
	public JsonResponse<Integer> update(@RequestBody JsonRequest<AcctOrgSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctOrgSysVO vo = jsonRequest.getReqBody();
			Integer result = acctOrgSysService.updateByPrimaryKeySelective(vo);
			jsonResponse.setRspBody(result);
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
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<AcctOrgSysVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctOrgSysVO vo = jsonRequest.getReqBody();
			Integer result = acctOrgSysService.deleteByPrimaryKey(vo.getRelaId());
			jsonResponse.setRspBody(result);
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
	public JsonResponse<AcctOrgSysVO> queryDetails(@RequestBody JsonRequest<AcctOrgSysVO> jsonRequest) {
		JsonResponse<AcctOrgSysVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctOrgSysVO vo = jsonRequest.getReqBody();
			AcctOrgSysVO acctOrgSysVO = acctOrgSysService.selectByPrimaryKey(vo.getRelaId());
			jsonResponse.setRspBody(acctOrgSysVO);
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
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<AcctOrgSysVO>> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<AcctOrgSysVO> acctOrgSysVOs = jsonRequest.getReqBody();
			if (CollectionUtils.isEmpty(acctOrgSysVOs)) {
				jsonResponse.setRetCode("");
				return jsonResponse;
			}
			Integer result =  acctOrgSysService.keep(acctOrgSysVOs);
			jsonResponse.setRspBody(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}
    
    
}