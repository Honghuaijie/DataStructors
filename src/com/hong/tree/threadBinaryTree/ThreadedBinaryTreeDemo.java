package com.hong.tree.threadBinaryTree;

import org.w3c.dom.Node;

/**
 * ClassName: ThreadedBinaryTree
 * Package: com.hong.tree
 * Description:
 * 线索化二叉树
 *
 * @Author honghuaijie
 * @Create 2023/10/4 10:19
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class ThreadedBinaryTreeDemo {
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
        ThreadedBinaryTree tbt = new ThreadedBinaryTree();
        tbt.setRoot(root);

        //测试：以10号节点为测试
//        tbt.threadedNodes();
        tbt.preThreadedNodes();
        Hero left = node5.getLeft();
        Hero right = node5.getRight();

        System.out.println("node5的左子节点:" + left);
        System.out.println("node5的右子节点:" + right);
        System.out.println(right.getLeft().getLeft());
        System.out.println(right.getLeft().getLeft().getRight());
        //遍历线索化二叉树
//        System.out.println("使用线索化的方式遍历 线索化二叉树");
//        tbt.threadedList(); // 8 3 10 1 14 6

        //线索化二叉树前序
        System.out.println("线索化二叉树前序");
        tbt.preThreadList();


    }

}

//定义线索化功能的二叉树
class ThreadedBinaryTree {
    private Hero root; //根节点

    //为了实现线索化，需要创建指向当前节点的前驱节点的指针
    //在递归线索化时，pre总是保留前一个节点
    private Hero pre = null;


    //遍历线索化中序二叉树的方法
    public void threadedList() {
        //定义一个变量，临时存储当前遍历的节点，从root开始
        Hero node = root;
        while (node != null) {

            //循环找到leftType=1的节点，第一个找到的就是8节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }

            //执行到这里就说明node的leftType为1，说明当前节点被线索化了
            //输出当前节点
            System.out.println(node);

            //如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.getRightType() == 1) {
                //获取当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }

            //替换这个遍历的节点
            node = node.getRight();
        }
    }

    //重载threadedNodes 默认从root开始调用
    public void threadedNodes() {
        this.threadedNodes(root);
    }

    //编写对二叉树进行中序线索化的方法
    //首先的递归，找到最左边的子节点，然后
    public void threadedNodes(Hero node) {
        if (node == null) {
            return;
        }
        //（一）线索化左子树
        if (node.getLeft() != null) {
            threadedNodes(node.getLeft());
        }
        //（二）线索化当前节点  :只有这里才是真正的处理节点，其他的都是在递归
        //处理当前节点的前驱节点
        if (node.getLeft() == null) {
            //让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            node.setLeftType(1); //改变左子节点的类型
        }
        //处理后继节点
        //将当前节点赋值给前序节点的右子节点
        if (pre != null && pre.getRight() == null) {
            //将当前节点赋值个前序节点的右子节点
            pre.setRight(node);
            //修改前序节点的右子节点的类型
            pre.setRightType(1);
        }
        //!!!每处理一个节点，都要将当前节点赋值给pre
        pre = node;

        //线索化右子树
        if (node.getRight() != null) {
            threadedNodes(node.getRight());
        }

    }

    //重载preThreadedNodes 让其默认调用preThreadedNodes（root）
    public void preThreadedNodes() {
        this.preThreadedNodes(root);
    }

    //编写线索化前序遍历
    public void preThreadList() {
        Hero node = root;
        while (node != null) {
            //先输出该节点
            System.out.println(node);
            //找到
            while (node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }


            if (node.getLeftType() == 0){  //如果左子树没有被线索化就将左子树赋给node
                node = node.getLeft();
            }else{ //如果左子树被线索化了， 就将右子树赋给node
                node  = node.getRight();
            }
        }
    }

    //编写对二叉树进行前序线索化的方法
    public void preThreadedNodes(Hero node) {
        if (node == null) {
            return;
        }
        //线索化当前节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        //将当前节点赋值pre的右指针
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        pre = node;

        //线索化左子树(如果该子树已经线索化过，就没必要在进去了)
        if (node.getLeftType() == 0 && node.getLeft() != null) {
            preThreadedNodes(node.getLeft());
        }

        //线索化右子树
        if (node.getRightType() == 0 && node.getRight() != null) {
            preThreadedNodes(node.getRight());
        }
    }

    //添加节点
    public void add(Hero hero) {
        if (this.root != null) {
            this.root.add(hero);
        } else {
            this.root = hero;
        }
    }

    //前序遍历
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void midOrder() {
        if (this.root != null) {
            this.root.midOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //后序遍历
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //前序查找
    public void preSearch(int id) {
        if (this.root != null) {
            Hero hero1 = this.root.preSearch(id);
            if (hero1 != null) {
                System.out.println(hero1);
            } else {
                System.out.println("没有找到");
            }
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //中序查找
    public void midSearch(int id) {
        if (this.root != null) {
            Hero hero1 = this.root.midSearch(id);
            if (hero1 != null) {
                System.out.println(hero1);
            } else {
                System.out.println("没有找到");
            }
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //后序查找
    public void postSearch(int id) {
        if (this.root != null) {
            Hero hero1 = this.root.postSearch(id);
            if (hero1 != null) {
                System.out.println(hero1);
            } else {
                System.out.println("没有找到");
            }
        } else {
            System.out.println("当前二叉树为空，无法遍历");
        }
    }

    //删除节点
    public void delNode(int id) {
        if (root == null) {
            System.out.println("这是一个空树，无法删除");
            return;
        }
        if (root.getId() == id || root.getLeft() == null && root.getRight() == null) { //说明要删除的是根节点。直接将root赋值为null
            root = null;
            return;
        }

        root.delNode2(id);
//        if (root.delNode2(id)){
//            System.out.println("删除成功");
//        }else{
//            System.out.println("删除失败");
//        }
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
