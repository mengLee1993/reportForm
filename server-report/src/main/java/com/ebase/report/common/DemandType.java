package com.ebase.report.common;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 指标需求类型：维度、度量值
 */
public enum DemandType {
    //dimension
    DIMENSION("dimension","维度", "F"),
    //measures
    MEASURES("measures","度量值", "M"),
    // none
    NONE("none","默认", "N");

    private String code;
    private String name;
    private String prefix;

    DemandType(String code, String name, String prefix){
        this.code = code.toUpperCase();
        this.name = name;
        this.prefix = prefix;
    }

    public String getPrefix(){
        return prefix;
    }

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

    public DemandType getDemandTypeByCode(String code){
        DemandType demandType = null;
        for(DemandType type : DemandType.values()){
            if(type.getCode().equals(code)){
                demandType = type;
            }
        }

        return demandType;
    }

    public boolean equals(DemandType type){
        if(type == null){
            return false;
        }

        return this.getCode().equals(type.getCode());
    }
}
