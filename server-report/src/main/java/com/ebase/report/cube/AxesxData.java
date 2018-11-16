package com.ebase.report.cube;

import java.util.List;

/**
 * @Auther: wangyu
 */
public class AxesxData {
    // 字段编码
    private String code;

    private String name;       //行名

    private String type;

    private Integer length; //从当前列 到最下面根的长度

    private Integer lev;// 层数

    private int subTotal;// subTotal

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private List<Object> val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public List<Object> getVal() {
        return val;
    }

    public void setVal(List<Object> val) {
        this.val = val;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }
}
