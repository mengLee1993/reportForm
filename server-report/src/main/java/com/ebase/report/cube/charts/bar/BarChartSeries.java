package com.ebase.report.cube.charts.bar;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 柱状图-Series
 */
public class BarChartSeries {
    private String name;
    private List<BigDecimal> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BigDecimal> getData() {
        return data;
    }

    public void setData(List<BigDecimal> data) {
        this.data = data;
    }
}
