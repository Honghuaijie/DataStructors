package com.hong.hashTable.myHshtab;
import java.util.Scanner;

/**
 * ClassName: MyHashTabDemo
 * Package: com.hong.hashTable.myHshtab
 * Description:
 * 哈希表的模拟实现
 * 哈希表就是有多个链表构成，而链表又是由多个节点构成
 * 链表中的方法只能有哈希表调用
 * @Author honghuaijie
 * @Create 2023/10/2 18:13
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyHashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        MyHashTab hashTab = new MyHashTab(7);

        //写一个简单的菜单
        String key = "";
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("add:添加雇员");
            System.out.println("list:显示雇员");
            System.out.println("find:查找雇员");
            System.out.println("del:删除雇员");
            System.out.println("exit:退出系统");
            key = sc.next();

            switch (key){
                case "add" -> {
                    System.out.print("输入id:");
                    int id = sc.nextInt();
                    System.out.print("输入姓名：");
                    String name = sc.next();
                    MyEmp emp = new MyEmp(id,name);
                    hashTab.add(emp);
                }
                case "list" -> {
                    hashTab.list();
                }
                case "find" ->{
                    System.out.print("输入该雇员的id:");
                    int id = sc.nextInt();
                    hashTab.find(id);
                }
                case "del" ->{
                    System.out.print("输入要删除雇员的id:");
                    int id = sc.nextInt();
                    hashTab.delEmp(id);
                }
                case "exit" -> {
                    System.out.println("退出系统成功");
                    sc.close();
                    System.exit(0);
                }
            }
        }
    }
}



//哈希表
class MyHashTab{
    //链表数组
    MyEmpLinkedList[] myEmpLinkedListArray ;
    //数组的长度（new对象的时候传入）
    int maxSize;

    public MyHashTab(int maxSize){
        this.maxSize = maxSize;
        myEmpLinkedListArray = new MyEmpLinkedList[maxSize];
        for (int i = 0; i < maxSize; i++) {
            myEmpLinkedListArray[i] = new MyEmpLinkedList();
        }
    }

    //添加雇员
    public void add(MyEmp emp){
        int no = hashFun(emp.getId()); //计算出这个雇员应该放在哪个链表
        myEmpLinkedListArray[no].add(emp);
    }

    //遍历每个链表
    public void list(){
        for (int i = 0; i < maxSize; i++) {
            myEmpLinkedListArray[i].list(i+1);
        }
    }

    //查找雇员
    public void find(int id){
        int no = hashFun(id); //计算出这个雇员放在哪个链表
        MyEmp myEmp = myEmpLinkedListArray[no].find(id);
        if (myEmp !=null){
            System.out.printf("在第%d条链表中找到该雇员 id:%d",(no+1),myEmp.getId());
        }else{
            System.out.println("没有找到该雇员");
        }
    }


    //删除雇员
    public void delEmp(int id){
        int no = hashFun(id); //计算出这个雇员放在哪个链表
        myEmpLinkedListArray[no].delEmp(id);
    }

    //写一个散列函数:可以根据传入的id 找到该雇员存放在哪个链表
    public int hashFun(int id){
        return id % maxSize;
    }

}


//雇员链表
class MyEmpLinkedList{
    //头节点：这里我们规定一下，链表中第一个员工就是头节点
    private MyEmp head ;

    public MyEmp getHead() {
        return head;
    }

    public void setHead(MyEmp head) {
        this.head = head;
    }

    //添加雇员
    //要求链表中的雇员id升序
    public void add(MyEmp emp){
        //1。链表为空 。emp直接做头节点
        if (head == null){
            head = emp;
            return ;
        }
        //2.emp的id，小于头节点的ID， emp做头节点
        if (emp.getId() < head.getId()){
            emp.setNext(head);
            head = emp;
            return ;
        }

        //3.使用临时变量，遍历链表，找到当前节点比emp小，当前节点的下一个节点比emp大的位置
        MyEmp curEmp = head;
        while (true){
            if (curEmp.getId() == emp.getId()){
                System.out.println("该id已存在，无法添加");
                return;
            }
            //说明找到最后一个元素了，直接放入链表的最后就行了
            if (curEmp.getNext() == null){
                break;
            }

            //说明找到了emp该存放的位置（curemp和curemp下一个节点的中间）
            if (curEmp.getId() < emp.getId() && curEmp.getNext().getId() > emp.getId()){
                break;
            }
            curEmp = curEmp.getNext();
        }

        emp.setNext(curEmp.getNext());
        curEmp.setNext(emp);
        System.out.println("添加成功");
    }

    //遍历雇员
    public void list(int no){
        if (head == null){
            System.out.println("第"+ no+"个链表为空");
            return;
        }
        System.out.println("开始遍历第" + no + "个链表");
        MyEmp curEmp = head;
        while (true){
            System.out.printf("id: %d => name: %s \t", curEmp.getId(),curEmp.getName());

            if (curEmp.getNext() == null){
                break;
            }
            curEmp = curEmp.getNext();
        }

        System.out.println("\n遍历结束");
    }

    //查找雇员
    //如果没有找到就返回null ,找到了就返回该雇员
    public MyEmp find(int id){
        if (head == null){
            System.out.println("链表为空，无法查询");
        }
        MyEmp curEmp = head;
        while (true){
            if (curEmp == null){
                break;
            }
            if (curEmp.getId() == id){
                break;
            }
            curEmp = curEmp.getNext();
        }
        return curEmp;
    }

    //删除雇员
    public void delEmp(int id){
        if (head == null){
            System.out.println("链表为空，无法删除");
            return;
        }

        //1.如果要删除的id是头结点。那么需要改变头节点
        if (head.getId() == id){
            head = head.getNext();
            System.out.println("删除成功");
            return;
        }

        //2.要删除的id在头节点之后
        MyEmp curEmp = head;
        while (true){
            if (head.getNext() == null){
                break;
            }
            if (head.getNext().getId() == id){
                head.setNext(head.getNext().getNext());
                System.out.println("删除成功");
                return;
            }
        }

        System.out.println("删除失败");
    }

}

//雇员类
class MyEmp{
    private int id;
    private String name;
    private MyEmp next;

    public MyEmp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
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

    public MyEmp getNext() {
        return next;
    }

    public void setNext(MyEmp next) {
        this.next = next;
    }
}
