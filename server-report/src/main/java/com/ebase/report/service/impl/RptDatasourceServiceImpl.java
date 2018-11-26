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
import com.ebase.report.dao.*;
import com.ebase.report.model.RptDataTable;
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

    @Autowired
    private RptDataTableMapper rptDataTableMapper;  //数据表

    @Autowired
    private RptDataFieldMapper rptDataFieldMapper;  //数据表字段

    @Autowired
    private RptDataDictMapper rptDataDictMapper; //数据字典

    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper; //个人主题表

    @Autowired
    private RptMeasuresMapper rptMeasuresMapper; //指标

    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper; //报表
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
                    vo.setConnStatus("0");
                    e.printStackTrace();
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

        List<RptDatasourceVO> returnList = BeanCopyUtil.copyList(list, RptDatasourceVO.class);

        PageInfo<RptDatasourceVO> pageInfo = new PageInfo(returnList);

        for (RptDatasourceVO vo : pageInfo.getResultData()) {
            try {
                boolean flag = DbConnFactory.testConn(vo.getDatasourceName());

                if (flag) {
                    vo.setConnStatus("1");
                } else {
                    vo.setConnStatus("0");
                }
            } catch (Exception e) {
                vo.setConnStatus("0");
                e.printStackTrace();
            }
        }

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

        for(DataBaseType dataBaseType : DataBaseType.getTypeMap().values()){
            RptDatasourceVO vo = new RptDatasourceVO();
            vo.setDatabaseType(dataBaseType.getName());
            list.add(vo);
        }

        return list;
    }

    @Override
    public List<RptDatasourceVO> queryConnPoolType() {
        List<RptDatasourceVO> list = new ArrayList<>();
        for(ConnPoolType poolType : ConnPoolType.getTypeMap().values()){
            RptDatasourceVO vo = new RptDatasourceVO();
            vo.setConnpoolType(poolType.getName());
            list.add(vo);
        }

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


        int removeStatus = vo.getRemoveStatus();


        if(removeStatus == 0){ //未
            com.ebase.report.model.RptDatasource rptDatasource = dsMapper.selectByPrimaryKey(vo.getDatasourceId());
            reportHandler.createConn(rptDatasource);

            sR.setRetContent(dsMapper.updateByPrimaryKeySelective(RptDatasource)); //都是 逻辑删除 不是物理删除
        }else if(removeStatus == 1){
            //禁用
            DataSourceManager.get().destroy(vo.getDatasourceName());

            sR.setRetContent(dsMapper.updateByPrimaryKeySelective(RptDatasource)); //都是 逻辑删除 不是物理删除
        }else{
            //删除数据  数据源，主题，报表下面对数据  清除所有
            //禁用
            DataSourceManager.get().destroy(vo.getDatasourceName());

            Long datasourceId = vo.getDatasourceId();

            removeDatasource(datasourceId);

        }


        return sR;
    }

    private void removeDatasource(Long datasourceId) {
        //数据报表
        List<RptDataTable> dataTables = rptDataTableMapper.selectByDatasourceId(datasourceId);
        //删除数据表
        dsMapper.deleteById(datasourceId);

        rptDataTableMapper.deleteByDatasourceId(datasourceId);
        dataTables.forEach(x -> {
            //数据表id
            Long tableId = x.getTableId();
            //根据表ID查询所有对字段id
            List<Long> fields = rptDataFieldMapper.selectIdByTableid(tableId);

            rptDataFieldMapper.deleteByTableId(tableId);
            if(!CollectionUtils.isEmpty(fields)){
                //根据字段删除元数据
                rptDataDictMapper.deleteByFieldIds(fields);
            }

            //根据table ids删除主题id
            List<Long> subjectIds = rptPersonalSubjectMapper.selectIdByTableId(tableId);

            rptPersonalSubjectMapper.deleteByTableId(tableId);
            if(!CollectionUtils.isEmpty(subjectIds)){
                //删除指标
                rptMeasuresMapper.deleteBySubjectId(subjectIds);
                //删除报表
                rptPersonalAnalysisMapper.deleteBySubjectId(subjectIds);
            }

        });
    }
}