package com.hong.tree.myTree.myThreadedBinaryTree;


/**
 * ClassName: MyThreadedBinaryTreeDemo
 * Package: com.hong.tree.myTree.myThreadedBinaryTree
 * Description:
 *  复习中序线索化二叉树
 * @Author honghuaijie
 * @Create 2023/10/5 9:33
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        Hero root = new Hero(1, "qwe");
        Hero node2 = new Hero(3, "qwe");
        Hero node3 = new Hero(6, "qwe");
        Hero node4 = new Hero(8, "qwe");
        Hero node5 = new Hero(10, "qwe");
        Hero node6 = new Hero(14, "qwe");
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        //创建线索二叉树
        MyThreadedBinaryTree tbt = new MyThreadedBinaryTree();
        tbt.setRoot(root);

        //测试：以10号节点为测试
//        tbt.threadedNode();
//
//        Hero left = node5.getLeft();
//        Hero right = node5.getRight();
//
//        System.out.println("node5的左子节点:" + left);
//        System.out.println("node5的右子节点:" + right);
//
//        System.out.println("遍历");
//        tbt.list();

        //前序遍历
        tbt.preThreadNodes();

        Hero left = node5.getLeft();
        Hero right = node5.getRight();

        System.out.println("node5的左子节点:" + left);
        System.out.println("node5的右子节点:" + right);

        System.out.println("遍历");
        tbt.preList();
    }
}

class MyThreadedBinaryTree{
    private Hero root; //根节点

    Hero pre;//用作线索化，使其每次都指向当前节点的前一个节点

    public void threadedNode(){
        this.threadedNode(root);
    }
    //中序线索化
    public void threadedNode(Hero node){

        if (node == null){
            return;
        }
        //线索化左子树
        threadedNode(node.getLeft());

        //线索化当前节点
        if (node.getLeft() == null){
            node.setLeft(pre); //将当前节点的左指针指向前一个节点
            node.setLeftType(1); //将左指针类型改变为前驱
        }

        //如果说前一个节点的右指针为空，那么就可以将当前节点指向前一个节点的右指针
        if (pre !=null && pre.getRight() == null){
            pre.setRight(node);
            pre.setRightType(1);
        }

        //将当前节点赋给pre
        pre = node;

        //线索化右子树
        threadedNode(node.getRight());
    }

    //遍历中序线索化后的二叉树
    /*
    * 思路，首先循环找到第一个要输出的元素（left=null）
    * 判断每一个元素的右指针类型(rightType是否等于1） 如果等于一就一直输出
    *
    *
    * 最后将当前节点的右节点赋值给左节点
    *
    * */
    public void list(){
        Hero node = root; //临时变量

        while (node !=null){

            //循环找到第一个要输出 的元素
            while (node.getLeftType() == 0){
                node = node.getLeft();
            }

            //执行到这里说明left为空
            System.out.println(node);

            while (node.getRightType() == 1){
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }


    }


    public void preThreadNodes(){
        this.preThreadNodes(root);
    }
    /*
    * 先线索化当前节点
    *
    * 然后将左子树线索化
    *
    * 然后将右子树线索化
    *
    * */
    //前序线索化
    public void preThreadNodes(Hero node){
        if (node == null){
            return;
        }
        //线索化当前节点
        if (node.getLeft() == null){
            node.setLeft(pre);
            node.setLeftType(1);
        }

        if( pre !=null && pre.getRight() == null){
            pre.setRight(node);
            pre.setRightType(1);
        }

        pre = node;

        //线索化左子树
        if (node.getLeftType() ==0){
            preThreadNodes(node.getLeft());
        }

        //线索化右子树
        if (node.getRightType() == 0){
            preThreadNodes(node.getRight());
        }
    }

    //前序线索化遍历

    /*
    * 首先输出当前节点，并判断当前节点的右子节点有没有被线索化，如果被线索化就一直输出
    *
    * 然后将左子节点赋给当前节点
    *
    * */
    public void preList(){
        Hero node = root;

        while (node !=null){

            //首先输出当前节点
            System.out.println(node);

            while (node.getRightType() == 1){
                node = node.getRight();
                System.out.println(node);
            }

            if (node.getLeftType() == 0){
                node = node.getLeft();
            }else{
                node = node.getRight();
            }
        }

    }


    public Hero getRoot() {
        return root;
    }

    public void setRoot(Hero root) {
        this.root = root;
    }
}



