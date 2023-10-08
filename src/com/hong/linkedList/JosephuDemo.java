package com.hong.linkedList;

/**
 * ClassName: JosephuDemo
 * Package: com.hong.linkedList
 * Description:
 *     约瑟夫问题的重写
 *     什么是约瑟夫问题
 *     就是一群男孩围成一个圈，设定某个男孩k为第一个，再设定一个数字m，当k从1开始喊，其余人依次相加
 *     直到喊到m的人出列，并重新从1开始喊，直到圈中只有一个人
 *
 *     思路：有两大步
 *     第一步：创建一个单向循环列表
 *     1.设计一个first 用来存放第一个节点
 *     2.后面每创建一个节点就把该节点放到此first的链表中即可，并将最后一个节点的next属性指向first
 *
 *     第二步，出圈的解决
 *     根据用户的输入，生成一个出圈的顺序
 *     n = 5 即有5个人
 *     k = 1 即从第一个人开始报数
 *     m = 2 即喊到2的人出圈
 *     1.定义一个临时变量preBoy,用来存放first的前一个节点
 *     2.将first移动到第一个开始报数的人，并且preboy也要移动 （移动k-1次）
 *     3.设置一个循环每次循环都移动first和preboy  m-1次，找到要出圈的节点，将其出圈
 *          出圈的细节( first = first.next   preboy.next = first)
 *     ，直到圈中只剩下一个节点
 *     4.最后输出最后一个节点的编号
 * @Author honghuaijie
 * @Create 2023/9/17 10:26
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class JosephuDemo {
    public static void main(String[] args) {
        //测试链表的创建
        SingleCircleLinkedList1 sl = new SingleCircleLinkedList1();
        sl.add(5);
        //测试链表的遍历
        sl.showBoy();

        //测试出圈功能
        sl.josephu(1,1);
    }
}


//创建单向循环列表
class SingleCircleLinkedList1{
    Boy first;//用来存放链表中第一个节点

    /**
     *
     * @param num 用来指定创建链表的长度
     */
    //创建链表
    public void add(int num){
        Boy curBoy = null; //用来最后一个节点
        if (num < 1){
            System.out.println("请输入大于等于1的数字");
            return;
        }
        for (int i = 1; i <= num ; i++) {
            Boy boy = new Boy(i);
            if (i == 1){
                //创建第一个节点
                first = boy;
                boy.setNext(first); //完成自循环
                curBoy = first; //由于当前链表的最后一个节点是first 所以将first赋给curboy
            }else{
                curBoy.setNext(boy);
                boy.setNext(first); //由于boy是最后一个节点，最后一个节点需要指向first完成自循环
                curBoy = boy; //将boy指向curBoy
            }
        }
    }

    //链表的遍历
    public void showBoy(){
        if (first == null){
            System.out.println("当前链表为空，无法遍历");
            return;
        }
        //使用一个临时变量来存储first
        Boy temp = first;
        while (true){
            System.out.println(temp);

            if (temp.getNext() == first){
                break;
            }
            temp = temp.getNext();

        }
    }

    /**
     *
     * @param k 表示从第k个人开始报数
     * @param m 表示报到m的人出圈
     */
    //方法1
    public void josephu(int k,int m){
        //1.定义一个临时变量preBoy,用来存放first的前一个节点
        Boy preBoy = first;
        while (true){
            if (preBoy.getNext() == first){
                break;
            }
            preBoy = preBoy.getNext();
        }
        //执行到这里preBoy已经指向了first的前一个节点

        //2.将first移动到第一个开始报数的人，并且preboy也要移动 （移动k-1次）
        for (int i = 0; i < k-1; i++) {
            first = first.getNext();
            preBoy = preBoy.getNext();
        }
        //此时 first已经移动到第一个开始报数的人了
        //3.设置一个循环每次循环都移动first和preboy  m-1次，找到要出圈的节点，
        // 将其出圈，直到圈中只剩下一个节点
        while (first != preBoy){
            for (int i = 0; i < m-1; i++) {
                first = first.getNext();
                preBoy = preBoy.getNext();
            }
            //此时first已经移动到要出圈的那个节点了
            System.out.printf("编号为%d的男孩出圈了\n",first.no);
            first = first.getNext();
            preBoy.setNext(first);
        }
        //4.最后输出最后一个节点的编号
        System.out.printf("最后一个在圈内的男孩的编号为%d",first.no);
    }

    //方法2
    public void josephu2(int k,int m){
        //1.定义一个临时变量preBoy,用来存放first的前一个节点
        Boy preBoy = first;
        while (true){
            if (preBoy.getNext() == first){
                break;
            }
            preBoy = preBoy.getNext();
        }
        //执行到这里preBoy已经指向了first的前一个节点

        //2.将first移动到第一个开始报数的人，并且preboy也要移动 （移动k-1次）
        for (int i = 0; i < k-1; i++) {
            first = first.getNext();
            preBoy = preBoy.getNext();
        }
        int i = 1;// 用来计数
        //此时 first已经移动到第一个开始报数的人了
        //3.设置一个循环每次循环都移动first和preboy  m-1次，找到要出圈的节点，
        // 将其出圈，直到圈中只剩下一个节点
        while (first != preBoy){
            if (i == m){ //说明有人要出圈了
                i = 1 ;//清空i，
                System.out.printf("编号为%d的男孩出圈了\n",first.no);
                first = first.getNext();
                preBoy.setNext(first);
                continue;
            }
            //当没有人要出圈的时候，有三个步骤1i++,2.将first和preboy后移一位
            i++;
            first = first.getNext();
            preBoy = preBoy.getNext();

        }
        //4.最后输出最后一个节点的编号
        System.out.printf("最后一个在圈内的男孩的编号为%d",first.no);
    }
}

//创建男孩类
class Boy{
    int no;
    private Boy next; //用来存放下一个节点

    public Boy(int no){
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                '}';
    }
}


