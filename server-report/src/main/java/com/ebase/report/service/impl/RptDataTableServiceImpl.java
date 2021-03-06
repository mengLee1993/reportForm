package com.ebase.report.service.impl;

import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.RemoveStatusEnum;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.*;
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

    @Autowired
    private RptDataDictMapper rptDataDictMapper;

    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper;

    @Autowired
    private RptMeasuresMapper rptMeasuresMapper;

    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper;


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
            //防止重复添加,如果表中存在此条数据就跳过此次循环
            Integer count = rptDataTableMapper.selectCount(rdt);
            if(count > 0){
                continue;
            }
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
                if(dbFieldTypeEnum != null){
                    field.setDimensionIndex(dbFieldTypeEnum.getDemandType());
                }
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

    //--------------------------------删除,真删除-------------------------
    public ServiceResponse<Integer> removeByPrimaryKey(Long tableId) {
        /*ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(rptDataTableMapper.deleteById(tableId));

        rptDataFieldMapper.deleteByTableId(tableId);*/

        ServiceResponse<Integer> sR = new ServiceResponse<>();
        rptDataTableMapper.deleteById(tableId);
        List<Long> fields = rptDataFieldMapper.selectIdByTableid(tableId);
        try {
            fields.forEach(x -> {
                //根据表ID查询所有对字段id
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
            sR.setRetContent(1);
        }catch (Exception e){
            e.printStackTrace();
            sR.setRetContent(0);
        }
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

        RptDataTableVO queryVo = new RptDataTableVO();
        queryVo.setDatasourceId(vo.getDatasourceId());
        List<RptDataTableVO> themsList = this.selectAll(queryVo);
//        RptDatasource rptDatasource = rptDatasourceMapper.selectByPrimaryKey(vo.getDatasourceId());
        List<RptDataTableVO> returnList = BeanCopyUtil.copyList(list, RptDataTableVO.class);
        for (RptDataTableVO table : returnList) {
            table.setDatasourceChineseName(vo.getDatasourceChineseName());
            table.setDatasourceName(vo.getDatasourceName());
            table.setStatus("0");
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
