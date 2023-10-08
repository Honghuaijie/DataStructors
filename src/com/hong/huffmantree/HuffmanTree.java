package com.hong.huffmantree;

import org.junit.Test;

import java.util.*;

/**
 * ClassName: HuffmanTree
 * Package: com.hong.huffmantree
 * Description:
 *   写一个哈夫曼树
 *   什么是哈夫曼树，就是WPL 最短的二叉树
 *   什么是WPL？ 就是树的权值路径
 *
 *   前提条件，将传入的数组中的元素设置成一个节点，并用集合包裹
 *   步骤
 *   1.将集合从小到大进行排序
 *   2.取出集合中的最小值
 *   3.取出集合中的最大值
 *   4.创建这两个节点的父节点，并将这个父节点的左右节点设置为这两个节点
 *   5.将集合中取出的两个节点remove掉，并将此父节点放入集合中
 *
 *
 * @Author honghuaijie
 * @Create 2023/10/6 12:20
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13,7,8,3,29,6,1};
        Node root = createHuffmanTree(arr);

        preList(root);
    }




    public static void preList(Node root){
        if (root !=null){
            root.preList();
        }else{
            System.out.println("该二叉树为空");
        }
    }

    /**
     *
     * @param arr 需要创建成哈夫曼树的数组
     * @return 返回哈夫曼树的头结点
     */
    //创建哈夫曼树
    public static Node createHuffmanTree(int[] arr){
        List<Node> nodes = new ArrayList<Node>();
        //前提：需要将数组中的元素放入到集合中
        for (int value : arr){
            Node node = new Node(value);
            nodes.add(node);
        }

        //当集合中只有一个元素的时候，结束循环
        while (nodes.size() > 1){
            //1.将集合从小到大进行排序
            Collections.sort(nodes);
            //2.取出集合中的最小值
            Node leftNode = nodes.get(0);
            //3.取出集合中的次小值
            Node rightNode = nodes.get(1);
            //4.创建这两个节点的父节点，并将这个父节点的左右节点设置为这两个节点
            Node parentNode = new Node(leftNode.getValue() + rightNode.getValue());
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);
            //5.将集合中取出的两个节点remove掉，并将此父节点放入集合中
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);
        }

        //集合中的最后一个元素就是根节点
        return nodes.get(0);
    }
}

//节点元素
//由于此节点是放在集合中，而如果使用Collection中的sort方法必须要节点实现Comparator接口
class Node implements Comparable<Node> {
    private int value;
    private Node leftNode;
    private Node rightNode;

    public Node(int value){
        this.value = value;
    }

    //写一个前序遍历
    public void preList(){
        System.out.println(this);

        if (this.leftNode !=null){
            this.leftNode.preList();
        }

        if (this.rightNode !=null){
            this.rightNode.preList();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    //从小到大排序
    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
