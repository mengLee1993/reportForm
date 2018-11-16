package com.ebase.report.cube.charts;

import com.ebase.report.common.DemandPositionType;
import com.ebase.report.common.DemandType;
import com.ebase.report.cube.CubeTree;
import com.ebase.report.cube.Dimension;
import com.ebase.report.cube.DimensionKey;
import com.ebase.report.cube.ManyTreeNode;
import com.ebase.report.cube.charts.bar.BarChart;
import com.ebase.report.cube.charts.bar.BarChartSeries;
import com.ebase.report.cube.charts.bar.BarChartXAxis;
import com.ebase.report.cube.charts.bar.BarChartYAxis;
import com.ebase.report.cube.charts.pie.PieChart;
import com.ebase.report.cube.charts.pie.PieChartData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: HCharts 图形
 */
public class HighCharts {
    private static Logger logger = LoggerFactory.getLogger(HighCharts.class);

    /**
     * 设置默认的图形配置
     *
     * @param cubeTree
     * @return
     */
    public static ChartOption getDefChartOption(CubeTree cubeTree) {
        ChartOption chartOption = new ChartOption();
        chartOption.setChartType(ChartType.BAR);
        chartOption.setPositionType(DemandPositionType.COLUMN);
        chartOption.setLineSeries(getChartSeries(cubeTree.getLineLeafList(), cubeTree));
        chartOption.setColumnSeries(getChartSeries(cubeTree.getColumnLeafList(), cubeTree));

        return chartOption;
    }

    /**
     * 获取柱状图
     *
     * @param chartOption
     * @param cubeTree
     * @return {
     * xxAxis: {
     * categories: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
     * crosshair: true
     * },
     * series: [{name: '东京',
     * data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
     * },
     * {name: '柏林',data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]}
     * ]
     * }
     */
    public static Object getHighCharts(ChartOption chartOption, CubeTree cubeTree){
        if(chartOption.getChartType().equals(ChartType.BAR)){
            return getBarCharts(chartOption, cubeTree);
        }else if(chartOption.getChartType().equals(ChartType.PIE)){
            return getPieCharts(chartOption, cubeTree);
        }
        return null;
    }


    private static BarChart getBarCharts(ChartOption chartOption, CubeTree cubeTree) {
        // 行序列
        List<ChartSeries> lineSeries;
        // 列序列
        List<ChartSeries> columnSeries;
        if (DemandPositionType.LINE.equals(chartOption.getPositionType())) {
            // 行 --->（x）分类轴
            lineSeries = chartOption.getLineSeries();
            columnSeries = chartOption.getColumnSeries();
        } else {
            // 列 --->（x）分类轴
            lineSeries = chartOption.getColumnSeries();
            columnSeries = chartOption.getLineSeries();
        }

        BarChart barChart = new BarChart();
        // 图表名称
        ChartTitle title = new ChartTitle();
        title.setText("");
        barChart.setTitle(title);

        // y轴
        BarChartYAxis chartYAxis = new BarChartYAxis();
        chartYAxis.setMin(0);
        ChartTitle yAxisTitle = new ChartTitle();
        yAxisTitle.setText("");
        chartYAxis.setTitle(yAxisTitle);
        barChart.setyAxis(chartYAxis);
        // X 轴(分类轴)
        BarChartXAxis chartXAxis = new BarChartXAxis();
        chartXAxis.setCrossHair(true);
        // 分类轴中的分类
        List<String> categories = new ArrayList<>();

        for (ChartSeries series : lineSeries) {
            if (series.isVisible()) {
                categories.add(series.getSeriesName());
            }
        }

        chartXAxis.setCategories(categories);
        barChart.setxAxis(chartXAxis);

        // bar 图表的数据列
        List<BarChartSeries> seriesList = new ArrayList<>();
        for (ChartSeries columnSer : columnSeries) {
            BarChartSeries barChartSeries = new BarChartSeries();
            barChartSeries.setName(columnSer.getSeriesName());
            List<BigDecimal> data = new ArrayList<>();
            for (ChartSeries lineSer : lineSeries) {
                if (lineSer.isVisible()) {
                    categories.add(lineSer.getSeriesName());
                    String cellKey = "";
                    if (DemandPositionType.LINE.equals(chartOption.getPositionType())) {
                        // 行 ---> 分类轴
                        cellKey = lineSer.getCellKey() + "#" + columnSer.getCellKey();
                    } else {
                        // 列 ---> 分类轴
                        cellKey = columnSer.getCellKey() + "#" + lineSer.getCellKey();
                    }
                    //
                    if (!cellKey.contains(DemandType.MEASURES.getCode())) {
                        cellKey = cellKey + new DimensionKey().getMeasuresKey(cubeTree.getDefReportMeasure().getKey());
                    }

                    BigDecimal dataValue = cubeTree.getCells().get(cellKey) == null ? new BigDecimal(0) : new BigDecimal(cubeTree.getCells().get(cellKey));
                    data.add(dataValue);
                }
            }

            barChartSeries.setData(data);
            seriesList.add(barChartSeries);
        }

        barChart.setSeries(seriesList);

        return barChart;
    }

