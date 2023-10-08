package com.hong.tree;

/**
 * ClassName: ArrayBinaryTreeDemo
 * Package: com.hong.tree
 * Description:
 *  顺序存储二叉树
 * @Author honghuaijie
 * @Create 2023/10/3 19:04
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree ab = new ArrayBinaryTree(arr);
        ab.preOrder();
        System.out.println();
        ab.midOrder(0);
        System.out.println();
        ab.postOrder(0);
    }
}

class ArrayBinaryTree {
    int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }


    //重载preOrder方法
    public void preOrder() {
        this.preOrder(0);
    }

    /**
     * @param i 表示从哪个位置开始找
     */
    //前序遍历，先输出当前节点，然后递归左子树，然后递归右子树
    public void preOrder(int i) {
        if (arr == null || arr.length == 0) {
            System.out.println("当前数组为空，无法进行前序遍历");
            return;
        }
        //输出当前节点
        System.out.print(arr[i]);

        //递归左子树
        if (i * 2 + 1 <arr.length){
            preOrder(i * 2 + 1);
        }
        //递归右子树
        if (i * 2 + 2 <arr.length){
            preOrder(i * 2 + 2);
        }


    }

    //重载midOrder方法
    public void midOrder() {
        this.midOrder(0);
    }

    /**
     * @param i 表示从哪个位置开始找
     */
    //中序遍历，先递归左子树，然后输出当前节点，然后递归右子树
    public void midOrder(int i) {
        if (arr == null || arr.length == 0) {
            System.out.println("当前数组为空，无法进行中序遍历");
            return;
        }

        //递归左子树
        if (i * 2 + 1 < arr.length){
            midOrder(i * 2 + 1);
        }
        //输出当前节点
        System.out.print(arr[i]);
        //递归右子树
        if (i * 2 + 2 < arr.length){
            midOrder(i * 2 + 2);
        }
    }


    /**
     * @param i 表示从哪个位置开始找
     */
    //后序遍历，先递归左子树，然后递归右子树，然后输出当前节点，
    public void postOrder(int i) {
        if (arr == null || arr.length == 0) {
            System.out.println("当前数组为空，无法进行后序遍历");
            return;
        }
        //递归左子树
        if (i * 2 + 1 <arr.length){
            postOrder(i * 2 + 1);
        }
        //递归右子树
        if (i * 2 + 2 <arr.length){
            postOrder(i * 2 + 2);
        }
        //输出当前节点
        System.out.print(arr[i]);

    }


}


