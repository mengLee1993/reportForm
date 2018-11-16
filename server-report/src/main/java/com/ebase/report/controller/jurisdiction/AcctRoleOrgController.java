package com.ebase.report.controller.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.AcctRoleOrgService;
import com.ebase.report.vo.jurisdiction.AcctRoleOrgVO;
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
 * controller :AcctOrgSys
 * @author 
 * @date 2018-10-10
 */
 
@RestController
@RequestMapping("/acctRoleOrg")
public class AcctRoleOrgController {
	private static Logger logger = LoggerFactory.getLogger(AcctRoleOrgController.class);

	@Autowired
    private AcctRoleOrgService acctRoleOrgService;
    
    /**
	 * 保存
	 * 一个角色引用多个组织
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/save")
	public JsonResponse<Integer> save(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			vo.setOrgId(AssertContext.getOrgId());
			Integer result = acctRoleOrgService.insertSelective(vo);
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
	public JsonResponse<Integer> saveOrgInfo(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			Integer result = acctRoleOrgService.saveOrgInfo(vo);
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
	public JsonResponse<Integer> update(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			Integer result = acctRoleOrgService.updateByPrimaryKeySelective(vo);
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
	public JsonResponse<Integer> delete(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			Integer result = acctRoleOrgService.deleteByPrimaryKey(vo.getRelaId());
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
	public JsonResponse<AcctRoleOrgVO> queryDetails(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<AcctRoleOrgVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			AcctRoleOrgVO acctOrgSysVO = acctRoleOrgService.selectByPrimaryKey(vo.getRelaId());
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
	 * 分页查询
	 * 
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/queryPagedResult")
	public JsonResponse<PageInfo<AcctRoleOrgVO>> queryPagedResult(@RequestBody JsonRequest<AcctRoleOrgVO> jsonRequest) {
		JsonResponse<PageInfo<AcctRoleOrgVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			AcctRoleOrgVO vo = jsonRequest.getReqBody();
			PageHelper.startPage(1, 10);
			List<AcctRoleOrgVO> acctOrgSysVOs = acctRoleOrgService.select(vo);
			PageInfo<AcctRoleOrgVO> pages = new PageInfo(acctOrgSysVOs);
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
	@RequestMapping("/keep")
	public JsonResponse<Integer> keep(@RequestBody JsonRequest<List<AcctRoleOrgVO>> jsonRequest) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<AcctRoleOrgVO> acctOrgSysVOs = jsonRequest.getReqBody();
			if (CollectionUtils.isEmpty(acctOrgSysVOs)) {
				jsonResponse.setRetCode("");
				return jsonResponse;
			}
			Integer result =  acctRoleOrgService.keep(acctOrgSysVOs);
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