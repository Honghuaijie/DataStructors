package com.hong.binarySortTree;

/**
 * ClassName: BinarySortTree
 * Package: com.hong.binarySortTree
 * Description:
 * 写一个二插排序树，有一个添加和遍历的功能
 * 删除功能
 *
 * @Author honghuaijie
 * @Create 2023/10/9 12:30
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        binarySortTree.infixOrder();  // 1,3,5,7,9,10,12

        //测试删除叶子节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);

        binarySortTree.delNode(1);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        System.out.println("删除节点后");
        binarySortTree.infixOrder();  // 1,3,5,7,9,10,12

//        System.out.println("根节点：" + binarySortTree.getRoot());
    }
}


//二叉排序树
class BinarySortTree {
    private Node root;


    //查找要删除的节点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //查找要删除的节点的前一个节点
    public Node parentSearch(int value) {
        if (root == null) {
            return null;
        } else {
            return root.parentSearch(value);
        }
    }

    //删除节点

    /**
     * 思路：
     * 1.找到要删除的节点
     * 2.找到要删除的节点的父节点
     * <p>
     * 首先判断该节点是否是根节点 如果是根节点就直接返回null
     * 3.判断该节点是叶子结点、有一个子树、右两个子树
     * 3.1 该节点是叶子节点(node.left == null && node.right == null)
     * 判断该节点是父节点的左子节点还是右子节点，如果是左子节点就将做子节点赋值为空，如果是右子节点就让右子节点赋值为空
     * 3.2 该节点只有一个子节点 (node.left == null && node.right !=null || node.left!=null && node.right == null)
     * 3.2判断该节点是父节点的左子节点还是右子节点
     * 3.2.1如果是左子节点 就判断（node.left == null && node.right !=null）  parent.left = node.right
     * 如果是左子节点 就判断（node.left != null && node.right ==null）  parent.left = node.left
     * 3.2.2如果是右子节点 就判断（node.left == null && node.right !=null）  parent.right = node.right;
     * 如果是右子节点 就判断（node.left != null && node.right ==null）  parent.right = node.left;
     * 3.3 该节点有两个子节点 (node.left !=null && node.right !=null)
     * 3.3.1判断该节点是父节点左子节点还是右子节点
     * 3.3.2找出该节点中右子树中的最小节点，将这个节点（这个节点一定是个叶子结点）去替换当前节点
     *
     * @param value 传入要删除的节点的值
     */
    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            //1.先找到要删除的节点
            Node targetNode = search(value);
            //说明没有找到要删除的节点
            if (targetNode == null) {
                return;
            }
            //执行到这里，说明找到了要删除的节点
            //如果我们发现当前二叉排序树只有一个根节点，那么要删除的节点就是根节点了
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
                return;
            }

            //去找到targetNode的父节点
            Node parent = parentSearch(value);
            //如果父节点为空，说明当前节点是root

            //1.如果说当前节点是叶子结点
            if (targetNode.getLeft() == null && targetNode.getRight() == null) {
                //如果当前节点是parent的左节点，那么就将左节点置空
                if (parent.getLeft() != null && parent.getLeft() == targetNode) {
                    parent.setLeft(null);
                } else {//如果当前节点是parent的右节点，那么就将右节点置空
                    parent.setRight(null);
                }
            } else if (targetNode.getLeft() !=null && targetNode.getRight() !=null){//3.删除有两颗子树的节点
                //删除该节点的右子节点的最小节点，并返回该最小节点的值
                int minValue = delRightTreeMin(targetNode.getRight());
//                int maxValue = delLeftTreeMax(targetNode.getLeft());

                targetNode.setValue(minValue);

            }else{  //2  删除只有一颗子树的节点
                //如果删除的节点有左子节点
                if (targetNode.getLeft() !=null){
                    if (parent !=null){
                        if (parent.getLeft()!=null && parent.getLeft() == targetNode){
                            parent.setLeft(targetNode.getLeft());
                        }else{ //当前节点是parent的右子节点
                            parent.setRight(targetNode.getLeft());
                        }
                    }else{ //说明要删除的节点是root节点
                        root = targetNode.getLeft();
                    }
                    //如果当前节点是parent的左子节点
                }else{ //如果删除的节点有右子节点
                    if (parent!=null){
                        //如果当前节点是parent的左子节点
                        if (parent.getLeft()!=null && parent.getLeft() == targetNode){
                            parent.setLeft(targetNode.getRight());
                        }else{ //当前节点是parent的右子节点
                            parent.setRight(targetNode.getRight());
                        }
                    }else{
                        root = targetNode.getRight();
                    }
                }
            }



        }
    }


    /**
     *  方法“
     *  1. 找出这个树的最小节点的值
     *  2. 删除这个最小节点
     * @param node 传入一个节点，(当做二叉排序树的根节点)
     * @return  返回这个树的最小节点的值
     */
    public int delRightTreeMin(Node node){
        Node temp = node;
        while (temp.getLeft() !=null){
            temp = temp.getLeft();
        }

        //删除这个最小节点
        delNode(temp.getValue());
        return temp.getValue();
    }


    /**
     * 方法
     *  1. 找出这个树的最大节点的值
     *  2. 删除这个最大节点
     * @param node
     * @return
     */
    public int delLeftTreeMax(Node node){
        Node temp = node;
        while (temp.getRight() !=null){
            temp = temp.getRight();
        }

        //删除这个最小节点
        delNode(temp.getValue());
        return temp.getValue();
    }


    //添加节点
    public void add(Node node) {
        if (root != null) {
            root.add(node);
        } else {
            root = node;
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("该树为空");
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}


class Node {
    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
    }


    //查找要删除的节点

    /**
     * @param value 希望删除的节点的值
     * @return 如果找到就返回该节点，如果没有找到就返回null
     */
    public Node search(int value) {
        if (this.value == value) {
            return this;
        }
        if (this.value > value) {  //该节点应该在当前节点的左边
            if (this.left != null) {
                return this.left.search(value);
            } else {
                return null;
            }
        } else {
            if (this.right != null) {
                return this.right.search(value);
            } else {
                return null;
            }
        }
    }

    //查找要删除节点的父节点
    public Node parentSearch(int value) {
        //当前节点的左子节点或右子节点的value等于value那么，就返回当前节点
        if (this.left != null && this.left.value == value ||
                this.right != null && this.right.value == value) {
            return this;
        } else {
            //如果要查找的值，小于当前节点的值，并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                return this.left.parentSearch(value);  //向左子树递归
            } else if (value >= this.value && this.right != null) {
                return this.right.parentSearch(value); //向右子树递归
            } else {
                return null; //没有找到父节点
            }
        }
    }


    //添加节点

    /**
     * 思路：
     * 首先将当前节点与要添加的节点比较
     * 会出现两个情况
     * 1.当前节点大于node
     * 1.1 当前节点的左子树为空
     * 将node作为当前节点的左节点
     * 1.2 当前节点的左子树不为空
     * 递归左子树 this.left.add(node)
     * 2.当前节点小于node
     * 2.1 当前节点的右子树为空
     * 将node作为当前节点的右节点
     * 2.2 当前节点的右子树不为空
     * 递归右子树 this.right.add(node)
     *
     * @param node 要添加的节点
     */
    public void add(Node node) {
        if (node.value < this.value) { //当前节点大于node节点
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if (this.right != null) {
            this.right.infixOrder();
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