class Hero {
    private int id; //英雄的ID
    private String name; //英雄的姓名
    private Hero left; //左子节点
    private Hero right; //右子节点

    //说明
    //1.如果leftType=0 表示指向的是左子树，如果leftType=1 表示指向的是前驱结点
    //2.如果rightType=0 表示指向的是右子树，如果rightType=1 表示指向的是后续结点
    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public Hero(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 添加节点

    /**
     * @param hero 要添加到二叉树的节点
     *             思路，将this和hero进行比较，
     *             1.this比hero大
     *             1.1 判断this的左子节点存在不存在
     *             1.1.1不存在，则直接将hero作为this的右子节点
     *             1.1.2存在，则继续执行this,left().add（hero）
     *             2.this比hero小
     *             2.1判断this的右子节点存在不存在
     *             2.1.1不存在，则直接将hero作为this的右子节点
     *             2.1.2存在，则继续执行this.right().add(hero)
     */
    public void add(Hero hero) {
        if (this.id > hero.id) { //this比hero大 判断左子节点
            if (this.left == null) {
                this.left = hero;
            } else {
                this.left.add(hero);
            }
        } else {//this比hero小 判断右子节点
            if (this.right == null) {
                this.right = hero;
            } else {
                this.right.add(hero);
            }
        }
    }


    //前序遍历
    /*
     * 思路：
     * 1.输出当前节点
     * 2.判断当前节点的左节点是否为空
     *   2.1如果为空，则继续执行
     *   2.2如果不为空，则继续让其左子节点递归前序遍历
     * 3.判断当前节点的右节点是否为空
     *   3.1如果为空，则继续执行
     *   3.2如果不为空，则继续让其右子节点递归前序遍历
     * */
    public void preOrder() {

        System.out.print(this + "\t");
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    //中序遍历
    /*
     * 思路：
     *
     * 1.判断当前节点的左节点是否为空
     *   1.1如果为空，则继续执行
     *   1.2如果不为空，则让其左子节点继续递归中序遍历
     * 2.输出当前节点
     * 3.判断当前节点的右节点是否为空
     *   3.1如果为空，则继续执行
     *   3.2如果不为空，则让其右子节点继续递归中序遍历
     * */

    public void midOrder() {
        if (this.left != null) {
            this.left.midOrder();
        }
        System.out.print(this + "\t");
        if (this.right != null) {
            this.right.midOrder();
        }
    }


    //后序遍历
    /*
     * 思路：
     *
     * 1.判断当前节点的左节点是否为空
     *   1.1如果为空，则继续执行
     *   1.2如果不为空，则让其左子节点继续递归后序遍历
     * 2.判断当前节点的右节点是否为空
     *   2.1如果为空，则继续执行
     *   2.2如果不为空，则让其右子节点继续递归后序遍历
     * 3.输出当前节点
     * */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.print(this + "\t");
    }

    //前序遍历查找
    /*
     * 思路
     * 1.首先判断当前节点是否与传入的hero相等，如果相等就返回 当前节点
     * 2.如果不等，就判断让当前节点的左子节点递归前序查找
     * 3.如果左递归没有找到该数，就进行右递归查找
     * */

    /**
     * @param id 查找的id
     * @return 如果找到就返回该Node，没有找到就返回null
     */
    public Hero preSearch(int id) {
        System.out.println("111");
        Hero Node = null;
        if (this.id == id) {
            return this;
        }
        if (this.left != null) { //进行左递归
            Node = this.left.preSearch(id);
        }

        //来判断左递归有没有找到值
        if (Node != null) {
            return Node;
        }

        //如果左递归没有找到，就判断右子节点是否存在，如果存在就进行右递归，如果不存在就返回null
        if (this.right != null) {
            Node = this.right.preSearch(id);
        }
        return Node;

    }


    //中序遍历查找
    /*
     * 思路
     * 1.首先进行左递归中序查找
     * 2.判断当前节点是否等于传入的id，如果相等就返回该节点
     * 3.如果左递归没有找到，就进行右子递归
     * */
    public Hero midSearch(int id) {
        Hero Node = null;

        if (this.left != null) { //进行左递归
            Node = this.left.midSearch(id);
        }

        //来判断左递归有没有找到值,找到就返回该值
        if (Node != null) {
            return Node;
        }
        System.out.println("111");
        //如果左递归没有找到值，则判断当前节点是否和id相等，如果相等就返回当前节点
        if (this.id == id) {
            return this;
        }

        //如果左递归和当前节点都没有找到
        // 就判断是否存在右子节点，如果存在就进行右递归，如果不存在就返回null
        if (this.right != null) {
            Node = this.right.midSearch(id);
        }
        return Node;

    }


    //后序遍历查找
    /*
     * 思路
     * 1.首先进行左递归后序查找
     * 2.如果左递归没有找到就进行右递归后序查找
     * 3.判断当前节点是否等于传入的id
     * */
    public Hero postSearch(int id) {
        Hero Node = null;

        if (this.left != null) { //进行左递归
            Node = this.left.postSearch(id);
        }

        //来判断左递归有没有找到值
        if (Node != null) {
            return Node;
        }

        if (this.right != null) {
            Node = this.right.postSearch(id);
        }
        //来判断右递归有没有找到值
        if (Node != null) {
            return Node;
        }
        System.out.println("111"); //真实的比较应该是下面的if，所以如果要记录比较的次数，应该输出在上面
        //如果左右字数都没有找到值，则判断当前节点是否相等
        if (this.id == id) {
            return this;
        } else {
            return null;
        }

    }

    //删除节点
    //1.如果删除的节点是叶子节点就直接删除该节点
    //2.如果删除的节点是非叶子节点就直接删除该子树
    //由于我们二叉树是单向的，所以我们只能去判断该节点的子节点是否需要被删除，
    // 而不能去判断当前节点是不是需要删除的节点，因为我们无法删除当前节点
    //删除成功就返回true，否则就返回false
    public boolean delNode(int id) {
        if (this.left != null && this.left.id == id) {
            this.left = null;
            return true;
        }
        if (this.right != null && this.right.id == id) {
            this.right = null;
            return true;
        }
        boolean flag = false;

        //如果左右节点都不等于，那么就进行左右递归
        if (this.left != null) {
            flag = this.left.delNode(id);
        }
        //如果左递归找到了就返回true，退出方法
        if (flag) {
            return flag;
        }

        if (this.right != null) {
            flag = this.right.delNode(id);
        }
        return flag;
    }

    //删除节点2：指定规则
    /* 如果要删除的节点是非叶子节点，现在我们不希望将该非叶子节点为根节点的子树删除，需要指定规则,
        假如规定如下:
        1.如果该非叶子节点A只有一个子节点B，则子节点B替代节点A
        2.如果该非叶子节点A有左子节点B和右子节点C，则让左子节点B替代节点A。
    */
    public boolean delNode2(int id) {
        if (this.left != null && this.left.id == id) {
            //如果找到该节点，会出现三种情况
            //1.该节点是叶子节点:直接删除该节点
            if (this.left.left == null && this.left.right == null) {
                this.left = null;
            } else if (this.left.left != null && this.left.right != null) {
                //2.该节点有两个子节点：我们让左子节点代替该节点
                this.left = this.left.left;
            } else {//3.该节点只有一个子节点
                this.left = (this.left.left == null ? this.left.right : this.left.left);
            }


            return true;
        }
        if (this.right != null && this.right.id == id) {
            //如果找到该节点，会出现三种情况
            //1.该节点是叶子节点:直接删除该节点
            if (this.right.left == null && this.right.right == null) {
                this.left = null;
            } else if (this.right.left != null && this.right.right != null) {
                //2.该节点有两个子节点：我们让左子节点代替该节点
                this.right = this.right.left;
            } else {//3.该节点只有一个子节点
                this.right = (this.right.left == null ? this.right.right : this.right.left);
            }
            return true;
        }
        boolean flag = false;

        //如果左右节点都不等于，那么就进行左右递归
        if (this.left != null) {
            flag = this.left.delNode(id);
        }
        if (flag) {
            return flag;
        }
        //就算左递归找到了，还是会执行右递归
        if (this.right != null) {
            flag = this.right.delNode2(id);
        }
        return flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hero getLeft() {
        return left;
    }

    public void setLeft(Hero left) {
        this.left = left;
    }

    public Hero getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setRight(Hero right) {
        this.right = right;
    }
}

