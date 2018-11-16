package com.ebase.report.cube;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 多叉树对象
 */
public class ManyNodeTree {
    private ManyTreeNode root;

    /**
     * 构造函数
     */
    public ManyNodeTree() {
        root = new ManyTreeNode(new TreeNode("root"));
    }

    public void addChildes(List<TreeNode> treeNodes){
        //将所有节点添加到多叉树中
        for (TreeNode treeNode : treeNodes) {
            if (treeNode.getParentId().equals("root")) {
                //向根添加一个节点
                this.getRoot().getChildList().add(new ManyTreeNode(treeNode));
            } else {
                addChild(this.getRoot(), treeNode);
            }
        }
    }
    /**
     * 向指定多叉树节点添加子节点
     *
     * @param manyTreeNode 多叉树节点
     * @param child        节点
     */
    public void addChild(ManyTreeNode manyTreeNode, TreeNode child) {
        for (ManyTreeNode item : manyTreeNode.getChildList()) {
            if (item.getTreeNode().getNodeId().equals(child.getParentId())) {
                //找到对应的父亲
                item.getChildList().add(new ManyTreeNode(child));
                break;
            } else {
                if (item.getChildList() != null && item.getChildList().size() > 0) {
                    addChild(item, child);
                }
            }
        }
    }

    /**
     * 遍历打印树节点
     *
     * @param manyTreeNode 多叉树节点
     * @return
     */
    public String printTree(ManyTreeNode manyTreeNode) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n");

        if (manyTreeNode != null) {
            for (ManyTreeNode index : manyTreeNode.getChildList()) {
                buffer.append(index.getTreeNode().getNodeId());
                buffer.append("\n");
                if (index.getChildList() != null && index.getChildList().size() > 0) {
                    buffer.append(printTree(index));
                }
            }
        }

        buffer.append("\n");

        return buffer.toString();
    }

    /**
     * 计算指定多叉树节点下的叶子节点总数
     * @param manyTreeNode
     * @param size
     * @return
     */
    public int leafNodeSize(ManyTreeNode manyTreeNode, Integer size){
        if(manyTreeNode.isLeaf()){
            size++;
        }
        for(ManyTreeNode child : manyTreeNode.getChildList()) {
            if(child != null) {
                size = leafNodeSize(child, size);
            }
        }

        return size;
    }

    /**
     * 得到节点下的所有叶节点集合
     * @param manyTreeNode
     * @return
     */
    public List<ManyTreeNode> leafNodeList(ManyTreeNode manyTreeNode){
        List<ManyTreeNode> leafNodeList = new ArrayList<>();
        if(manyTreeNode.isLeaf()){
            leafNodeList.add(manyTreeNode);
        }
        for(ManyTreeNode child : manyTreeNode.getChildList()) {
            if(child != null) {
                leafNodeList.addAll(leafNodeList(child));
            }
        }

        return leafNodeList;
    }

    /**
     * 得到节点下的所有非叶节点
     * @param manyTreeNode
     * @return
     */
    public List<ManyTreeNode> elderNodeList(ManyTreeNode manyTreeNode){
        List<ManyTreeNode> elderNodeList = new ArrayList<>();
        if(!manyTreeNode.isLeaf()){
            elderNodeList.add(manyTreeNode);
        }
        for(ManyTreeNode child : manyTreeNode.getChildList()) {
            if(child != null) {
                elderNodeList.addAll(elderNodeList(child));
            }
        }

        return elderNodeList;
    }

    /**
     * 计算指定多叉树节点下的，树的深度
     * @param manyTreeNode
     * @return
     */
    public int depthOfTree(ManyTreeNode manyTreeNode){
        int deep = manyTreeNode.getChildList().size();
        System.out.println(manyTreeNode.getTreeNode().getNodeId()+":"+deep);
        if(deep==0){
            return 1;
        }else{
            int childNum = manyTreeNode.getChildList().size();
            int[] count = new int[childNum];
            for(int i = 0;i<childNum;i++){
                count[i] = depthOfTree(manyTreeNode.getChildList().get(i))+1;
            }
            int maxCount = count[0];
            for(int i = 1;i<count.length;i++){
                if(maxCount<count[i]){
                    maxCount = count[i];
                }
            }
            return maxCount;
        }
    }

    /**
     * 根据nodeId找到树中某一个节点
     * @param t
     * @param nodeId
     * @return
     */
    public ManyTreeNode findTreeNodeById(ManyTreeNode t, String nodeId) {
        if(nodeId.equals(t.getTreeNode().getNodeId())){
            return t;
        }else{
            List<ManyTreeNode> childes = t.getChildList();
            for(ManyTreeNode manyTreeNode : childes){
                ManyTreeNode treeNode = findTreeNodeById(manyTreeNode, nodeId);
                if(treeNode != null){
                    return treeNode;
                }
            }
        }

        return null;
    }

    /**
     * 遍历树，将treeNode放到对应的lev list中
     * @param manyTreeNode
     * @param arrayLists
     */
    public void treeNodeToLevList(ManyTreeNode manyTreeNode, ArrayList<AxesyData>[] arrayLists) {

        if (manyTreeNode != null) {
            for (ManyTreeNode treeNode : manyTreeNode.getChildList()) {
                AxesyData axesyData = new AxesyData();
                axesyData.setName(treeNode.getTreeNode().getName());
                axesyData.setLength(treeNode.getTreeNode().getLength());
                axesyData.setLev(treeNode.getTreeNode().getLev());
                axesyData.setCode(treeNode.getTreeNode().getCode());
                if(treeNode.getTreeNode().isSubTotal()){
                    axesyData.setSubTotal(1);
                    arrayLists[arrayLists.length - axesyData.getLev()].add(axesyData);
                }else{
                    arrayLists[arrayLists.length - axesyData.getLev()].add(axesyData);
                }

                if (treeNode.getChildList() != null && treeNode.getChildList().size() > 0) {
                    treeNodeToLevList(treeNode, arrayLists);
                }
            }
        }
    }

    public ManyTreeNode getRoot() {
        return root;
    }

    public void setRoot(ManyTreeNode root) {
        this.root = root;
    }

}
