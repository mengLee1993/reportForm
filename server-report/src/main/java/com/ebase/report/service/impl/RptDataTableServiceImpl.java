package com.ebase.report.service.impl;

import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.RemoveStatusEnum;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.RptDataFieldMapper;
import com.ebase.report.dao.RptDataTableMapper;
import com.ebase.report.dao.RptDatasourceMapper;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptDataTable;
import com.ebase.report.model.RptDatasource;
import com.ebase.report.service.RptDataTableService;
import com.ebase.report.vo.RptDataTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RptDataTableServiceImpl implements RptDataTableService {

    @Autowired
    private RptDatasourceMapper rptDatasourceMapper;

    @Autowired
    private RptDataTableMapper rptDataTableMapper;

    @Autowired
    private RptDataFieldMapper rptDataFieldMapper;

    @Autowired
    private ReportHandler reportHandler;

    //---------------------------根据选择数据库查询出数据库下所有的表------------------
    @Transactional(readOnly = true)
    public List<RptDataTableVO> queryAllByDataSource(RptDataTableVO vo) {
        List<RptDataTable> allTable = rptDataTableMapper.queryAllByDataSource(vo);
        List<RptDataTableVO> listVO = BeanCopyUtil.copyList(allTable, RptDataTableVO.class);
        return listVO;
    }

    //业务表表-->数据表<主题>
    public ServiceResponse<Integer> insertBusinessTableToThemo(List<RptDataTableVO> vos) {
        List<RptDataTable> listRdts = BeanCopyUtil.copyList(vos, RptDataTable.class);
        int i;
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        for (RptDataTable rdt : listRdts) {
            rdt.setTableName(rdt.getComment());
            rdt.setRemoveStatus(RemoveStatusEnum.NOREMOVE.getRemoveStatus());
            rptDataTableMapper.insertSelective(rdt);
            //通过表名查询到此表的列名,然后存如字段表
            List<RptDataField> fields = reportHandler.queryFields(rdt.getDatasourceName(), rdt.getTableCode());
            for (RptDataField field : fields) {
                field.setFieldName(field.getFieldName());
                field.setFieldCode(field.getFieldCode());
                field.setTableId(rdt.getTableId());
                field.setCreatedDt(new Date());
                field.setAccuracy(0);
                field.setRowLevelAuth(Byte.parseByte("0"));

                //设置初始化指标或者是维度
                String fieldType = field.getFieldType();
                DBFieldTypeEnum dbFieldTypeEnum = DBFieldTypeEnum.getByName(fieldType);
                field.setDimensionIndex(dbFieldTypeEnum.getDemandType());
            }
            i = rptDataFieldMapper.insertBatch(fields);
            sR.setRetContent(i);
        }
        return sR;
    }

    //--------------------------------新增-------------------------------------
    public ServiceResponse<Integer> add(RptDataTableVO vo) {
        RptDataTable rdt = BeanCopyUtil.copy(vo, RptDataTable.class);
        int num = rptDataTableMapper.insert(rdt);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(num);
        return sR;
    }

    //--------------------------------删除,假删除,改变状态-------------------------
    public ServiceResponse<Integer> removeByPrimaryKey(Long tableId) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(rptDataTableMapper.deleteByPrimaryKey(tableId));
        return sR;
    }

    //---------------------------------更新--------------------------------------
    public ServiceResponse<Integer> updateByPrimaryKey(RptDataTableVO vo) {
        RptDataTable rpt = BeanCopyUtil.copy(vo, RptDataTable.class);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(rptDataTableMapper.updateByPrimaryKey(rpt));
        return sR;
    }

    public ServiceResponse<Integer> updateByPrimaryKeySelective(RptDataTableVO vo) {
        RptDataTable rpt = BeanCopyUtil.copy(vo, RptDataTable.class);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        List<RptDataTable> list = rptDataTableMapper.selectbyTableName(rpt);

        if (!CollectionUtils.isEmpty(list)) {
            for (RptDataTable table : list) {
                if (!table.getTableId().equals(vo.getTableId())) {
                    sR.setRetContent(0);
                    sR.setResponseCode("0101002");
                    return sR;
                }
            }
        }

        sR.setRetContent(rptDataTableMapper.updateByPrimaryKeySelective(rpt));
        return sR;
    }

    @Override
    public List<RptDataTableVO> queryAllByDataSourceName(RptDataTableVO vo) {
        List<RptDataTable> list = reportHandler.queryAllTables(vo.getDatasourceName());
        List<RptDataTableVO> themsList = this.selectAll(vo);
//        RptDatasource rptDatasource = rptDatasourceMapper.selectByPrimaryKey(vo.getDatasourceId());
        List<RptDataTableVO> returnList = BeanCopyUtil.copyList(list, RptDataTableVO.class);
        for (RptDataTableVO table : returnList) {
            table.setDatasourceChineseName(vo.getDatasourceChineseName());
            table.setDatasourceName(vo.getDatasourceName());
            for (RptDataTableVO tableVO : themsList) {
                if (table.getTableCode().equals(tableVO.getTableCode())) {
                    table.setStatus("1");//已添加
                }
            }
        }
        return returnList;
    }

    @Override
    public List<RptDataTableVO> selectAll(RptDataTableVO vo) {
        List<RptDataTable> list = rptDataTableMapper.select(BeanCopyUtil.copy(vo, RptDataTable.class));
        return BeanCopyUtil.copyList(list, RptDataTableVO.class);
    }

    @Override
    public List<RptDataTableVO> queryThemeTable(RptDataTableVO vo) {
        List<RptDataTable> list = rptDataTableMapper.queryThemeTable(BeanCopyUtil.copy(vo, RptDataTable.class));
        return BeanCopyUtil.copyList(list, RptDataTableVO.class);
    }

    @Override
    public RptDataTableVO getDetailByTableId(RptDataTableVO vo) {
        RptDataTable table = rptDataTableMapper.selectByTableId(vo.getTableId());
        return BeanCopyUtil.copy(table, RptDataTableVO.class);
    }

    @Override
    public ServiceResponse<Integer> deleteByPrimaryKey(RptDataTableVO vo) {
        // TODO: 2018/11/3  删除逻辑待处理

        return null;
    }


    @Override
    public PageDTO<RptDataTableVO> queryForPage(RptDataTableVO vo) {
        RptDataTable model = BeanCopyUtil.copy(vo, RptDataTable.class);

        PageDTO<RptDataTableVO> pageDTO = new PageDTO<>(vo.getPageNum(), vo.getPageSize());

        Integer count = rptDataTableMapper.selectCount(model);
        pageDTO.setTotal(count);

        model.setStartRow(pageDTO.getStartRow());
        List<RptDataTable> rptDataTables = rptDataTableMapper.selectByPage(model);
        List<RptDataTableVO> rptDataTableVOs = BeanCopyUtil.copyList(rptDataTables, RptDataTableVO.class);
        String datasourceChineseName = vo.getDatasourceChineseName();
        String datasourceName = vo.getDatasourceName();
        for (RptDataTableVO vo2 : rptDataTableVOs) {
            vo2.setDatasourceChineseName(datasourceChineseName);
            vo2.setDatasourceName(datasourceName);
        }
        pageDTO.setResultData(rptDataTableVOs);
        return pageDTO;
    }

}
