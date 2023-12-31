package com.hong.queue;

/**
 * ClassName: ArrayQueQue
 * Package: com.hong.queque
 * Description:
 *          数组队列
 * @Author honghuaijie
 * @Create 2023/9/12 9:54
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue aq = new ArrayQueue(3);
        aq.addQueue(10);
        aq.addQueue(20);
        aq.addQueue(30);
        aq.getQueue();
        aq.getQueue();
        aq.getQueue();
    }
}

//使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {
    private int maxSize; //表示数组的最大长度
    private int front; //队列头
    private int rear; //队列尾
    private int[] arr; //该数据用于存放数据，模拟队列

    //创建队列的构造器
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[arrMaxSize];
        front = -1; //指向队列的头部，分析出front是指向队列头的前一个位置
        rear = -1;//指向队列的尾部，指向队列尾的数据（即就是队列的最后一个数据）
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n) {
        //判断队列是否满，如果满了就不能添加了
        if (isFull()) {
            System.out.println("队列已满，不能添加数据");
            return;
        }
        //执行到这里说明队列没有满，可以添加数据
        rear++;
        arr[rear] = n;
    }

    //取出队列数据
    public int getQueue(){
        //判断队列是否为空，如果为空就不能取出数据
        if(isEmpty()){
            //通过抛出异常来处理
            throw new RuntimeException("队列为空，不能取出数据");
        }
        front++;
        return arr[front];

    }
    //显示队列的所有数据
    public void showQueue(){
        if (isEmpty()){
            System.out.println("队列空的，没有数据");
            return;
        }
        for (int i = front+1; i<=rear;i++){
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    //显示队列的头数据，注意不是取出数据
    public int hearQueue(){
        if (isEmpty()){
            throw  new RuntimeException("队列空的，没有数据");
        }
        return arr[front+1];
    }


}