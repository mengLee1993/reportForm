package com.ebase.report.cube;

import com.ebase.report.common.DemandType;
import com.ebase.report.cube.TreeNode;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:  用于组装 维度的key
 */
public class DimensionKey {
    private StringBuilder key = new StringBuilder();
    private Map<String, String> dimensionMap = new HashMap<>();

    public void addDimension(String dimensionCode, String dimensionValue){
//        key.append("[");
        if(!StringUtils.isEmpty(key.toString())){
            key.append("#");
        }
        key.append(dimensionCode);
        key.append(",");
        key.append(dimensionValue);

        dimensionMap.put(dimensionCode, dimensionValue);
    }

    public void addMeasures(String measure, String value){
        dimensionMap.put(measure, value);
    }

    @Override
    public String toString(){
        return key.toString();
    }

    public StringBuilder getKey() {
        return key;
    }

    public void setKey(StringBuilder key) {
        this.key = key;
    }

    public Map<String, String> getDimensionMap() {
        return dimensionMap;
    }

    public void setDimensionMap(Map<String, String> dimensionMap) {
        this.dimensionMap = dimensionMap;
    }

    public String getMeasuresKey(String columnName){
        return this.key.toString()+ "#"+ DemandType.MEASURES.getCode()+"." + columnName;
    }

}
