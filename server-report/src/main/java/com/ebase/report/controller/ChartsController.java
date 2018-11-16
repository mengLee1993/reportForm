package com.ebase.report.controller;

import com.ebase.report.core.db.handler.ReportHandler;
import com.ebase.report.core.json.JsonRequest;
import com.ebase.report.core.json.JsonResponse;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.charts.HighCharts;
import com.ebase.report.cube.charts.pie.PieChart;
import com.ebase.report.model.dynamic.ReportDatasource;
import com.ebase.report.service.ReportService;
import com.ebase.report.cube.charts.bar.BarChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
@RestController
@RequestMapping("/report/charts")
public class ChartsController {
    private static Logger logger = LoggerFactory.getLogger(ChartsController.class);

    @Resource
    private ReportHandler reportHandler;

    @Autowired
    private ReportService reportService;

    // 柱状图
    @RequestMapping("/hcharts")
    public JsonResponse<Object> hCharts(@RequestBody JsonRequest<ReportDatasource> jsonRequest) {

        JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

        try {
            ReportDatasource reportDatasource = jsonRequest.getReqBody();

            // 生成 cube tree
            CubeTree cubeTree = reportService.reportCore(reportDatasource.getReportDynamicParam());

            cubeTree = reportHandler.report(reportDatasource, cubeTree);
            cubeTree.toCharts();

            // todo remove
            if(null == reportDatasource.getChartOptions()){
                reportDatasource.setChartOptions(HighCharts.getDefChartOption(cubeTree));
            }

            jsonResponse.setRspBody(HighCharts.getHighCharts(reportDatasource.getChartOptions(),cubeTree));

        } catch (Exception e) {
            logger.error("error = {}", e);
            jsonResponse.setRetCode(JsonResponse.SYS_EXCEPTION);
        }

        return jsonResponse;

    }

}
