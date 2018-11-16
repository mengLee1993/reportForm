package com.ebase.report.model;

import com.ebase.report.cube.AxesxData;
import com.ebase.report.cube.AxesyData;
import com.ebase.report.cube.charts.ChartOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangyu
 */
public class ReportResp {
    private List<List<AxesxData>> axisxData;
    private List<List<AxesyData>> axisyData;
    private List<Object> cellList;
    private ChartOption chartOptions;

    public List<List<AxesxData>> getAxisxData() {
        return axisxData;
    }

    public void setAxisxData(List<List<AxesxData>> axisxData) {
        this.axisxData = axisxData;
    }

    public List<List<AxesyData>> getAxisyData() {
        return axisyData;
    }

    public void setAxisyData(List<List<AxesyData>> axisyData) {
        this.axisyData = axisyData;
    }

    public List<Object> getCellList() {
        return cellList;
    }

    public void setCellList(List<Object> cellList) {
        this.cellList = cellList;
    }

    public ChartOption getChartOptions() {
        return chartOptions;
    }

    public void setChartOptions(ChartOption chartOptions) {
        this.chartOptions = chartOptions;
    }
}
