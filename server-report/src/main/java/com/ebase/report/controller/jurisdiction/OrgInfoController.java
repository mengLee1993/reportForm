package com.ebase.report.controller.jurisdiction;


import com.ebase.report.core.BusinessException;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.JsonUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.model.jurisdiction.AcctInfo;
import com.ebase.report.model.jurisdiction.OrgInfo;
import com.ebase.report.service.jurisdiction.OrgInfoService;
import com.ebase.report.vo.jurisdiction.AcctInfoVO;
import com.ebase.report.vo.jurisdiction.OrgInfoVO;
import com.ebase.report.vo.jurisdiction.SysInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


/**
 *
 * @author zhangx
 *
 */
@RestController
@RequestMapping("/orgInfo")
public class OrgInfoController {

	private static Logger logger = LoggerFactory.getLogger(OrgInfoController.class);

	@Autowired
	private OrgInfoService orgInfoService;

	/**
	 * 添加   组织机构信息
	 * @param orgInfoVO
	 * @return
	 */
	@RequestMapping("/addOrgInfo")
	public JsonResponse<String> addOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<String> jsonResponse = new JsonResponse<String>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {
			Long i = orgInfoService.addOrgInfo(orgInfo);

			jsonResponse.setRspBody(orgInfo.getId());

		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织名称唯一性校验
	 * 未发
	 */
	@RequestMapping("/getParityOrgName")
	public JsonResponse<Boolean> getParityOrgName(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<Boolean> jsonResponse = new JsonResponse<Boolean>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {

			Boolean condition = orgInfoService.getParityOrgName(orgInfo);

			jsonResponse.setRspBody(condition);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织机构信息树查詢
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getChildTreeOrgInfo")
	public JsonResponse<Map<String, List<OrgInfoVO>>> getChildTreeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		logger.info(" www 系统编码list 参数 = {}", JsonUtil.toJson(jsonRequest));
		JsonResponse<Map<String, List<OrgInfoVO>>> jsonResponse = new JsonResponse<Map<String, List<OrgInfoVO>>>();
		try {
			OrgInfoVO orgInfoVO=jsonRequest.getReqBody();
			orgInfoVO.setParentId(AssertContext.getOrgId());

			OrgInfoVO retContent = orgInfoService.getChildTreeOrgInfo(orgInfoVO);
			Map<String, List<OrgInfoVO>> map=new HashMap<String, List<OrgInfoVO>>();
			map.put("children", Arrays.asList(retContent));
			jsonResponse.setRspBody(map);
		} catch (BusinessException e) {
			jsonResponse.setRetCode(e.getErrorCode());
			jsonResponse.setRetDesc(e.getMessage());
		}
		return jsonResponse;
	}

	/**
	 * 刪除 组织机构信息
	 * @param orgInfoVO
	 * @return
	 */
	@RequestMapping("/removeOrgInfo")
	public JsonResponse<Integer> removeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<Integer>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {
			Integer i = orgInfoService.removeOrgInfo(orgInfo);
			jsonResponse.setRspBody(i);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织机构信息父查子
	 * @param id
	 * @return
	 */
	@RequestMapping("/getListTreeOrgInfo")
	public JsonResponse<Map<String, List<OrgInfoVO>>> getListTreeOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<Map<String, List<OrgInfoVO>>> jsonResponse = new JsonResponse<Map<String, List<OrgInfoVO>>>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {
			List<OrgInfo> listTreeOrgInfo = orgInfoService.getListTreeOrgInfo(orgInfo);
			List<OrgInfoVO> retContentVO  = BeanCopyUtil.copyList(listTreeOrgInfo, OrgInfoVO.class);
            Map<String, List<OrgInfoVO>> map=new HashMap<String, List<OrgInfoVO>>();
            map.put("resultData",retContentVO);
            jsonResponse.setRspBody(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 修改  组织机构信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/saveOrgInfo")
	public JsonResponse<Integer> saveOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<Integer> jsonResponse = new JsonResponse<Integer>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {
			Integer i = orgInfoService.saveOrgInfo(orgInfo);
			jsonResponse.setRspBody(i);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织机构信息查詢
	 * @param id
	 * @return
	 */
	@RequestMapping("/getOrgInfo")
	public JsonResponse<OrgInfoVO> getOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<OrgInfoVO> jsonResponse = new JsonResponse<OrgInfoVO>();

		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(orgInfoVO.getReqBody(), orgInfo);
		try {
			OrgInfo orgInfo2 = orgInfoService.getOrgInfo(orgInfo);
			OrgInfoVO copy = BeanCopyUtil.copy(orgInfo2, OrgInfoVO.class);
			jsonResponse.setRspBody(copy);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 角色未引用并且可以引用的组织
	 * @param jsonRequest
	 * @return
	 */
	@RequestMapping("/selectRoleYesQuote")
	public JsonResponse<PageInfo<OrgInfoVO>> selectRoleYesQuote(@RequestBody JsonRequest<OrgInfoVO> jsonRequest) {
		JsonResponse<PageInfo<OrgInfoVO>> jsonResponse = new JsonResponse<PageInfo<OrgInfoVO>>();

		try {
            OrgInfoVO orgInfoVO=jsonRequest.getReqBody();
            if(AssertContext.getAcctType().equals(1)){
                orgInfoVO.setOrgId(AssertContext.getOrgId());
            }
            if(AssertContext.getAcctType().equals(0)){
                orgInfoVO.setOrgId(AssertContext.getOrgId());
            }
			PageInfo<OrgInfoVO> page = orgInfoService.selectRoleYesQuote(orgInfoVO);
			jsonResponse.setRspBody(page);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织机构信息查詢分页
	 * @param id
	 * @return
	 */
	@RequestMapping("/getListOrgInfo")
	public JsonResponse<PageInfo<OrgInfoVO>> getListOrgInfo(@RequestBody JsonRequest<OrgInfoVO> orgInfoVO) {
		JsonResponse<PageInfo<OrgInfoVO>> jsonResponse = new JsonResponse<PageInfo<OrgInfoVO>>();

		try {
			PageInfo<OrgInfoVO> page = orgInfoService.getListOrgInfo(orgInfoVO.getReqBody());
			jsonResponse.setRspBody(page);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	 * 物料综合查询用
	 * @return
	 */
	@RequestMapping("/getMaterielOrginfo")
	public JsonResponse<List<OrgInfoVO>> getMaterielOrginfo(@RequestBody JsonRequest<AcctInfoVO> reqBody){
		JsonResponse<List<OrgInfoVO>> jsonResponse = new JsonResponse<List<OrgInfoVO>>();

		AcctInfo acctInfo = new AcctInfo();
		acctInfo.setAcctId(reqBody.getReqBody().getAcctId());
		List<OrgInfoVO> list=new ArrayList<>();
		try {
			List<OrgInfo> listOrgInfo= orgInfoService.getMaterielOrginfo(acctInfo);
			if(!CollectionUtils.isEmpty(listOrgInfo)){
				list = BeanCopyUtil.copyList(listOrgInfo, OrgInfoVO.class);
			}
			jsonResponse.setRspBody(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 根据当前用户的组织id，查询出当前用户及当前用户一下的组织
	 * 物料综合查询用
	 * @return
	 */
	@RequestMapping("/selectSysQuoteOrgInof")
	public JsonResponse<List<OrgInfoVO>> selectSysQuoteOrgInof(@RequestBody JsonRequest<SysInfoVO> reqBody){
		JsonResponse<List<OrgInfoVO>> jsonResponse = new JsonResponse<List<OrgInfoVO>>();

		try {
			List<OrgInfoVO> listOrgInfo= orgInfoService.selectSysQuoteOrgInof(reqBody.getReqBody());
			jsonResponse.setRspBody(listOrgInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}


	/**
	 * 角色引用多个组织 角色已引用组织
	 * @return
	 */
	@RequestMapping("/selectRoleQuoteOrg")
	public JsonResponse<HashMap<String,List<OrgInfoVO>>> selectRoleQuoteOrg(@RequestBody JsonRequest<OrgInfoVO> reqBody){
		JsonResponse<HashMap<String,List<OrgInfoVO>>> jsonResponse = new JsonResponse<HashMap<String,List<OrgInfoVO>>>();
		try {
            OrgInfoVO orgInfoVO=reqBody.getReqBody();
            orgInfoVO.setOrgId(AssertContext.getOrgId());
			List<OrgInfoVO> listOrgInfo= orgInfoService.selectRoleQuoteOrg(orgInfoVO);
            HashMap map=new HashMap();
            map.put("resultData", listOrgInfo);
            jsonResponse.setRspBody(map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
			return jsonResponse;
		}
		return jsonResponse;
	}

	/**
	 * 组织机构信息树查詢--->角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPwerTreeRoleInfo")
	public JsonResponse<OrgInfoVO> getPwerTreeRoleInfo(@RequestBody JsonRequest<OrgInfo> jsonRequest) {
		JsonResponse<OrgInfoVO> response = new JsonResponse<OrgInfoVO>();

		OrgInfo reqBody = jsonRequest.getReqBody();
		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(reqBody, orgInfo);
		try {
			OrgInfo childTreeOrgInfo = orgInfoService.getPwerTreeRoleInfo(orgInfo);
			OrgInfoVO copy = BeanCopyUtil.copy(childTreeOrgInfo, OrgInfoVO.class);
			response.setRspBody(copy);
		} catch (BusinessException e) {
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
			logger.error(e.getMessage());
		}
		return response;
	}


	/**
	 * 组织机构信息树查詢 -->用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPwerTreeAcctInfo")
	public JsonResponse<OrgInfoVO> getPwerTreeAcctInfo(@RequestBody JsonRequest<OrgInfo> jsonRequest) {
		JsonResponse<OrgInfoVO> response = new JsonResponse<OrgInfoVO>();

		OrgInfo reqBody = jsonRequest.getReqBody();
		OrgInfo orgInfo = new OrgInfo();
		BeanCopyUtil.copy(reqBody, orgInfo);
		try {
			OrgInfo childTreeOrgInfo = orgInfoService.getPwerTreeAcctInfo(orgInfo);
			OrgInfoVO copy = BeanCopyUtil.copy(childTreeOrgInfo, OrgInfoVO.class);
			response.setRspBody(copy);
		} catch (BusinessException e) {
			response.setRetCode(JsonResponse.SYS_EXCEPTION);
			logger.error(e.getMessage());
		}
		return response;
	}


}
