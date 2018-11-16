package com.ebase.report.cube;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */
public class TreeTest {
    public static void main(String[] args){
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        treeNodes.add(new TreeNode("area.中国", "root"));

        treeNodes.add(new TreeNode("area.中国#movie_type.体育", "area.中国"));
        treeNodes.add(new TreeNode("area.中国#movie_type.体育#movie_emotion.动作", "area.中国#movie_type.体育"));
        treeNodes.add(new TreeNode("area.中国#movie_type.体育#movie_emotion.喜剧", "area.中国#movie_type.体育"));
        treeNodes.add(new TreeNode("area.中国#movie_type.体育#movie_emotion.推理", "area.中国#movie_type.体育"));

        treeNodes.add(new TreeNode("area.中国#movie_type.历史", "area.中国"));
        treeNodes.add(new TreeNode("area.中国#movie_type.历史#movie_emotion.爱情", "area.中国#movie_type.历史"));
        treeNodes.add(new TreeNode("area.中国#movie_type.历史#movie_emotion.推理", "area.中国#movie_type.历史"));


        treeNodes.add(new TreeNode("area.美国", "root"));

        treeNodes.add(new TreeNode("area.美国#movie_type.体育", "area.美国"));
        treeNodes.add(new TreeNode("area.美国#movie_type.体育#movie_emotion.动作", "area.美国#movie_type.体育"));
        treeNodes.add(new TreeNode("area.美国#movie_type.体育#movie_emotion.爱情", "area.美国#movie_type.体育"));

        treeNodes.add(new TreeNode("area.美国#movie_type.历史", "area.美国"));
        treeNodes.add(new TreeNode("area.美国#movie_type.历史#movie_emotion.动作", "area.美国#movie_type.历史"));
        treeNodes.add(new TreeNode("area.美国#movie_type.历史#movie_emotion.爱情", "area.美国#movie_type.历史"));
        treeNodes.add(new TreeNode("area.美国#movie_type.历史#movie_emotion.推理", "area.美国#movie_type.历史"));
        treeNodes.add(new TreeNode("area.美国#movie_type.历史#movie_emotion.惊悚", "area.美国#movie_type.历史"));


        ManyNodeTree tree = new ManyNodeTree();
        tree.addChildes(treeNodes);

        tree.addChild(tree.getRoot(), new TreeNode("小计【area.美国】","area.美国"));

        tree.addChild(tree.getRoot(), new TreeNode("小计【area.美国#movie_type.体育】","area.美国#movie_type.体育"));
        tree.addChild(tree.getRoot(), new TreeNode("小计【area.美国#movie_type.历史】","area.美国#movie_type.历史"));

        System.out.println(tree.printTree(tree.getRoot()));

        System.out.println("------整棵树的叶节点-------");
        System.out.println("叶节点数量："+tree.leafNodeSize(tree.getRoot(), 0));

        for(ManyTreeNode treeNode : tree.getRoot().getChildList()){
            System.out.println(treeNode.getTreeNode().getNodeId()+"---的叶节点---");
            System.out.println(treeNode.getTreeNode().getNodeId()+"---叶节点数量："+tree.leafNodeSize(treeNode, 0));

            System.out.println(treeNode.getTreeNode().getNodeId()+"---树深度："+tree.depthOfTree(treeNode));
        }

        ManyTreeNode treeNode = tree.findTreeNodeById(tree.getRoot(), "area.美国#movie_type.历史#movie_emotion.惊悚");
        System.out.println("查找 area.美国#movie_type.历史#movie_emotion.惊悚 "+treeNode.getTreeNode().getNodeId());

        // 得到当前结点的所有父级结点
        List<ManyTreeNode> list = treeNode.getElders(tree);
        System.out.println("--- area.美国#movie_type.历史#movie_emotion.惊悚 ---");
        System.out.println("--- 所有父级nodes ---");
        for(ManyTreeNode code : list){
            System.out.println("---nodeId:"+code.getTreeNode().getNodeId());
        }

        System.out.println("--- 得到树的所有叶子结点 ---");
        ManyTreeNode manyTreeNode = tree.getRoot().getChildList().get(1);
        List<ManyTreeNode> leafNodeList = tree.leafNodeList(manyTreeNode);
        for(ManyTreeNode node : leafNodeList){
            System.out.println("---"+node.getTreeNode().getNodeId());
        }
    }
}
