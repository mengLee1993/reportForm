package com.ebase.report.service;

import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.vo.RptDatasourceVO;

import java.util.List;

/**
 * dal Interface:RptDatasourceService
 * @author 		 lujiawei
 * @date 		 2018-9-29
 */
public interface RptDatasourceService {

	/**
	 * 查询全部
	 * @param
	 * @return List
	 */
	public List<RptDatasourceVO> selectAll();


	/**
	 * 查询一条
	 * @param key
	 * @return VO
	 */
	public RptDatasourceVO selectByPrimaryKey(Long key);


	/**
	 * 增加
	 * @param vo
	 * @return Integer
	 */
	public ServiceResponse<Integer> add(RptDatasourceVO vo);


	/**
	 * 根据主键删除<更改状态>
	 * @param key
	 * @return Integer
	 */
	public ServiceResponse<Integer> removeByPrimaryKey(Long key);

	/**
	 *高级查询+分页
	 * @param key
	 * @return PageInfo<RptDatasourceVO>
	 */
	public List<RptDatasourceVO> queryForList(RptDatasourceVO jsonRequest);

	public PageInfo<RptDatasourceVO> queryByPage(RptDatasourceVO rptDatasourceVO);

	/**
	 * 根据主键更新
	 * @param datasourceId
	 * @returnServiceResponse<Integer>
	 */
	ServiceResponse<Integer> updateByPrimaryKeySelective(RptDatasourceVO vo);

	/**
	 * @param:  
	 * @return:  List<RptDatasourceVO>
	 * @description:  获取数据库类型
	 * @author: lirunze
	 * @Date: 2018/11/1
	 */
	List<RptDatasourceVO> queryDataBaseType();

	/**
	 * @param:
	 * @return:	List<RptDatasourceVO>
	 * @description:  获取数据连接池类型
	 * @author: lirunze
	 * @Date: 2018/11/1
	 */
	List<RptDatasourceVO> queryConnPoolType();

	/**
	 * @param:  vo
	 * @return:  List<RptDatasourceVO>
	 * @description:  测试数据库连接
	 * @author: lirunze
	 * @Date: 2018/11/2
	 */
	Boolean testConn(RptDatasourceVO vo);

	/**
	 * @param:
	 * @return:
	 * @description:  启用禁用
	 * @author: lirunze
	 * @Date: 2018/11/5
	 */
	ServiceResponse<Integer> changeStatus(RptDatasourceVO vo);

	/**
	 * 查看当前能看的所有数据库
	 * @param acctId
	 * @param orgId
	 * @return
	 */
    List<RptDatasource> listSelect(String acctId, String orgId);
}