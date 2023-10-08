package com.hong.linkedList;

import org.junit.Test;

/**
 * ClassName: DoubleLinkedListDemo
 * Package: com.hong.linkedList
 * Description:
 *      双向环形链表
 * @Author honghuaijie
 * @Create 2023/9/16 14:53
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class DoubleLinkedListDemo {
    //测试add
    @Test
    public void test1() {
        DoubleLikedList dl = new DoubleLikedList();
        //创建几个节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        HeroNode2 hero5 = new HeroNode2(5, "林冲", "豹子头");
        HeroNode2 hero6 = new HeroNode2(6, "鲁智深", "豹");
        //添加节点
        dl.add(hero1);
        dl.add(hero3);
        dl.add(hero5);
        dl.add(hero2);
        dl.add(hero4);
        dl.add(hero6);
        //测试遍历操作
        dl.list();
    }

    //测试add1
    @Test
    public void test2() {
        DoubleLikedList dl = new DoubleLikedList();
        //创建几个节点
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");
        HeroNode2 hero5 = new HeroNode2(4, "林冲4", "豹子头4");
        HeroNode2 hero6 = new HeroNode2(6, "鲁智深", "豹");
        HeroNode2 hero7 = new HeroNode2(7, "武松", "打虎");
        //添加节点
        dl.add1(hero3);
        dl.add1(hero1);
        dl.add1(hero6);
        dl.add1(hero2);
        dl.add1(hero4);
        //测试遍历操作
        dl.list();

        dl.update(hero5);
        System.out.println("替换以后的链表");
        dl.list();

        dl.delete(6);
        System.out.println("删除以后的链表");
        dl.list();


    }


}


//创建HeroNode类，一个英雄对象就是一个节点
class HeroNode2 {
    int no;  //英雄的编号
    String name; //英雄的名字
    String nickName; // 英雄的绰号
    HeroNode2 next; //下一个节点的引用
    HeroNode2 pre;  //上一个节点的引用

    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

//创建一个双向链表的类
class DoubleLikedList {
    //先初始化一个头结点，头结点不要动，不存放具体的数据
    private HeroNode2 head = new HeroNode2(0, "", "");

    //返回头节点
    public HeroNode2 getHead() {
        return head;
    }

    //遍历
    public void list() {
        if (head.next == null) {
            System.out.println("该链表为空，无法遍历");
            return;
        }

        HeroNode2 temp = head; //使用一个临时变量来存放head
        while (temp.next != null) {
            System.out.println(temp.next);
            temp = temp.next;
        }

    }

    //添加一个节点到双向链表的最后
    // 方式1：不考虑其他，直接添加到链表的末尾
    //
    public void add(HeroNode2 heroNode2) {
        HeroNode2 temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        //进行双向绑定
        temp.next = heroNode2;
        heroNode2.pre = temp;
    }

    //方式2：保证链表是升序状态，并且不能有编号重复的情况
    /*
     * 思路：有三种情况
     * 1.传入的节点编号最大，保存在链表的尾部，
     * 2.传入的节点编号需要插入到两个节点之间
     * 3.传入的节点编号存在，无法加入
     * */
    public void add1(HeroNode2 heroNode2) {
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                temp.next = heroNode2;
                heroNode2.pre = temp;
                break;
            }
            if (temp.next.no > heroNode2.no) { //将heroNode2插入到temp和temp.next之间
                heroNode2.next = temp.next;
                temp.next.pre = heroNode2;
                heroNode2.pre = temp;
                temp.next = heroNode2;
                break;
            }
            if (temp.next.no == heroNode2.no) {
                System.out.printf("当前编号%d已存在\n", heroNode2.no);
                break;
            }

            temp = temp.next;
        }

    }
    //修改一个节点的内容
    /*
     * 思路
     * 1.找到了该节点，，直接修改其属性值
     * 2.没有找到该节点
     * */


    /**
     *
     * @param newNode 新的节点的内容
     */
    public void update( HeroNode2 newNode) {
        if (head.next == null) {
            System.out.println("当前列表为空，无法修改");
        }
        boolean flag = false; //判断是否找到

        HeroNode2 temp = head.next;
        while (temp != null) {
            if (temp.no == newNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }

        if (flag) {
            temp.name = newNode.name;
            temp.nickName = newNode.nickName;
            System.out.println("修改成功");
        } else {
            System.out.println("没有找到该节点");
        }


    }
    //删除 :传入一个编号，删除这个编号的节点
    /*
    * 思路:
    * 1.找到该节点，进行删除
    * 2.没有找到该节点，不进行操作
    * */
    public void delete(int no){
        if (head.next == null){
            System.out.println("该链表为空，无法进行删除操作");
        }

        HeroNode2 temp = head.next;
        while (temp !=null){
            if (temp.no == no){ //找到该节点
                temp.pre.next = temp.next;
                if (temp.next!=null){ //判断要删除的节点是否是最后一个节点，如果是则不需要执行下方代码
                    temp.next.pre = temp.pre;
                }
                break;
            }
            temp = temp.next;
        }

        if (temp == null){
            System.out.println("没有找到该节点");
        }else{
            System.out.println("删除成功");
        }
    }
}