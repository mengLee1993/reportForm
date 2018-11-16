package com.ebase.report.cube;

import com.ebase.report.common.DemandPositionType;
import com.ebase.report.common.DemandType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * 维度数据
 * description:
 */
public class DimensionData {
    private String code;  //表字段值 select ?
    private String name;
    // 用来区分行还是列(line column)
    private DemandPositionType positionType = DemandPositionType.LINE;

    // 是否是维度 还是度量值 (dimension measures)
    private DemandType demandType = DemandType.DIMENSION;

    //查询值(不是度量值),只有type是维度的是才有值
    private Map<String, String> values = new LinkedHashMap<String, String>();

    // 层级
    private int lev;

    // 是否小计
    private boolean subtotal = false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DemandPositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(DemandPositionType positionType) {
        this.positionType = positionType;
    }

    public DemandType getDemandType() {
        return demandType;
    }

    public void setDemandType(DemandType demandType) {
        this.demandType = demandType;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public boolean isSubtotal() {
        return subtotal;
    }

    public void setSubtotal(boolean subtotal) {
        this.subtotal = subtotal;
    }
}
