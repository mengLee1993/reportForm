package com.ebase.report.service;

import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.vo.RptDataTableVO;

import java.util.List;

public interface RptDataTableService {
    List<RptDataTableVO> queryAllByDataSource(RptDataTableVO vo);

    //业务表表-->数据表<主题>
    ServiceResponse<Integer> insertBusinessTableToThemo(List<RptDataTableVO> vos);

    ServiceResponse<Integer> add(RptDataTableVO vo);

    //假删除-->主题表删除操作
    ServiceResponse<Integer> removeByPrimaryKey(Long tableId);

    ServiceResponse<Integer> updateByPrimaryKey(RptDataTableVO vo);

    ServiceResponse<Integer> updateByPrimaryKeySelective(RptDataTableVO vo);

    /**
     * @param:  vo
     * @return:  List<RptDataTableVO>
     * @description:  根据选择数据库查询出数据库下所有的表
     * @author: lirunze
     * @Date: 2018/11/2
     */
    List<RptDataTableVO> queryAllByDataSourceName(RptDataTableVO vo);

    /**
     * @param:
     * @return:
     * @description:  所有主题表
     * @author: lirunze
     * @Date: 2018/11/2
     */
    List<RptDataTableVO> selectAll(RptDataTableVO vo);

    /**
     * @param:
     * @return:
     * @description:  主题表分页
     * @author: lirunze
     * @Date: 2018/11/2
     */
    List<RptDataTableVO> queryThemeTable(RptDataTableVO vo);

    /**
     * @param:
     * @return:
     * @description:
     * @author: lirunze
     * @Date: 2018/11/3
     */
    RptDataTableVO getDetailByTableId(RptDataTableVO vo);

    /**
     * @param:
     * @return:
     * @description:  删除，真删
     * @author: lirunze
     * @Date: 2018/11/3
     */
    ServiceResponse<Integer> deleteByPrimaryKey(RptDataTableVO vo);
}
