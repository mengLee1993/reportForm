package com.ebase.report.cube.charts;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:数据列，图表上一个或多个数据系列，比如图表中的一条曲线，一个柱形
 */
public class ChartSeries {
    // 数据列名称
    private String seriesName;
    // x轴
    private boolean xAxis;
    // y轴
    private boolean yAxis;
    // 是否显示
    private boolean visible = true;
    // 子图类型
    private String subChartType;
    // 数值key
    private String cellKey;

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public boolean isxAxis() {
        return xAxis;
    }

    public void setxAxis(boolean xAxis) {
        this.xAxis = xAxis;
    }

    public boolean isyAxis() {
        return yAxis;
    }

    public void setyAxis(boolean yAxis) {
        this.yAxis = yAxis;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getSubChartType() {
        return subChartType;
    }

    public void setSubChartType(String subChartType) {
        this.subChartType = subChartType;
    }

    public String getCellKey() {
        return cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }
}
