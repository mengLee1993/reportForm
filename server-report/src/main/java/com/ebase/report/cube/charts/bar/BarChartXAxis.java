package com.ebase.report.cube.charts.bar;

import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 柱型图：x轴数据
 */
public class BarChartXAxis {
    private List<String> categories;
    private boolean crossHair;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public boolean isCrossHair() {
        return crossHair;
    }

    public void setCrossHair(boolean crossHair) {
        this.crossHair = crossHair;
    }
}
