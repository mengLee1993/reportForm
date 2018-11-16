package com.ebase.report.cube.charts;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 图形类型
 */
public enum ChartType {
    BAR("bar", "柱状图"),
    PIE("pie", "饼状图");

    private String chartType;
    private String chartName;

    ChartType(String type, String name) {
        this.chartType = type;
        this.chartName = name;
    }

    public String getChartType() {
        return this.chartType;
    }

    public String getChartName() {
        return chartName;
    }

    public ChartType getChartByType(String chartType) {
        ChartType returnValue = null;
        for (ChartType type : ChartType.values()) {
            if (type.getChartType().equals(chartType)) {
                returnValue = type;
            }
        }

        return returnValue;
    }

    public boolean equals(ChartType type) {
        if (type == null) {
            return false;
        }

        return this.getChartType().equals(type.getChartType());
    }

    @Override
    public String toString(){
        return this.getChartType();
    }
}
