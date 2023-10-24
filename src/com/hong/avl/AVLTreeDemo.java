package com.hong.avl;

/**
 * ClassName: AVLTreeDemo
 * Package: com.hong.avl
 * Description:
 *   平衡二叉树的实现（平衡左子树与右子树的高度
 *   左旋转（右子树的高度大于左子树的高度）
 *      在进行左旋转之前需要先判断右子树的左子树的高度是否大于右子树的右子树的高度，
 *          如果大于，则需要先将右子树进行右旋转
 *   右旋转（左子树的高度大于右子树的高度）
 *      在进行右旋转的时候，需要判断左子树本身需不需要进行左旋转
 * @Author honghuaijie
 * @Create 2023/10/14 10:02
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        //int[] arr = {4,3,6,5,7,8};
        //int[] arr = {10,12,8,9,7,6};
        int[] arr = {10,11,7,6,8,9};
        //创建一个AVLTree对象
        AVLTree avlTree = new AVLTree();
        //添加节点
        for (int i = 0; i < arr.length;i++){
            avlTree.add(new Node(arr[i]));
        }
//        //遍历
//        avlTree.infixOrder();

        //在没有做平衡处理之前~
        System.out.println("在没有做平衡处理之前~");
        //当前树的高度
        System.out.println("树的高度" +avlTree.height());
        //输出左子树的高度
        System.out.println("左子树的高度" + avlTree.leftHeight());
        //输出右子树的高度
        System.out.println("右子树的高度" + avlTree.rightHeight());

        System.out.println("根节点" + avlTree.getRoot());
        System.out.println("根节点的左" + avlTree.getRoot().getLeft());
        System.out.println("根节点的右" + avlTree.getRoot().getRight().getRight());

    }
}


//创建AVLTree(在二叉排序树上增加一个功能，用来平衡二叉排序树)

class AVLTree {
    private Node root;


    //获取当前树的高度
    public int height(){
        if (root ==null){
            return 0;
        }else{
            return root.height();
        }
    }

    //返回左子树的高度
    public int leftHeight(){
        if (root == null){
            return 0;
        }
        return root.leftHeight();
    }
    //返回右子树的高度
    public int rightHeight(){
        if (root == null){
            return 0;
        }
        return root.rightHeight();
    }
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

    //左旋转方法
    //左旋转(当右子树的高度-左子树高度大于1时)
    private void leftRotate(){
        //1.创建一个新的节点，节点的值就是当前节点的值
        Node newNode = new Node(value);
        //2.将新节点的左子节点指向当前节点的左子节点
        newNode.setLeft(left);
        //3.将新节点的右子节点指向当前节点的右子节点的左子节点
        newNode.setRight(right.left);
        //4.将当前节点的value改为当前节点右节点的值
        value = right.value;
        //5.将当前节点节点的左子节点指向新节点
        left = newNode;
        //6.将当前节点节点的右子节点指向root节点的右子节点的右节点
        right = right.right;
    }

    //右旋转(当左子树的高度-右子树高度大于1时)
    private void rightRotate(){
        Node newNode = new Node(value);
        newNode.left = left.right;
        newNode.right = right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    //返回左子树的高度
    public int leftHeight(){
        if (left == null){
            return 0;
        }else{
            return left.height();
        }
    }

    //返回右子树的高度
    public int rightHeight(){
        if (right == null){
            return 0;
        }else{
            return right.height();
        }
    }

    //返回 以当前节点为根节点的树的高度
    public int height(){
        return Math.max(left == null ? 0 : left.height(),right == null ? 0: right.height()) +1;
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

        //在添加一个节点之后，如果（右子树的高度-左子树的高度) >1 则需要进行左旋转

        if (rightHeight()-leftHeight() > 1){
            //判断他的右子树的左子树高度是否比他的右子树的右子树高度大，如果大 ，还需要先进行右旋转
            if (right != null && right.leftHeight() > right.rightHeight()){
                //先对当前节点的右子树进行右旋转
                right.rightRotate();
            }
            leftRotate(); //左旋转
        }else if (leftHeight()-rightHeight() > 1){
            //在进行右旋转之前还需要进行以下判断
            //判断他的左子树的右子树高度是否比他的左子树的左子树高度大，如果大 ，还需要先进行左旋转
            if (left != null && left.rightHeight() > left.leftHeight()){
                //先对当前节点的左子树进行左旋转
                left.leftRotate();
            }
            rightRotate(); //右旋转
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


