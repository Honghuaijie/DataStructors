package com.hong.linkedList;

import org.junit.Test;

import java.util.Stack;

/**
 * ClassName: SingleLinkedList
 * Package: com.hong.linkedList
 * Description:
 *   单向链表
 * @Author honghuaijie
 * @Create 2023/9/15 10:48
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class SingleLinkedListDemo {


    //测试单向链表
    @Test
    public void test1() {
        SingleLikedList singleLikedList = new SingleLikedList();

        //创建几个节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "林冲", "豹子头");
        HeroNode hero6 = new HeroNode(6, "鲁智深", "豹");
        singleLikedList.add1(hero1);
        singleLikedList.add1(hero5);
        singleLikedList.add1(hero5);

        singleLikedList.add1(hero4);
        singleLikedList.add1(hero2);
        singleLikedList.add1(hero3);
        singleLikedList.delect(hero5);
        singleLikedList.delect(hero2);
        singleLikedList.update(hero3, hero6);

        singleLikedList.list();
    }

    //
    @Test
    public void test2() {
        SingleLikedList singleLikedList = new SingleLikedList();

        //创建几个节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "林冲", "豹子头");
        HeroNode hero6 = new HeroNode(6, "鲁智深", "豹");
        singleLikedList.add1(hero1);
        singleLikedList.add1(hero5);

        singleLikedList.add1(hero4);
        singleLikedList.add1(hero2);
        singleLikedList.add1(hero3);
        singleLikedList.list();
        //测试getLength方法
//        System.out.println(getLength(singleLikedList.head));
//
//        //测试getLastNode方法
//        System.out.println(findLastIndexNode(singleLikedList.head, 5));
        System.out.println();
        //测试reverseLinkedList
        reverseLinkedList(singleLikedList.head);
        singleLikedList.list();

        System.out.println("利用递归从尾到头遍历");
        LastPrintLinkedList1(singleLikedList.head.next);
        System.out.println("利用栈从尾到头遍历");
        LastPrintLinkedList2(singleLikedList.head);
    }



    //1.获取单链表中有效节点的个数(如果是带头链表的，需要不统计头节点)

    /**
     * @param head 传入的是链表的头结点
     * @return 返回的是链表有效节点的个数
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        HeroNode temp = head;
        while (temp.next != null) {
            length++;
            temp = temp.next;
        }

        return length;
    }


    //2.查找单链表中倒数第K个结点
    /*
     * 思路
     * 1.写一个方法传入头结点和index
     * 2.得到链表的长度size
     * 3.从链表的第一个开始遍历（size-index）个元素，就得到了该节点
     * 4.如果没有找到就返回null；
     * */

    /**
     * @param head  传入的是链表的头结点
     * @param index 传入的是倒数第index个结点
     * @return 倒数第index个结点
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        //如果链表为空 返回null
        if (head.next == null) {
            return null;
        }
        int length = getLength(head); //得到该链表的长度
        int i = 0;
        HeroNode temp = head;
        if (index > length && index <= 0) {
            return null;
        }
        while (i < length - index) {
            temp = temp.next;
            i++;
        }

        return temp.next;
    }

    //3.反转一个单链表
    /*
     * 思路：
     * 1.首先创建一个新的链表reverseHead
     * 2.依次遍历原来的链表list，
     * 将遍历的每一个节点都将其节点取出并将该节点的next属性指向reverseHead的头个结点
     * 并将reverseHead的头个结点设置为该节点
     *
     * 最后将原来链表的head的next属性指向reverse的前端
     * */
    public static void reverseLinkedList(HeroNode head) {
        HeroNode reverseHead = new HeroNode(0, "", "");
        if (head.next == null) {
            System.out.println("该链表是个空列表无法反转");
            return;
        }
        HeroNode temp = head.next; //获取链表的第一个节点
        HeroNode cur;
        while (temp != null) {
            cur = temp; //保存当前节点
            temp = temp.next;//后移节点
            cur.next = reverseHead.next;
            reverseHead.next = cur;

        }
        head.next = reverseHead.next;

    }

    //4.从尾到头打印单链表
    // 方式一：先将单链表反转，然后依次遍历（不建议，因为破坏了单链表的结构）

    // 方式二：利用栈——递归
    public void LastPrintLinkedList1(HeroNode heroNode) {
        if (heroNode.next != null) {
            LastPrintLinkedList1(heroNode.next);
        }

//        if (heroNode.no != 0) {
            System.out.println(heroNode);
//        }

    }

    //方式二：利用栈
    public void LastPrintLinkedList2(HeroNode head) {
        if (head.next == null) {
            return; //空链表不打印
        }

        //创建一个栈，将链表中的数据压入栈中
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = head;
        while (temp.next != null) {
            //stack.add(temp.next);
            stack.push(temp.next);
            temp = temp.next;
        }

        while (stack.size() > 0) {
            HeroNode pop = stack.pop();
            System.out.println(pop);
        }

    }


    //测试comList
    @Test
    public void test3(){

        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
        HeroNode hero5 = new HeroNode(5, "林冲", "豹子头");
        HeroNode hero6 = new HeroNode(6, "鲁智深", "豹");

        SingleLikedList s1 = new SingleLikedList();
        s1.add(hero1);
        s1.add(hero3);
        s1.add(hero5);
        SingleLikedList s2 = new SingleLikedList();
        s2.add(hero2);
        s2.add(hero4);
        s2.add(hero6);
//        s1.list();
//        s2.list();
        comList(s1.head,s2.head);
    }

    //5.合并两个有序的单链表，合并以后的链表任然有序
    public void comList(HeroNode head1,HeroNode head2){
        SingleLikedList comList = new SingleLikedList();

        HeroNode temp = head1.next;
        HeroNode cur;
        while (temp!=null){
            cur = temp;
            temp = temp.next;
            comList.add1(cur);
        }

        temp = head2.next;
        while (temp !=null){
            cur = temp;
            temp = temp.next;
            comList.add1(cur);
        }

        temp = comList.head;
        while (temp.next !=null){
            System.out.println(temp.next);
            temp = temp.next;
        }
    }
}


