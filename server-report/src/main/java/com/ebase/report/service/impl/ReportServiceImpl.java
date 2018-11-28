package com.ebase.report.service.impl;

import com.ebase.report.common.*;
import com.ebase.report.core.session.AssertContext;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.Dimension;
import com.ebase.report.dao.*;
import com.ebase.report.model.RptDataField;
import com.ebase.report.model.RptMeasures;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.RptPersonalSubject;
import com.ebase.report.model.dynamic.ReportDynamicParam;
import com.ebase.report.model.dynamic.ReportEchoBody;
import com.ebase.report.model.dynamic.ReportMeasure;
import com.ebase.report.service.ReportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private static Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    //  主题
    @Autowired
    private RptPersonalSubjectMapper rptPersonalSubjectMapper;

    // 字段
    @Autowired
    private RptDataFieldMapper rptDataFieldMapper;

    // 指标
    @Autowired
    private RptMeasuresMapper rptMeasuresMapper;

    // 元数据
    @Autowired
    private RptDataDictMapper rptDataDictMapper;

    // 自定义报表
    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper;
    /**
     * 获得主题和 数据源信息
     * @param userId  创建人
     * @return
     */
    @Override
    public List<ReportEchoBody> getThemeDataSource(Long userId) {
        Long acctId = AssertContext.getAcctId();
        if(acctId == null){
            acctId = 1L;
        }
//        Long

        //当前角色id
        Long roleId = AssertContext.getRoleId();
        if(roleId == null){
            roleId = 1L;
        }
        Map<String,Object> tmp = new HashMap<>();
        tmp.put("acctId",acctId);
        tmp.put("roleId",roleId);

        List<RptPersonalSubject> rptPersonalSubjects = rptPersonalSubjectMapper.selectByCreatedBy(tmp);


        List<ReportEchoBody> result = new ArrayList<>(rptPersonalSubjects.size());
        rptPersonalSubjects.forEach(x -> {
            Long personalSubjectId = x.getPersonalSubjectId();
            ReportEchoBody themeDataSource = getReportEchoBody(personalSubjectId);
//            themeDataSource.setSubjectName(x.getSubjectName());
            result.add(themeDataSource);
        });



        return result;
    }


    /**
     * 根据主题ID获得单个主题对象
     * @param personalSubjectId
     * @return
     */
    @Override
    public List<ReportEchoBody> getThemeDataSourceByPersonalSubjectId(Long personalSubjectId) {
        ReportEchoBody themeDataSource = getReportEchoBody(personalSubjectId);
        List<ReportEchoBody> result = new ArrayList<>();
        result.add(themeDataSource);
        return result;
    }

    /**
     * 获得主题数据
     * @param personalSubjectId
     * @return
     */
    public ReportEchoBody getReportEchoBody(Long personalSubjectId) {

        Long acctId = AssertContext.getAcctId();  //当前登陆人
        if(acctId == null){
            acctId = 1L;
        }

        //获得主数据
        ReportEchoBody themeDataSource = rptPersonalSubjectMapper.getThemeDataSource(personalSubjectId);
        themeDataSource.setPersonalSubjectId(personalSubjectId);
        if(themeDataSource!= null && themeDataSource.getSubjectType().equals(SubjectTypeEnum.DATATABLE.getCode())){
            //是数据
            Long tableId = themeDataSource.getReportTables().get(0).getTableId();

            Map<String,Object> tmp = new HashMap<>();
            tmp.put("tableId",tableId);
            tmp.put("dimensionIndex", DemandType.DIMENSION.getCode());
            //查出所有的字段  维度
            List<RptDataField> rptDataFieldList = rptDataFieldMapper.selectByTableId(tmp);
            themeDataSource.setRptDataFields(rptDataFieldList);

            tmp.put("dimensionIndex",DemandType.MEASURES.getCode());
            List<RptDataField> rptDataIndexs =rptDataFieldMapper.selectByTableId(tmp);
            themeDataSource.setRptDataIndexs(rptDataIndexs);

            List<RptMeasures> rptMeasures = null;  //这个指标是库里的 然后自己再生成一些
            //查询类型 如果是系统分享的 就不根据当前登陆人
            if(SubjectSourceEnum.share.getCode().equals(themeDataSource.getSubjectSourceEnum())){
                rptMeasures = rptMeasuresMapper.selectBySubjectId(personalSubjectId);
            }else{

                rptMeasures = rptMeasuresMapper.selectBySubjectIdAcctId(personalSubjectId,acctId);
            }

            List<RptMeasures> list = getRptMeasures(personalSubjectId, rptDataIndexs);

            rptMeasures.addAll(list);
            themeDataSource.setRptMeasures(rptMeasures);

        }else{

        }
        return themeDataSource;
    }

    private List<RptMeasures> getRptMeasures(Long personalSubjectId, List<RptDataField> rptDataIndexs) {
        Long acctId = AssertContext.getAcctId();
        if(acctId == null){
            acctId = 1l;
        }

        List<RptMeasures> list = new ArrayList<>(rptDataIndexs.size());
        //初始化一些指标
        int i = 0;
        for(RptDataField x:rptDataIndexs){
            RptMeasures rptMeasures1 = new RptMeasures();
            rptMeasures1.setMeasureId(new Date().getTime() + acctId + i ++);
            String measuresName = x.getFieldName() + "(" + MeasureTypeEnum.SUM.getName() + ")";
            rptMeasures1.setMeasuresName(measuresName);
            rptMeasures1.setFieldId(x.getFieldId());
            rptMeasures1.setFieldCode(x.getFieldCode());
            rptMeasures1.setFieldName(x.getFieldName());
            rptMeasures1.setMeasureType(MeasureTypeEnum.SUM.getCode());
            rptMeasures1.setSubjectId(personalSubjectId);
            rptMeasures1.setIsSystem((byte)1);
            list.add(rptMeasures1);
        }
        ;
        return list;
    }

    @Override
    public Long addRptMeasures(RptMeasures reqBody) {
        reqBody.setCreatedDt(new Date());
        reqBody.setRemoveStatus((byte)RemoveStatusEnum.NOREMOVE.getRemoveStatus());

        if(!MeasureTypeEnum.CUSTOM.getCode().equals(reqBody.getMeasureType().toUpperCase())){
            //是系统
            Long fieldId = reqBody.getFieldId();
            RptDataField rptDataField = rptDataFieldMapper.selectByPrimaryKey(fieldId);
            String measuresName = rptDataField.getFieldName() + "(" + MeasureTypeEnum.getMeasureTypeEnumByCode(reqBody.getMeasureType()).getName() + ")";
            //生成指标名称
            reqBody.setMeasuresName(measuresName);
        }

        //
        if(reqBody.getMeasureId() == null){
            rptMeasuresMapper.insertSelective(reqBody);
        }else{
            rptMeasuresMapper.updateByPrimaryKeySelective(reqBody);
        }


        Long personalAnalysisId = reqBody.getPersonalAnalysisId();
        if(personalAnalysisId != null){
            //需要维护json
            RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisMapper.selectByPrimaryKey(personalAnalysisId);

            ReportEchoBody reportEchoBody = rptPersonalAnalysis.getReportEchoBody();

            Long measureId = reqBody.getMeasureId();
            Stream<RptMeasures> rptMeasuresStream = reportEchoBody.getRptMeasures().stream().filter(x -> !x.getMeasureId().equals(measureId));

            //Convert a Stream to List
            List<RptMeasures> result = rptMeasuresStream.collect(Collectors.toList());

            result.add(reqBody);
            reportEchoBody.setRptMeasures(result);

            rptPersonalAnalysisMapper.updateByPrimaryKeySelective(rptPersonalAnalysis);

        }

        return reqBody.getMeasureId();

    }

    @Override
    public Long addCustomReport(RptPersonalAnalysis reqBody) {

        ReportEchoBody reportEchoBody = reqBody.getReportEchoBody();
        if(reportEchoBody != null){
            reqBody.setPersonalSubjectId(reportEchoBody.getPersonalSubjectId());
        }

        reqBody.setCreatedDt(new Date());
        if(reqBody.getPersonalAnalysisId() == null){
            rptPersonalAnalysisMapper.insertSelective(reqBody);
        }else{
            rptPersonalAnalysisMapper.updateByPrimaryKeySelective(reqBody);
        }

        return reqBody.getPersonalAnalysisId();
    }

    @Override
    public RptPersonalAnalysis getCustomReport(Long personalAnalysisId) {
        return rptPersonalAnalysisMapper.selectByPrimaryKey(personalAnalysisId);
    }

    /**
     * 动态 报表核心类
     */
    @Override
    public CubeTree reportCore(ReportDynamicParam reportDynamicParam) {

        int lineLev = reportDynamicParam.getLine().size();

        //所有度量值
        List<ReportMeasure> mensions = new ArrayList<>();
        Set<ReportMeasure> custList = new HashSet<>(); //放自定义维度list
        boolean isDimension = false;
        for (Dimension dimension : reportDynamicParam.getLine()) {
            dimension.setLev(lineLev--);
            dimension.setPosition(DemandPositionType.LINE.getType());

            if (DemandType.MEASURES.equals(dimension.getDemandType())) {
//                reportDynamicParam.get
                for (ReportMeasure measure : dimension.getRptMeasures()) {
                    if(measure.getIsChecked() == 0){
                        continue;
                    }
                    measure.setPosition(DemandPositionType.LINE.getType());
                    measure.setLev(dimension.getLev());
                    measure.setDemandType(DemandType.MEASURES);
//                    ReportMeasure copy = BeanCopyUtil.copy(measure, ReportMeasure.class);

                    //如果是自定义的
                    isCustom(mensions, custList, measure);
                }
            }else{
                isDimension = true;
            }
        }
        int columnLev = reportDynamicParam.getColumn().size();
        for (Dimension dimension : reportDynamicParam.getColumn()) {
            dimension.setLev(columnLev--);
            dimension.setPosition(DemandPositionType.COLUMN.getType());

            if (DemandType.MEASURES.equals(dimension.getDemandType())) {
                for (ReportMeasure measure : dimension.getRptMeasures()) {
                    if(measure.getIsChecked() == 0){
                        continue;
                    }
                    measure.setPosition(DemandPositionType.COLUMN.getType());
                    measure.setLev(dimension.getLev());
                    measure.setDemandType(DemandType.MEASURES);
                    isCustom(mensions, custList, measure);

                }
            }else{
                isDimension = true;
            }
        }


        //生成树
        CubeTree cubeTree = new CubeTree();

        cubeTree.setMeasures(mensions,custList);
//        if(!CollectionUtils.isEmpty(custList)){
//        custList.addAll(mensions);
//        }
        reportDynamicParam.setMeasures(custList);
        reportDynamicParam.setDimension(isDimension);
        cubeTree.setLineDimension(reportDynamicParam.getLine());
        cubeTree.setColumnDimension(reportDynamicParam.getColumn());

        return cubeTree;
    }

    private void isCustom(List<ReportMeasure> mensions, Set<ReportMeasure> custList, ReportMeasure measure) {
        //如果是自定义的
        if (measure.getMeasureType().equals(MeasureTypeEnum.CUSTOM)) {
            ReportMeasure reportMeasure = measure.getReportMeasure();
            reportMeasure.getCustomIndexTmp().forEach(x -> {
                x.setDemandType(DemandType.MEASURES);
            });

            reportMeasure.setDemandType(DemandType.MEASURES);
            reportMeasure.setMeasureType(MeasureTypeEnum.CUSTOM);
            reportMeasure.setFieldId(String.valueOf(System.currentTimeMillis()));
            reportMeasure.setFieldName(measure.getFieldName());
            reportMeasure.setCombinationName(measure.getCombinationName());
            mensions.add(reportMeasure);
            custList.addAll(reportMeasure.getCustomIndexTmp());
        } else {
            mensions.add(measure);
            custList.add(measure);
        }
    }

    @Override
    public List<RptDataField> getRptDataField(Long personalSubjectId) {

        RptPersonalSubject rptPersonalSubject = rptPersonalSubjectMapper.selectByPrimaryKey(personalSubjectId);

        if(rptPersonalSubject != null){
            Long tableId = rptPersonalSubject.getTableId();

            Map<String,Object> tmp = new HashMap<>();
            tmp.put("tableId",tableId);
            tmp.put("dimensionIndex",DemandType.MEASURES.getCode());

            List<RptDataField> rptDataFields = rptDataFieldMapper.selectByTableId(tmp);

            return rptDataFields;
        }
        return new ArrayList<>();
    }

    @Override
    public List<RptMeasures> getRptMeasuresSystem(Long personalSubjectId) {
        //先查询数据表
        RptPersonalSubject rptPersonalSubject = rptPersonalSubjectMapper.selectByPrimaryKey(personalSubjectId);

        List<RptMeasures> result = new ArrayList<>();
        if(rptPersonalSubject != null){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("tableId",rptPersonalSubject.getTableId());
            tmp.put("dimensionIndex",DemandType.MEASURES.getCode());
            //根据指标字段生成指标
            List<RptDataField> rptDataIndexs =rptDataFieldMapper.selectByTableId(tmp);
            result = getRptMeasures(personalSubjectId, rptDataIndexs);
            //生成指标
            List<RptMeasures> rptMeasures = rptMeasuresMapper.selectSystemBySubjectId(personalSubjectId);
            result.addAll(rptMeasures);
        }

        return  result;
    }


}
