package com.ebase.report.model;

import com.ebase.report.core.pageUtil.PageDTO;
import com.ebase.report.cube.AxesxData;
import com.ebase.report.cube.AxesyData;
import com.ebase.report.cube.charts.ChartOption;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wangyu
 */
public class ReportRespDeteil {

    private List<String> headers; //列名

    private List<List<String>> dataList; //结果集

    /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;


    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<List<String>> getDataList() {
        return dataList;
    }

    public void setDataList(List<List<String>> dataList) {
        this.dataList = dataList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
