package com.ebase.report.cube.charts.bar;

import com.ebase.report.cube.charts.ChartTitle;

import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 柱状图
 */
public class BarChart {
    private ChartTitle title;
    private BarChartXAxis xAxis;
    private BarChartYAxis yAxis;
    private List<BarChartSeries> series;

    public ChartTitle getTitle() {
        return title;
    }

    public void setTitle(ChartTitle title) {
        this.title = title;
    }

    public BarChartXAxis getxAxis() {
        return xAxis;
    }

    public void setxAxis(BarChartXAxis xAxis) {
        this.xAxis = xAxis;
    }

    public BarChartYAxis getyAxis() {
        return yAxis;
    }

    public void setyAxis(BarChartYAxis yAxis) {
        this.yAxis = yAxis;
    }

    public List<BarChartSeries> getSeries() {
        return series;
    }

    public void setSeries(List<BarChartSeries> series) {
        this.series = series;
    }
}
