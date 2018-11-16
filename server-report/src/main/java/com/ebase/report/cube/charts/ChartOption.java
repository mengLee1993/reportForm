package com.ebase.report.cube.charts;

import com.ebase.report.common.DemandPositionType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 图形参数配置
 */
public class ChartOption {
    // 图形类型: bar pie
    private ChartType chartType;
    // 按行/列显示？
    private DemandPositionType positionType;
    // 行序列
    private List<ChartSeries> lineSeries = new ArrayList<>();
    // 列序列
    private List<ChartSeries> columnSeries = new ArrayList<>();

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public DemandPositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(DemandPositionType positionType) {
        this.positionType = positionType;
    }

    public List<ChartSeries> getLineSeries() {
        return lineSeries;
    }

    public void setLineSeries(List<ChartSeries> lineSeries) {
        this.lineSeries = lineSeries;
    }

    public List<ChartSeries> getColumnSeries() {
        return columnSeries;
    }

    public void setColumnSeries(List<ChartSeries> columnSeries) {
        this.columnSeries = columnSeries;
    }

}
