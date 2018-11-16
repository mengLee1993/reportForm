package com.ebase.report.service.impl;

import com.ebase.report.dao.RptMeasuresMapper;
import com.ebase.report.dao.RptPersonalAnalysisMapper;
import com.ebase.report.model.RptMeasures;
import com.ebase.report.model.RptPersonalAnalysis;
import com.ebase.report.model.dynamic.ReportEchoBody;
import com.ebase.report.service.RptMeasuresService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RptMeasuresServiceImpl implements RptMeasuresService {

    @Autowired
    private RptMeasuresMapper rptMeasuresMapper;

    @Autowired
    private RptPersonalAnalysisMapper rptPersonalAnalysisMapper;

    @Override
    public int delRptMeasures(RptMeasures rptMeasures) {
        Long measureId = rptMeasures.getMeasureId();

        int i = rptMeasuresMapper.deleteByPrimaryKey(measureId);
        //通过json删除
        Long personalAnalysisId = rptMeasures.getPersonalAnalysisId(); //指标id
        if(personalAnalysisId != null){
            RptPersonalAnalysis rptPersonalAnalysis = rptPersonalAnalysisMapper.selectByPrimaryKey(personalAnalysisId);

            //删除 匹配的id
            ReportEchoBody reportEchoBody = rptPersonalAnalysis.getReportEchoBody();
            if(reportEchoBody != null && CollectionUtils.isNotEmpty(reportEchoBody.getRptMeasures())){
                Stream<RptMeasures> rptMeasuresStream = reportEchoBody.getRptMeasures().stream().filter(x ->
                        !x.getMeasureId().equals(measureId)
                );
                //Convert a Stream to List
                List<RptMeasures> result = rptMeasuresStream.collect(Collectors.toList());

                reportEchoBody.setRptMeasures(result);
            }

            //再存一下
            rptPersonalAnalysisMapper.updateByPrimaryKeySelective(rptPersonalAnalysis);
        }

        return i;
    }

    @Override
    public Integer getRptMeasuresByService(RptMeasures reqBody) {

        return rptMeasuresMapper.selectCountByService(reqBody);
    }
}
