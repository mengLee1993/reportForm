package com.ebase.report.model.dynamic;

import com.ebase.report.common.DBFieldTypeEnum;
import com.ebase.report.common.FilterTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 过滤区字段
 */
public class FilterArea {

    // 维度code
    private String code;   //name

    // 维度名称
    private String name;   //名称

    private DBFieldTypeEnum dbFieldTypeEnum = DBFieldTypeEnum.VARCHAR;  // 当前字段的类型 给我一个类型

    //添加组合名称
    private String combinationName;


//    private Map<FilterTypeEnum, List<String>> tmp = new HashMap<>();

    //过滤条件 key: < > = !=  value:值       最少四个值
    private List<FilterAreaValue> tmp = new ArrayList<>();


    private String filterJson; //前端保存字段 没软用

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

//    public Map<FilterTypeEnum, List<String>> getTmp() {
//        return tmp;
//    }
//
//    public void setTmp(Map<FilterTypeEnum, List<String>> tmp) {
//        this.tmp = tmp;
//    }


    public List<FilterAreaValue> getTmp() {
        return tmp;
    }

    public void setTmp(List<FilterAreaValue> tmp) {
        this.tmp = tmp;
    }

    public String getDbFieldTypeEnum() {
        if(dbFieldTypeEnum != null){
            return dbFieldTypeEnum.getName();
        }
        return null;
    }

    public void setDbFieldTypeEnum(String name) {
        this.dbFieldTypeEnum = DBFieldTypeEnum.getByName(name);
    }

    public String getFilterJson() {
        return filterJson;
    }

    public void setFilterJson(String filterJson) {
        this.filterJson = filterJson;
    }

    public String getCombinationName() {
        return combinationName;
    }

    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }
}
