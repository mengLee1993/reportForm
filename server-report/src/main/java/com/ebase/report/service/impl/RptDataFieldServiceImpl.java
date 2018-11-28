package com.ebase.report.service.impl;

import com.ebase.report.core.db.DataBaseType;
import com.ebase.report.core.db.conn.DbConnFactory;
import com.ebase.report.core.db.exception.DbException;
import com.ebase.report.core.db.handler.AbstractAccessor;
import com.ebase.report.core.db.handler.AccessorFactory;
import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.pageUtil.PageInfo;
import com.ebase.report.core.utils.BeanCopyUtil;
import com.ebase.report.core.utils.serviceResponse.ServiceResponse;
import com.ebase.report.dao.RptDataDictMapper;
import com.ebase.report.dao.RptDataFieldMapper;
import com.ebase.report.dao.RptDataTableMapper;
import com.ebase.report.dao.RptDatasourceMapper;
import com.ebase.report.model.*;
import com.ebase.report.service.RptDataFieldService;
import com.ebase.report.vo.RptDataDictVO;
import com.ebase.report.vo.RptDataFieldVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RptDataFieldServiceImpl implements RptDataFieldService {

    @Autowired
    private RptDataFieldMapper rdfMapper;

    @Autowired
    private RptDataTableMapper rptDataTableMapper;

    @Autowired
    private RptDatasourceMapper rptDatasourceMapper;

    @Autowired
    private RptDataDictMapper rptDataDictMapper;

    @Autowired
    private ReportHandler reportHandler;

    //-----------------------------------分页----------------------------------
    @Transactional(readOnly = true)
    public List<RptDataFieldVO> queryForList(RptDataFieldVO vo) {
        RptDataField rptDs=new RptDataField();
        BeanCopyUtil.copy(vo,rptDs);
        ServiceResponse<PageInfo<RptDataFieldVO>> response = new ServiceResponse<>();
        //查询分页数据
        List<RptDataField> list = rdfMapper.queryForList(rptDs);
        List<RptDataFieldVO> roleInfoVo= BeanCopyUtil.copyList(list, RptDataFieldVO.class);
        return roleInfoVo;

    }

    /**
     * 新增
     * @param vo
     * @return
     */
    public ServiceResponse<Integer> add(RptDataFieldVO vo) {
        RptDataField rdf = BeanCopyUtil.copy(vo, RptDataField.class);
        Date date = new Date();
        rdf.setCreatedDt(date);
        int num = rdfMapper.insertSelective(rdf);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(num);
        return sR;
    }


    /**
     * 更新
     * @param vo
     * @return
     */
    public ServiceResponse<Integer> updateByPrimaryKey(RptDataFieldVO vo) {
        RptDataField rdf = BeanCopyUtil.copy(vo, RptDataField.class);
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        Date date = new Date();
        rdf.setUpdatedDt(date);
        sR.setRetContent(rdfMapper.updateByPrimaryKeySelective(rdf));
        return sR;
    }

    /**
     * 分页
     * @param vo
     * @return
     */
    public List<RptDataDictVO> findSelective(RptDataFieldVO record) {
        RptDataField model = BeanCopyUtil.copy(record, RptDataField.class);
        List<RptDataDict> rptPersonalSubjects = rdfMapper.select(model);
        List<RptDataDictVO> rptPersonalSubjectVOs = BeanCopyUtil.copyList(rptPersonalSubjects, RptDataDictVO.class);
        return rptPersonalSubjectVOs;
    }

    @Override
    public ServiceResponse<Integer> updateBatch(List<RptDataFieldVO> list) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();

        if (!CollectionUtils.isEmpty(list)) {

            for (RptDataFieldVO vo : list) {
                RptDataField rdf = BeanCopyUtil.copy(vo, RptDataField.class);
                Date date = new Date();
                rdf.setUpdatedDt(date);
                if (vo.getRowLevelAuth()==1) {
                    StringBuffer sql = new StringBuffer();
                    sql.append("select COUNT(distinct(");
                    sql.append(vo.getFieldCode());
                    sql.append(")) from ");
                    sql.append(vo.getTableCode());
                    vo.setMetadataCount(reportHandler.queryDistinctCount(vo.getDatasourceName(), sql.toString()));
                }
                rdfMapper.updateByPrimaryKeySelective(rdf);
            }
        }
        return sR;
    }

    @Override
    public List<RptDataFieldVO> queryList(RptDataFieldVO vo) {
        List<RptDataField> list = rdfMapper.selectList(BeanCopyUtil.copy(vo, RptDataField.class));
        return BeanCopyUtil.copyList(list, RptDataFieldVO.class);
    }

    @Override
    public List<RptDataFieldVO> queryMetadataList(RptDataFieldVO vo) {
        vo.setRowLevelAuth(Byte.valueOf("1"));
        return this.queryList(vo);
    }

    @Override
    public Boolean extractMetadata(List<RptDataFieldVO> list) {
        boolean flag = false;
        // 获取数据源对象
        Map<String, List<RptDataFieldVO>> map = new HashMap<>();
        for (RptDataFieldVO vo : list) {
            rptDataDictMapper.deleteByFieldId(vo.getFieldId());
            map.put(vo.getDatasourceName(), new ArrayList<>());
        }

        for (RptDataFieldVO vo : list) {
            map.get(vo.getDatasourceName()).add(vo);
        }

        // 创建一次连接运行多个sql
        try {
            for (String s : map.keySet()) {
                List<RptDataDict> dataDicts = reportHandler.queryDistinct(s, map.get(s));
                // 批量添加数据
                rptDataDictMapper.insertBatch(dataDicts);

                // 跟新元数据数量
                List<RptDataFieldVO> fieldVOS = reportHandler.queryDistinctCountBatch(s, map.get(s));

                for (RptDataFieldVO vo : fieldVOS) {
                    rdfMapper.updateByPrimaryKeySelective(BeanCopyUtil.copy(vo, RptDataField.class));
                }
          }

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public Integer extractMetaDataCount(RptDataFieldVO vo) throws DbException {
        RptDatasource datasource = this.getDatasource(vo.getTableId());
        StringBuffer sql = new StringBuffer();
        sql.append("select COUNT(distinct(");
        sql.append(vo.getFieldCode());
        sql.append(")) from ");
        sql.append(vo.getTableCode());
        Integer i = reportHandler.queryDistinctCount(datasource.getDatasourceName(), sql.toString());

        return i;
    }

    @Override
    public ReportDetail getFieldsByMap(Map<String, Object> tmpMap) {
        ReportDetail reportDetail = new ReportDetail();

        String sql = tmpMap.get(AbstractAccessor.KEY_SQL).toString();

        List<Long> lds = (ArrayList)tmpMap.get(AbstractAccessor.KEY_FIELD_IDS);

        if(!CollectionUtils.isEmpty(lds)){
            List<RptDataField> rptDataFields = rdfMapper.selectByPrimaryKeys(lds);
            reportDetail.setFieldList(rptDataFields);
        }


        reportDetail.setSql(sql);
        return reportDetail;
    }

    @Override
    public Integer extractCount(Long fieldId) {

        RptDataField rptDataField = rdfMapper.selectByPrimaryKey(fieldId);
        if(rptDataField != null){
            String fieldCode = rptDataField.getFieldCode();
            RptDataTable rptDataTable = rptDataTableMapper.selectByTableId(rptDataField.getTableId());
            String tableCode = rptDataTable.getTableCode();
            String datasourceName = rptDataTable.getDatasourceName();

            StringBuffer sql = new StringBuffer();
            sql.append("select COUNT(distinct(");
            sql.append(fieldCode);
            sql.append(")) from ");
            sql.append(tableCode);
            return reportHandler.queryDistinctCount(datasourceName, sql.toString());
        }

        return 0;
    }

    @Override
    public List<RptDataDict> extractRealTimeMetadata(Long fieldId) {

        RptDataField rptDataField = rdfMapper.selectByPrimaryKey(fieldId);
        if(rptDataField != null) {
            String fieldCode = rptDataField.getFieldCode();
            RptDataTable rptDataTable = rptDataTableMapper.selectByTableId(rptDataField.getTableId());
            String tableCode = rptDataTable.getTableCode();
            String datasourceName = rptDataTable.getDatasourceName();

            StringBuffer sql = new StringBuffer();
            sql.append("select distinct(");
            sql.append(fieldCode);
            sql.append(") field from ");
            sql.append(tableCode);
            List<RptDataDict> list = reportHandler.queryDistinct(sql,datasourceName,rptDataTable.getTableId());

            return list;

        }

        return new ArrayList<>();
    }

    /**
     * 删除
     * @param vo
     * @return
     */
    public ServiceResponse<Integer> removeByPrimaryKey(RptDataFieldVO vo) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        sR.setRetContent(rdfMapper.deleteByPrimaryKey(vo.getFieldId()));
        return sR;
    }

    /**
     * 批量删除
     * @param vo
     * @return
     */
    public ServiceResponse<Integer> removeByIds(RptDataFieldVO vo) {
        ServiceResponse<Integer> sR = new ServiceResponse<>();
        List<Long> ids = vo.getIds();
        int i = 0;
        for (Long id : ids) {
            i = rdfMapper.deleteByPrimaryKey(id);
        }
        sR.setRetContent(i);
        return sR;
    }

    private RptDataTable getDataTable (Long tableId) {
        return rptDataTableMapper.selectByTableId(tableId);
    }

    private RptDatasource getDatasource (Long tableId) {
        return rptDatasourceMapper.selectByTableId(tableId);
    }
}
