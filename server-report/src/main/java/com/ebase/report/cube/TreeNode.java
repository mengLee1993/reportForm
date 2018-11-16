package com.ebase.report.cube;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 树结点
 */
public class TreeNode {

    // 节点ID dimension.value
    private String nodeId;
    // 父节点ID
    private String parentId;
    //表字段值 select ?
    private String code;
    // 名称
    private String name;
    // 层级，前端json需要
    private int lev;
    // 行数量（列数量）
    private int length = 1;

    private boolean subTotal = false;
    private String subKey;

    List<Object> cells = new ArrayList<Object>();

    public TreeNode(String nodeId){
        this.nodeId = nodeId;
    }

    public TreeNode(String nodeId, String parentId){
        this.nodeId = nodeId;
        this.parentId = parentId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isSubTotal() {
        return subTotal;
    }

    public void setSubTotal(boolean subTotal) {
        this.subTotal = subTotal;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public List<Object> getCells() {
        return cells;
    }

    public void setCells(List<Object> cells) {
        this.cells = cells;
    }
}
