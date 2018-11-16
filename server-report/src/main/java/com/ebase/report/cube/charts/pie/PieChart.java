package com.ebase.report.cube.charts.pie;

import com.ebase.report.cube.charts.ChartTitle;

import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 饼状图
 */
public class PieChart {
    // 图表名称
    private ChartTitle title;

    private String name;
    private boolean colorByPoint;
    List<PieChartData> data;

    public ChartTitle getTitle() {
        return title;
    }

    public void setTitle(ChartTitle title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isColorByPoint() {
        return colorByPoint;
    }

    public void setColorByPoint(boolean colorByPoint) {
        this.colorByPoint = colorByPoint;
    }

    public List<PieChartData> getData() {
        return data;
    }

    public void setData(List<PieChartData> data) {
        this.data = data;
    }
}
