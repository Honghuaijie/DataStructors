package com.hong.stack;

import org.junit.Test;

import java.util.Scanner;

/**
 * ClassName: ArrayStackDemo
 * Package: com.hong.stack
 * Description:
 *  使用数组来模拟栈
 * @Author honghuaijie
 * @Create 2023/9/17 13:22
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class ArrayStackDemo {

    //测试ArrayStack是否正确
    @Test
    public void test1(){
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner sc = new Scanner(System.in);
        while (loop){
            System.out.println("show:表示显示栈");
            System.out.println("exit:表示退出程序");
            System.out.println("push:表示添加数据到栈(入栈)");
            System.out.println("pop:表示从栈取出数据(出栈)");

            System.out.println("请输入指令");
            key = sc.next();
            switch (key){
                case "show" -> stack.list();
                case "exit" -> loop = false;
                case "push" -> {
                    System.out.println("请输入要入栈的数据");
                    int value = sc.nextInt();
                    stack.push(value);
                    System.out.println("入栈成功");
                }
                case "pop" -> {
                    try {
                        System.out.println("出栈成功，出栈的数据为：" + stack.pop());
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
    }
}

//表示栈
class ArrayStack{
    private int maxSize; //栈的大小
    private int[] stack; //数组模拟栈，数据都存放入数组中
    private int top = -1; //指向的是栈顶, 默认为-1

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //判断栈是否已满
    public boolean isFull(){
        return top == maxSize-1;
    }

    //判断栈是否为空
    public boolean isEmpty(){
        return top == -1;
    }
    //入栈
    public void push(int value){
        //首先判断栈是否已满，满了就不能添加了
        if (isFull()){
            System.out.println("栈已满，无法添加");
            return;
        }
        top++;
        stack[top] = value;
    }
    //出栈:将栈顶的数据返回，并将top--
    public int pop(){
        //首先判断栈是否为空
        if (isEmpty()){
            throw  new RuntimeException("栈为空，无法出栈");
        }

        int value = stack[top];
        top--;
        return value;

    }

    //遍历栈的情况[遍历栈],
    public void list(){
        if (isEmpty()){
            System.out.println("栈空， 没有数据");
            return;
        }
        for (int i = top; i>=0; i--){
            System.out.printf("stack[%d]= %d\n",i,stack[i]);
        }
    }

}
