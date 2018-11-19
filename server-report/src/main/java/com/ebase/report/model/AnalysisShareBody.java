package com.ebase.report.model;

//查看分享列表对象
public class AnalysisShareBody {

    private Long id; //报表id

    private Byte type;  //0  角色   1  账号

    private String name; //角色或账号名称

    /**
     */
    private Integer pageNum = 1;
    /**
     */
    private Integer pageSize = 10;

    private Integer startRow ;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }
}
