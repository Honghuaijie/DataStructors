package com.hong.hashTable;

import java.util.Scanner;
import java.util.zip.CRC32;

/**
 * ClassName: HashTabDemo
 * Package: com.hong.hashTable
 * Description:
 * 对自己写的hash表进行测试
 *
 * @Author honghuaijie
 * @Create 2023/10/1 11:16
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);

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
                    Emp emp = new Emp(id,name);
                    hashTab.add(emp);
                }
                case "list" -> {
                    hashTab.list();
                }
                case "find" ->{
                    System.out.print("输入该雇员的id:");
                    int id = sc.nextInt();
                    hashTab.findEmpById(id);
                }
                case "del" ->{
                    System.out.print("输入要删除雇员的id:");
                    int id = sc.nextInt();
                    hashTab.delEmpById(id);
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


//写一个hash表管理多条链表
class HashTab {
    //有一个链表数组
    private EmpLinkedList[] empLinkedListArray;
    private int size;//用来保存链表数组的长度

    //在初始化hash表时，给链表数组初始化
    public HashTab(int size) {
        empLinkedListArray = new EmpLinkedList[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //传入一个emp将其存入链表
    public void add(Emp emp) {
        //根据员工的id 得到该员工应该放入哪张链表
        int empLinkedListNo = hashFun(emp.id); //用来存放该员工应该存在哪一个链表
//        if (empLinkedListArray[empLinkedListNo ] == null){
//            //说明此链表还没有创建，先创建再添加
//            empLinkedListArray[empLinkedListNo] = new EmpLinkedList();
//        }
        empLinkedListArray[empLinkedListNo].add2(emp);
    }

    //编写散列函数，使用一个简单的取模法
    public int hashFun(int id) {
        return id % size;
    }

    //遍历所有链表
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i+1);
        }
    }

    //查找员工
    //传入一个id，根据这个id找到该员工，并返回
    public void findEmpById(int id) {
        int empLinkedListNo = hashFun(id); //计算出这个id应该存放在哪条链表

        Emp emp =  empLinkedListArray[empLinkedListNo].findEmpById(id);
        if (emp !=null){
            System.out.printf("在第%d个链表中找到该雇员 id = %d\n", empLinkedListNo+1,emp.id );
        }else{
            System.out.println("在哈希表中，没有找到该雇员");
        }
    }

    //删除员工
    //传入一个id，根据这个id找到该员工并删除
    public void delEmpById(int id){
        int empLinkedListNo = hashFun(id); //计算出这个id应该存放在哪条链表

        empLinkedListArray[empLinkedListNo].delEmpById(empLinkedListNo+1,id);
    }

}


//写一个链表，用来存放雇员
class EmpLinkedList {
    // 头结点 直接指向第一个Emp
    private Emp head;

    //添加雇员到链表
    //1.假定添加雇员是，id是自增长，即id 分配总是从小到大
    //因此我们将传入的雇员放入链表的最后即可
    public void add(Emp emp) {
        //如果head为null，说明链表为空，此时将emp作为链表的第一个Emp
        if (head == null) {
            head = emp;
            return;
        }

        //如果不是第一个雇员，那么就使用一个辅助指针指向该链表的最后一个雇员
        Emp curEmp = head;
        while (true) {
            //说明此时的curEmp指向的是该链表的最后一个雇员
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        curEmp.next = emp;
    }

    //1.假定添加雇员是，id不是子增长，即id 分配需要总是从小到大
    //此时我们需要对emp进行判断然后将emp放入指定的位置
    public void add2(Emp emp) {
        //如果head为null，说明链表为空，此时将emp作为链表的第一个Emp
        if (head == null) {
            head = emp;
            return;
        }
        //首先将头结点的id和emp的id进行比较
        //为什么？ 因为如果头节点比emp.id大，那么就需要将emp作为头节点，需要对头结点进行修改
        if (emp.id < head.id){
            emp.next = head;
            head = emp;
            return;
        }

        //如果不是第一个雇员，那么就使用一个辅助指针找到该雇员应该存放的位置
        Emp curEmp = head; //记录当前节点
        while (true) {
            //说明此时的curEmp指向的是该链表的最后一个雇员
            if (curEmp.next == null) {
                break;
            }

            //如果存在相同ID，也是不可以存的
            if (curEmp.id == emp.id){
                System.out.println("该链表中已有相同id，无法存");
                return;
            }
            //如果当前节点的id比emp的id小，当前节点的下一个节点的id比emp的id大
            //那么就可以将emp放入当前节点和当前节点的下一个节点的中间
            if (curEmp.id < emp.id && emp.id < curEmp.next.id) {
                break;
            }
            curEmp = curEmp.next;
        }
        emp.next = curEmp.next;
        curEmp.next = emp;
    }


    //遍历该链表
    public void list(int no) {
        //首先判断列表是否为空
        if (head == null) {
            System.out.println("第" + no + "个链表为空，无法遍历");
            return;
        }

        //此时链表不为空，使用一个临时变量，遍历即可
        System.out.println("第" + no + "个链表的信息为：");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=>id=%d name=%s\t", curEmp.id, curEmp.name);

            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }

        System.out.println("\n链表遍历结束");
    }

    //查找员工
    /**
     * @param id 传入一个id
     * @return 如果找到该员工就返回该员工，如果没有找到就报错
     */
    public Emp findEmpById(int id) {
        //使用一个临时变量，依次遍历链表，直到找到该员工
        //1.首先判断链表是否为空
        if (head == null) {
            throw new RuntimeException("该链表为空");
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                break; //这是curEmp指向的就是要查找的雇员
            }
            //退出
            if (curEmp.next == null) {
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;
        }
        return curEmp;
    }

    //删除雇员
    //传入一个id根据这个id找到该员工的位置，并进行删除
    public void delEmpById(int no,int id){
        if (head == null){ //判断链表是否为空
            System.out.println("第"+ (no)+ "个链表为空，无法删除");
            return ;
        }

        //判断该id是否是头结点
        if (head.id == id){
            head = head.next;
            System.out.println("该元素在第"+ (no)+ "个链表，删除成功");
            return;
        }

        //如果该id不是头节点，就使用一个临时变量，依次遍历即可
        Emp curEmp = head;
        while (true){
            if (curEmp.next == null){
                break;
            }
            if (curEmp.next.id == id){
                curEmp.next = curEmp.next.next;
                System.out.println("该元素在第"+ (no)+ "个链表，删除成功");
                break;
            }
            curEmp = curEmp.next;
        }
    }
}

//雇员类
class Emp {
    int id;
    String name;
    Emp next;  //默认为null

    public Emp(int id, String name) {
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
}