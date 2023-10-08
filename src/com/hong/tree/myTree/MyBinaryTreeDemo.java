package com.hong.tree.myTree;

/**
 * ClassName: MyBinaryTreeDemo
 * Package: com.hong.tree.myTree
 * Description:
 *      复习二叉树的前序查找吧
 * @Author honghuaijie
 * @Create 2023/10/4 9:45
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyBinaryTreeDemo {
    public static void main(String[] args) {
        //创建一个二叉树
        MyBinaryTree mb = new MyBinaryTree();
        Node node1 = new Node(1,"宋江");
        Node node2 = new Node(2,"吴用");
        Node node3 = new Node(3,"卢俊义");
        Node node4 = new Node(4,"武松");
        Node node5 = new Node(5,"及时雨");
        node1.setLeft(node2);
        node1.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node4);
        mb.setRoot(node1);
        mb.preOrder();
        System.out.println();

        mb.preSearch(3);
    }

}

class MyBinaryTree{
    private Node root; //根节点

    //前序遍历
    public void preOrder(){
        if (this.root !=null){
            this.root.preOrder();
        }else{
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //前序查找
    public void preSearch(int id){
        if (this.root != null){
            Node node = this.root.preSearch(id);
            if (node !=null){
                System.out.println(node);
            }else{
                System.out.println("没有找到");
            }
        }else{
            System.out.println("当前二叉树为空，无法查询");
        }
    }


    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}

class Node{
    private int id;
    private String name;
    //左右子节点都默认为空
    private Node left; //左子节点
    private Node right; //右子节点


    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //前序遍历
    public void preOrder(){
        System.out.print(this + "\t");

        if (this.left !=null){
            this.left.preOrder();
        }

        if (this.right !=null){
            this.right.preOrder();
        }
    }

    //前序查找

    public Node preSearch(int id){
        //1.先判断当前节点是否符合
        if (this.id == id){
            return this;
        }
        //2.如果当前节点不符合就左递归
        Node node = null;
        if (this.left !=null){
            node = this.left.preSearch(id);
        }

        //如果node不为空，说明找到了，就直接返回
        if (node !=null){
            return node;
        }

        //3.如果左递归没有找到就进行右递归
        if (this.right != null){
            node = this.right.preSearch(id);
        }

        return node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
