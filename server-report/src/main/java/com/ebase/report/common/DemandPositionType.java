package com.ebase.report.common;

/**
 * 前端 参数类型
 * @Auther: wangyu
 */
public enum DemandPositionType {

    LINE("line","行"),
    COLUMN("column","列");

    private String type;

    private String name;

    DemandPositionType(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DemandPositionType getDemandPositionType(String type){
        DemandPositionType positionType = null;
        for(DemandPositionType demandPositionType : DemandPositionType.values()){
            if(demandPositionType.getType().equals(type)){
                positionType = demandPositionType;
            }
        }

        return positionType;
    }

    @Override
    public String toString(){
        return this.getType();
    }
}
