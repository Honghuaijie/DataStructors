package come.hong2.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: HuffmanTree
 * Package: come.hong2.huffmantree
 * Description:
 * 复习哈夫曼树
 * 什么是哈夫曼树？ 就是树的带权路径长度最小的二叉树，也称最优二叉树
 * 树的带权节点=所有叶子节点的带权路径长度之和
 * 如何将一个数组，转换成哈夫曼树
 * 步骤
 * 前置条件：将数组中每个数据转换成一个节点，并放入集合中
 * 1.将集合从小到大进行排序
 * 2.取出集合中的首两个元素（也就是当前集合中最小值和次小值）
 * 3.利用这两个元素的value之和重新组成一个新的元素作为他们两个的父节点，并放入集合中
 * 4.从集合中删除这两个元素
 *
 * @Author honghuaijie
 * @Create 2023/10/7 8:56
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


    public static Node createHuffmanTree(int[] arr) {
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            Node node = new Node(value);
            nodes.add(node);
        }

        while (nodes.size() > 1) {

            Collections.sort(nodes);

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            Node parentNode = new Node(leftNode.getValue() + rightNode.getValue());
            parentNode.setLeftNode(leftNode);
            parentNode.setRightNode(rightNode);

            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);
        }

        return nodes.get(0);

    }

    public static void preList(Node root) {
        if (root != null){
            root.preList();
        }else {
            System.out.println("当前二叉树为空");
        }
    }
}


//由于需要对集合进行排序，而如果集合想要排序那么集合中的元素就必须实现Comparable接口
class Node implements Comparable<Node> {
    private int value;
    private Node leftNode;
    private Node rightNode;

    public Node(int value) {
        this.value = value;
    }

    //前序遍历
    public void preList() {
        System.out.println(this);
        if (this.leftNode != null) {
            this.leftNode.preList();
        }
        if (this.rightNode != null) {
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

    @Override
    public int compareTo(Node o) {
        //从小到大进行排序
        return this.value - o.value;
    }
}
