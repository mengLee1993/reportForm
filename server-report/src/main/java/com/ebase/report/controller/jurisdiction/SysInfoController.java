package com.ebase.report.controller.jurisdiction;

import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.service.jurisdiction.SysInfoService;
import com.ebase.report.vo.jurisdiction.SysInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller :SysInfo
 * @author 
 * @date 2018-10-10
 */
 
@RestController
@RequestMapping("/sysInfo")
public class SysInfoController {
	private static Logger logger = LoggerFactory.getLogger(SysInfoController.class);

	@Autowired
    private SysInfoService sysInfoService;
    
    /**
	 * 保存
	 * 
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/save")
	public JsonResponse<SysInfoVO> save(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<SysInfoVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("save 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
            jsonRequest.setOrgId(AssertContext.getOrgId());
            jsonRequest.setCreatedBy(AssertContext.getAcctName());
            SysInfoVO result = sysInfoService.insertSelective(jsonRequest);
			jsonResponse.setRspBody(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}

		return jsonResponse;

	}
	
	/**
	 * 更新
	 * 
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/update")
	public JsonResponse<SysInfoVO> update(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<SysInfoVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("update 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
            SysInfoVO result = sysInfoService.updateByPrimaryKeySelective(jsonRequest);
			jsonResponse.setRspBody(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public JsonResponse<SysInfoVO> delete(@RequestBody JsonRequest<SysInfoVO> jsonRequest) {
		JsonResponse<SysInfoVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysInfoVO vo = jsonRequest.getReqBody();
			SysInfoVO result = sysInfoService.deleteByPrimaryKey(vo.getSysId());
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
	 * 系统删除验证  是否被组织关联
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/verSysInfo")
	public JsonResponse<String> verSysInfo(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<String> jsonResponse = new JsonResponse();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO sysInfoVO=jsonreq.getReqBody();
			String ver = sysInfoService.verSysInfo(sysInfoVO);
			jsonResponse.setRspBody(ver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;

		}
		return jsonResponse;
	}

	/**
	 * 系统删除验证  是否被组织关联
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/verInsertSysInfo")
	public JsonResponse<String> verInsertSysInfo(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<String> jsonResponse = new JsonResponse();
		try {
			logger.info("delete 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO sysInfoVO=jsonreq.getReqBody();
			String ver = sysInfoService.verInsertSysInfo(sysInfoVO);
			jsonResponse.setRspBody(ver);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public JsonResponse<SysInfoVO> queryDetails(@RequestBody JsonRequest<SysInfoVO> jsonRequest) {
		JsonResponse<SysInfoVO> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryDetails 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysInfoVO vo = jsonRequest.getReqBody();
			SysInfoVO sysInfoVO = sysInfoService.selectByPrimaryKey(vo.getSysId());
			jsonResponse.setRspBody(sysInfoVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public JsonResponse<PageInfo<SysInfoVO>> queryPagedResult(@RequestBody JsonRequest<SysInfoVO> jsonRequest) {
		JsonResponse<PageInfo<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonRequest));
			SysInfoVO vo = jsonRequest.getReqBody();
			PageInfo<SysInfoVO> pages = sysInfoService.select(vo);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 组织管理员创建的系统
	 *
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/selectSysInfoOrgCreate")
	public JsonResponse<List<SysInfoVO>> selectSysInfoOrgCreate(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<List<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
			List<SysInfoVO> pages = sysInfoService.selectSysInfoOrgCreate(jsonRequest);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 引用查询
	 *
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/selectSysInfoOrg")
	public JsonResponse<List<SysInfoVO>> selectSysInfoOrg(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<List<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
            jsonRequest.setOrgId(AssertContext.getOrgId());
            jsonRequest.setAcctType(AssertContext.getAcctType());
			List<SysInfoVO> pages = sysInfoService.selectSysInfoOrg(jsonRequest);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 根据组织查询创建系统
	 *
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/selectSysEstablish")
	public JsonResponse<List<SysInfoVO>> selectSysEstablish(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<List<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
			List<SysInfoVO> pages = sysInfoService.selectSysEstablish(jsonRequest);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 查看查询
	 *
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/selectSysInfoOrgSee")
	public JsonResponse<List<SysInfoVO>> selectSysInfoOrgSee(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<List<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
			jsonRequest.setOrgId(AssertContext.getOrgId());
            jsonRequest.setAcctType(AssertContext.getAcctType());
			List<SysInfoVO> pages = sysInfoService.selectSysInfoOrgSee(jsonRequest);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public JsonResponse<SysInfoVO> keep(@RequestBody JsonRequest<List<SysInfoVO>> jsonRequest) {
		JsonResponse<SysInfoVO> jsonResponse = new JsonResponse<>();
		
		try {
			logger.info("keep 参数 = {}", JsonUtil.toJson(jsonRequest));
			List<SysInfoVO> sysInfoVOs = jsonRequest.getReqBody();
//			if (CollectionUtils.isEmpty(sysInfoVOs)) {
//				jsonResponse.setRetCode("");
//				return jsonResponse;
//			}
			SysInfoVO result =  sysInfoService.keep(sysInfoVOs);
			jsonResponse.setRspBody(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}




	/**
	 * 查看查询
	 *
	 * @param jsonreq
	 * @return
	 */
	@RequestMapping("/selectSysInfoRoleSee")
	public JsonResponse<List<SysInfoVO>> selectSysInfoRoleSee(@RequestBody JsonRequest<SysInfoVO> jsonreq) {
		JsonResponse<List<SysInfoVO>> jsonResponse = new JsonResponse<>();
		try {
			logger.info("queryPagedResult 参数 = {}", JsonUtil.toJson(jsonreq));
			SysInfoVO jsonRequest=jsonreq.getReqBody();
			if(StringUtils.isEmpty(jsonRequest.getOrgId())){
				jsonRequest.setOrgId(AssertContext.getOrgId());
			}
			jsonRequest.setAcctType(Long.parseLong("1"));
			List<SysInfoVO> pages = sysInfoService.selectSysInfoOrgSee(jsonRequest);
			jsonResponse.setRspBody(pages);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}
}