package com.hong.queue;

import java.util.Scanner;

/**
 * ClassName: CircleArrayQueue
 * Package: com.hong.queue
 * Description:
 *      循环队列
 * @Author honghuaijie
 * @Create 2023/9/12 11:20
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        System.out.println("测试数组模拟环形队列的案例");
        //创建一个队列
        CircleArray queue = new CircleArray(3); //队列的有效数据最大为2，因为空出一个空间作为约定
        char key = ' '; //接受用户输入
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            key = sc.next().charAt(0); //接受一个字符
            switch (key){
                case 's' -> queue.showQueue();
                case 'e' -> {
                    sc.close();
                    loop = false;
                }
                case 'a' -> {
                    System.out.println("请输入一个数据：");
                    int n  = sc.nextInt();
                    queue.addQueue(n);
                }
                case 'g' -> {
                    try {
                        int res = queue.getQueue();
                        System.out.println("取出的数据是" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 'h' -> {
                    try {
                        int res = queue.hearQueue();
                        System.out.println("取出的数据是" + res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}

//数组环形队列 环形队列只能存储（数组长度-1）个元素
class CircleArray{
    private int maxSize; //表示数组的最大长度
    //front的含义做一个调整，表示队列的头个元素，也就是arr[front]就是队列的首个元素
    //front的初始值为0
    private int front;
    //rear的含义做一个调整，rear指向的是队列的最后一个元素的后一个位置，希望空出一个空间作为约定
    //约定的位置是动态变化的
    //rear的初始值为0;
    private int rear;
    private int[] arr; //该数据用于存放数据，模拟队列

    public CircleArray(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    //判断队列是否已满
    //思路就是末尾的指针加1是否等于首部的指针（为了防止下标溢出还需要多队列的最大个数取余）
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        return rear == front;
    }

    //向队列添加数据
    public void addQueue(int n){
        //判断队列是已满
        if (isFull()){
            System.out.println("队列已满，无法添加数据");
            return;
        }
        arr[rear] = n;
        rear = (rear+1) % maxSize;// 防止下标溢出
    }

    //取出队列中的数据
    public int getQueue(){
        //判断队列是否为空
        if (isEmpty()){
            throw new RuntimeException("队列为空，不能取出数据");
        }
        //因为front指向的是队列中的第一个元素，所以可以直接取
        int temp = arr[front];
        front = (front + 1) % maxSize;
        return temp;
    }

    //遍历队列中的所有元素
    public void showQueue(){
        //队列是否为空
        if (isEmpty()){
            System.out.println("队列已空，无法遍历数据");
            return;
        }
        //首先需要知道数组中有多少有效数据
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d] = %d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    //用来获取数组中的有效数据的个数
    public int size(){
        //rear = 1;
        //front = 0;
        //maxsize = 3;
        return (rear + maxSize - front) % maxSize;
    }

    //获取队列的首个元素
    public int hearQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列为空，不能取出数据");
        }
        return arr[front];
    }

}
