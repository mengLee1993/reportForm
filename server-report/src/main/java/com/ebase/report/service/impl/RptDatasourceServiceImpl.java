package com.ebase.report.service.impl;

import com.ebase.report.common.RemoveStatusEnum;
import com.ebase.report.controller.IndexController;
import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.conn.ConnPoolType;
import com.ebase.report.core.db.conn.DataSourceManager;
import com.ebase.report.core.db.conn.DbConnFactory;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.RptDatasourceMapper;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.service.RptDatasourceService;
import com.ebase.report.vo.RptDatasourceVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * dal Interface:RptDatasourceService
 * @author       lujiawei
 * @date         2018-9-29
 */
@Service
@Transactional
public class RptDatasourceServiceImpl implements RptDatasourceService {

    private final static Logger logger = LoggerFactory.getLogger(RptDatasourceServiceImpl.class);

    @Autowired
    private RptDatasourceMapper  dsMapper;

    @Autowired
    private ReportHandler reportHandler;


    //--------------------------------查询全部------------------------------------
    public List<RptDatasourceVO> selectAll() {
        List<RptDatasource> RptDatasources = dsMapper.selectAll();
        List<RptDatasourceVO> RptDatasourceVOs = BeanCopyUtil.copyList(RptDatasources, RptDatasourceVO.class);


            for (RptDatasourceVO vo : RptDatasourceVOs) {
                try {
                    boolean flag = DbConnFactory.testConn(vo.getDatasourceName());

                    if (flag) {
                        vo.setConnStatus("1");
                    } else {
                        vo.setConnStatus("0");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    vo.setConnStatus("0");
                }
            }



        return RptDatasourceVOs;
    }

    //--------------------------------根据ID查询单个----------------------------------
    public RptDatasourceVO selectByPrimaryKey(Long key) {
        RptDatasource RptDatasource = dsMapper.selectByPrimaryKey(key);
        return BeanCopyUtil.copy(RptDatasource, RptDatasourceVO.class);
    }

    //--------------------------------新增-------------------------------------------
    public ServiceResponse<Integer> add(RptDatasourceVO vo) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        RptDatasource RptDatasource = BeanCopyUtil.copy(vo, RptDatasource.class);
        List<RptDatasource> rptDatasources = dsMapper.selectAllByDatasourceName(RptDatasource);

        if (!CollectionUtils.isEmpty(rptDatasources)) {
            sR.setRetContent(0);
            sR.setResponseCode("0101001");
            return sR;
        }
        RptDatasource.setRemoveStatus(RemoveStatusEnum.NOREMOVE.getRemoveStatus());
        int num = dsMapper.insert(RptDatasource);
        sR.setRetContent(num);

        try {
            reportHandler.createConn(BeanCopyUtil.copy(vo, RptDatasource.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sR;
    }


    //--------------------------------删除,假删除,改变状态----------------------------------------
    public ServiceResponse<Integer> removeByPrimaryKey(Long key) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(dsMapper.deleteByPrimaryKey(key));
        return sR;
    }

    //--------------------------------高级查询+分页-----------------------------------

    public List<RptDatasourceVO> queryForList(RptDatasourceVO jsonRequest) {
        RptDatasource rptDs=new RptDatasource();
        BeanCopyUtil.copy(jsonRequest,rptDs);
        ServiceResponse<PageInfo<RptDatasourceVO>> response = new ServiceResponse<>();
        //查询分页数据
        List<RptDatasource> list = dsMapper.queryForList(rptDs);
        List<RptDatasourceVO> returnList = BeanCopyUtil.copyList(list, RptDatasourceVO.class);

        for (RptDatasourceVO vo : returnList) {
            try {
                boolean flag = DbConnFactory.testConn(vo.getDatasourceName());

                if (flag) {
                    vo.setConnStatus("1");
                } else {
                    vo.setConnStatus("0");
                }
            } catch (Exception e) {
                e.printStackTrace();
                vo.setConnStatus("0");
            }
        }

        return returnList;
    }

    public PageInfo<RptDatasourceVO> queryByPage(RptDatasourceVO datasourceVO){
        RptDatasource rptDs=new RptDatasource();
        BeanCopyUtil.copy(datasourceVO,rptDs);

        //查询分页数据
        PageHelper.startPage(datasourceVO.getPageNum(), datasourceVO.getPageSize());
        List<RptDatasource> list = dsMapper.queryForList(rptDs);

        PageInfo<RptDatasourceVO> pageInfo = new PageInfo(list);

//        List<RptDatasourceVO> returnList = BeanCopyUtil.copyList(list, RptDatasourceVO.class);
//        PageInfo<RptDatasourceVO> pageVo = new PageInfo(returnList);
//        pageVo.setTotal(pageInfo.getTotal());
//        pageVo.setPages(pageInfo.getPages());
//        pageVo.setPageNum(pageInfo.getPageNum());
//        pageVo.setPageSize(pageInfo.getPageSize());

        return pageInfo;
    }

    //--------------------------------更新----------------------------------------
    public 	ServiceResponse<Integer> updateByPrimaryKeySelective(RptDatasourceVO vo) {
        RptDatasource RptDatasource = BeanCopyUtil.copy(vo, RptDatasource.class);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        List<RptDatasource> rptDatasources = dsMapper.selectAllByDatasourceName(RptDatasource);

        if (!CollectionUtils.isEmpty(rptDatasources)) {
            for (RptDatasource datasource : rptDatasources) {
                if (!datasource.getDatasourceId().equals(vo.getDatasourceId())) {
                    sR.setRetContent(0);
                    sR.setResponseCode("0101001");
                    return sR;
                }
            }
        }

        sR.setRetContent(dsMapper.updateByPrimaryKeySelective(RptDatasource));
        try {
            reportHandler.createConn(BeanCopyUtil.copy(vo, RptDatasource.getClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sR;
    }

    @Override
    public List<RptDatasourceVO> queryDataBaseType() {
        List<RptDatasourceVO> list = new ArrayList<>();
        RptDatasourceVO vo = null;

        vo = new RptDatasourceVO();
        vo.setDatabaseType(DataBaseType.TYPE_NAME_ORACLE);
        list.add(vo);
        vo = new RptDatasourceVO();
        vo.setDatabaseType(DataBaseType.TYPE_NAME_MYSQL);
        list.add(vo);
//        vo.setDatabaseType(DataBaseType.TYPE_NAME_HIVE);
//        list.add(vo);
        return list;
    }

    @Override
    public List<RptDatasourceVO> queryConnPoolType() {
        List<RptDatasourceVO> list = new ArrayList<>();
        RptDatasourceVO vo = null;

        vo = new RptDatasourceVO();
        vo.setConnpoolType(ConnPoolType.TYPE_NAME_HIKARI);
        list.add(vo);
        vo = new RptDatasourceVO();
        vo.setConnpoolType(ConnPoolType.TYPE_NAME_BONECP);
        list.add(vo);
//        vo.setDatabaseType(DataBaseType.TYPE_NAME_HIVE);
//        list.add(vo);
        return list;
    }

    @Override
    public Boolean testConn(RptDatasourceVO vo) {
        Boolean flag = false;
        try {
             flag = reportHandler.createConn(BeanCopyUtil.copy(vo, RptDatasource.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public ServiceResponse<Integer> changeStatus(RptDatasourceVO vo) {
        ServiceResponse<Integer> sR = new ServiceResponse<Integer>();
        RptDatasource RptDatasource = BeanCopyUtil.copy(vo, RptDatasource.class);

        //需要删除 数据源
        DataSourceManager.destroy(vo.getDatasourceName());
        sR.setRetContent(dsMapper.updateByPrimaryKeySelective(RptDatasource)); //都是 逻辑删除 不是物理删除
        return sR;
    }
}