//创建一个单向链表
class SingleLikedList {
    HeroNode head = new HeroNode(0, "", ""); //定义链表的头结点

    //添加节点
    //不考虑编号，只添加到链表的最后
    public void add(HeroNode heroNode) {
        HeroNode temp = head; // 因为head不能动，所以使用临时变量存储head
        //循环，找到链表的最后
        //链表的最后一个节点的next属性为null
        while (true) {
            if (temp.next == null) {
                break;
            }
            //执行到这里说明此节点不是最后一个节点，继续找下一个节点
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    //按编号升序，进行存放
    /*
     * 有三种情况
     * 1.该节点编号最大，存放到链表的最后
     * 2.该节点编号存在，不能存放
     * 3.找到比该节点编号大的节点的前一个节点(temp)，就存放在temp的后面
     * */
    public void add1(HeroNode heroNode) {
        HeroNode temp = head; // 因为head不能动，所以使用临时变量存储head
        boolean flag = false; //判断编号是否存在
        //因为是单链表，所以我们找到的temp需要位于添加位置的前一个节点，否则添加不了

        //循环，找到编号比heroNode大的节点的前一个节点
        while (true) {
            if (temp.next == null) {
                //说明此节点最大，将此节点放入链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) {  //位置找到了，就在temp的后面
                break;
            }
            //如果找到编号与此节点编号相同的情况就报错
            if (temp.next.no == heroNode.no) {  //该编号已经存在，不能添加
                flag = true;
                break;
            }
            //将temp后移，避免出现死循环
            temp = temp.next;
        }
        //判断是否应该添加节点
        if (flag) {
            System.out.printf("准备插入的英雄的编号%d已经存在，不能加入了\n", heroNode.no);
        } else {
            heroNode.next = temp.next;
            temp.next = heroNode;
        }


    }

    //删除节点
    /*
     *  有两种情况
     * 1.找到该节点的前一个节点(temp) 将temp的next属性指向该节点的next属性
     * 2.没有找到该节点
     *
     */
    public void delect(HeroNode heroNode) {
        HeroNode temp = head;

        while (true) {
            //说明没有找到
            if (temp.next == null) {
                System.out.println("没有找到该节点");
                break;
            }

            if (temp.next == heroNode) {
                temp.next = heroNode.next;
                break;
            }

            temp = temp.next;
        }

    }

    //修改节点
    /*
     * 有两种情况
     * 1.找到该节点的前一个节点(temp)，并判断要替换的节点(upnode)的编号是唯一的(如果不是唯一的就不能存储)
     *   然后将temp的next属性赋值给upnode的next属性，并将upnode赋值给temp.next
     * 2.没有找到该节点
     *
     */
    public void update(HeroNode heroNode, HeroNode updateHeroNode) {
        HeroNode temp = head;
        boolean flag = true;
        while (true) {
            //说明没有找到
            if (temp.next == null) {
                System.out.println("没有找到该节点");
                flag = false;
                break;
            }

            if (temp.next == heroNode) {
                //判断updateHeroNode的编号是否存在
                HeroNode temp1 = head;
                while (true) {
                    if (temp1.next == null) {
                        break;
                    }
                    if (temp1.next.no == updateHeroNode.no) {
                        System.out.printf("准备插入的英雄的编号%d已经存在，不能加入了\n", updateHeroNode.no);
                        flag = false;
                        break;
                    }
                    temp1 = temp1.next;
                }
                break;
            }

            temp = temp.next;
        }
        //判断是否有权限修改
        if (flag) {
            updateHeroNode.next = temp.next.next;
            temp.next = updateHeroNode;
        }


    }

    //遍历链表
    public void list() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空，无法遍历");
            return;
        }

        HeroNode temp = head.next; // 因为head不能动，所以使用临时变量存储head
        while (true) {
            if (temp == null) {
                break;
            }
            //将temp后移
            System.out.println(temp);

            temp = temp.next;
        }

    }
}


//创建英雄类，一个英雄对象就是一个节点
class HeroNode {
    int no;  //英雄的编号
    String name; //英雄的名字
    String nickName; // 英雄的绰号
    HeroNode next; //下一个节点的引用

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