    /**
     * 获取饼状图
     *
     * @param chartOption
     * @param cubeTree
     * @return series: [{name: 'Brands',colorByPoint: true,
     * data: [
     * {name: 'Chrome',y: 61.41,sliced: true,selected: true},
     * {name: 'Internet Explorer',y: 11.84},
     * {name: 'Edge',y: 4.67}
     * ]
     * }]
     */
    private static List<PieChart> getPieCharts(ChartOption chartOption, CubeTree cubeTree) {
        List<PieChart> list = new ArrayList<PieChart>();
        // 饼图个数
        List<ChartSeries> seriesName;
        // 数据个数
        List<ChartSeries> seriesData;
        if (DemandPositionType.LINE.equals(chartOption.getPositionType())) {
            seriesData = chartOption.getColumnSeries();
            seriesName = chartOption.getLineSeries();
        } else {
            seriesName = chartOption.getColumnSeries();
            seriesData = chartOption.getLineSeries();
        }

        for (ChartSeries nameSeries : seriesName) {
            // 有几个分类就有几个饼状图
            PieChart pieChart = new PieChart();
            pieChart.setName(nameSeries.getSeriesName());
            pieChart.setColorByPoint(true);

            // 图表名称
            ChartTitle title = new ChartTitle();
            title.setText(nameSeries.getSeriesName());
            pieChart.setTitle(title);

            List<PieChartData> dataList = new ArrayList<>();
            //
            for (ChartSeries dataSeries : seriesData) {
                PieChartData pieChartData = new PieChartData();
                pieChartData.setName(dataSeries.getSeriesName());
                // 获取数据
                String cellKey = "";
                if (DemandPositionType.LINE.equals(chartOption.getPositionType())) {
                    // 行 ---> 分类轴
                    cellKey = nameSeries.getCellKey() + "#" + dataSeries.getCellKey();
                } else {
                    // 列 ---> 分类轴
                    cellKey = dataSeries.getCellKey() + "#" + nameSeries.getCellKey();
                }
                //
                if (!cellKey.contains(DemandType.MEASURES.getCode())) {
                    cellKey = cellKey + new DimensionKey().getMeasuresKey(cubeTree.getDefReportMeasure().getKey());
                }else {

                }

                pieChartData.setY(new BigDecimal(cubeTree.getCellValue(cellKey)));

                dataList.add(pieChartData);
            }

            pieChart.setData(dataList);

            list.add(pieChart);
        }

        return list;
    }

    /**
     * 根据树的叶结点，获取图形数据列
     *
     * @param leafTreeNodeList
     * @return
     */
    private static List<ChartSeries> getChartSeries(List<ManyTreeNode> leafTreeNodeList, CubeTree cubeTree) {
        List<ChartSeries> chartSeries = new ArrayList<>();
        for (ManyTreeNode treeNode : leafTreeNodeList) {
            if (treeNode.getTreeNode().getNodeId().indexOf("subTotal") == -1) {
                ChartSeries series = new ChartSeries();

                series.setSeriesName(getSeriesName(treeNode.getTreeNode().getNodeId(),cubeTree));
                series.setCellKey(treeNode.getTreeNode().getNodeId());
                series.setVisible(true);

                chartSeries.add(series);
            }

        }
        return chartSeries;
    }

    private static String getSeriesName(String key, CubeTree cubeTree) {
        StringBuffer keyBuffer = new StringBuffer();

        String[] dimensions = key.split("#");

        for (int i = 0; i < dimensions.length; i++) {
            if (i > 0) {
                keyBuffer.append("_");
            }

            String str = dimensions[i];
            if(str.contains(DemandType.MEASURES.getCode())){
                // 指标
                // 维度
                String[] values = str.split("\\.");
                Dimension dimension = cubeTree.getDimensionMap().get(values[1]);
                if(null != dimension){
                    keyBuffer.append(dimension.getFieldName());
                }else {
                    keyBuffer.append(values[1]);
                }

            }else {
                // 维度
                String[] values = str.split(",");
                keyBuffer.append(values[1]);
            }
        }

        return keyBuffer.toString();
    }

}
