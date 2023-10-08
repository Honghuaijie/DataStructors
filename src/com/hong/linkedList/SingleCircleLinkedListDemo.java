package com.hong.linkedList;

import org.junit.Test;

/**
 * ClassName: SingleCircleLinkedListDemo
 * Package: com.hong.linkedList
 * Description:
 * 单向环形链表
 * @Author honghuaijie
 * @Create 2023/9/16 18:14
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class SingleCircleLinkedListDemo {
    @Test
    public void test1(){


        SingleCircleLinkedList sl = new SingleCircleLinkedList();
        sl.addBoy(5);
//        sl.list();
//        sl.countBoy(1,1,5);
        josephu(sl.first,1);
    }

    //传入一个m和链表的头节点，定义一个i=1 每当遍历到一个节点时i++，
    // 当i=m时，输出该节点的no值，并将该节点从链表中删除
    public void josephu(BoyNode first,int m){
        BoyNode temp = first;

        BoyNode perBoy =first; //记录first的前一个节点
        while (perBoy.getNext() !=first){
            perBoy = perBoy.getNext();
        }
        int i = 1;
        while (temp !=perBoy){ //只要不剩下一个人， 就一直叫下去
            if (i == m){
                //输出该节点的编号
                System.out.println(temp.no);
                temp = temp.getNext();
                perBoy.setNext(temp);
                i=1;
                continue;
            }
            perBoy = temp;
            temp = temp.getNext();
            i++;
        }
        System.out.println(temp.no);
    }
}


class SingleCircleLinkedList{
    BoyNode first ; //用来保存链表中的第一个节点
    BoyNode curBoy; //用来记录最后一个节点
    //添加一个指定长度的链表
    public void addBoy(int num){
        if (num < 1 ){
            System.out.println("请输入正确的数字");
            return;
        }
        first = new BoyNode(1);
        first.setNext(first); //构成环
        curBoy = first;
        for (int i = 2; i <=num ; i++) {
            BoyNode boy = new BoyNode(i);
            curBoy.setNext(boy);
            boy.setNext(first);
            curBoy = boy;
        }
    }

    //遍历
    public void list(){
        if (first == null){
            System.out.println("链表为空，无法遍历");
            return;
        }
        BoyNode temp = first;
        while (true){
            System.out.println(temp);
            if (temp.getNext() == first){
                break;
            }
            temp = temp.getNext();
        }
    }

    //根据用户的输入，计算出小孩出圈的顺序
    /*
    * 思路：
    * 1.首先定义一个perBoy，用来保存first节点的前一个节点
    * 2.将first和perBoy节点移动到指定boy的位置
    * 3.将first和perBoy一起移动countNum-1次，将要出圈的小孩出圈
    * 4.直到圈中只有一个小孩2
    * */
    /**
     *
     * @param startNo 从第几个小孩开始数数
     * @param countNum 数几下
     * @param nums 表示最初有多少小孩在圈
     */
    public void countBoy(int startNo,int countNum,int nums){
        //先判断链表是否存在
        if (first == null){
            System.out.println("链表为空");
            return;
        }
        BoyNode perBoy = first;
        while (perBoy.getNext() != first){
            perBoy = perBoy.getNext();
        }
        //执行到这里说明perBoy现在指向的是最后一个节点了

        //然后将first和perBoy都移动到startNo这里
        for (int i = 0; i < startNo-1; i++) {
            first = first.getNext();
            perBoy = perBoy.getNext();
        }

        //执行到这里我们已经找到了从第几个小孩开始喊数了
        while (first !=perBoy){

            //找到要出圈的小孩
            for (int i = 0; i < countNum-1; i++) {
                first = first.getNext();
                perBoy = perBoy.getNext();
            }
            //此时first指向的就是要出圈的小孩了
            System.out.printf("小孩%d要出圈了\n",first.no);
            first = first.getNext();
            perBoy.setNext(first);
        }
        System.out.printf("最后在圈中的小孩是%d\n",first.no);
    }
}

//定义一个男孩类'
class BoyNode{
    int no;
    private BoyNode next;

    public BoyNode getNext() {
        return next;
    }

    public void setNext(BoyNode next) {
        this.next = next;
    }

    public BoyNode(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "BoyNode{" +
                "no=" + no +
                '}';
    }
}

