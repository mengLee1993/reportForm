package com.ebase.report.cube;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 多叉树结点
 */
public class ManyTreeNode {
    // 树结点数据
    private TreeNode treeNode;
    // 子树集合
    private List<ManyTreeNode> childList;

    public ManyTreeNode(TreeNode treeNode){
        this.treeNode = treeNode;
        this.childList = new ArrayList<ManyTreeNode>();
    }

    public ManyTreeNode(TreeNode treeNode, List<ManyTreeNode> childList){
        this.treeNode = treeNode;
        this.childList = childList;
    }

    /**
     * 是否叶子结点
     * @return
     */
    public boolean isLeaf() {
        if(childList.isEmpty() && !"root".equals(this.treeNode.getNodeId()))
            return true;
        return false;
    }

    /**
     * 返回当前节点的父辈节点集合
     * @return
     */
    public List<ManyTreeNode> getElders(ManyNodeTree tree) {
        List<ManyTreeNode> elderList = new ArrayList<ManyTreeNode>();
        String parentId = this.getTreeNode().getParentId();
        ManyTreeNode parentNode = tree.findTreeNodeById(tree.getRoot(), parentId);
        if (StringUtils.isEmpty(parentNode.getTreeNode().getParentId())) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders(tree));
            return elderList;
        }
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public List<ManyTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<ManyTreeNode> childList) {
        this.childList = childList;
    }
}
