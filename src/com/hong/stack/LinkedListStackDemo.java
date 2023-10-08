package com.hong.stack;

import org.junit.Test;

import java.util.Scanner;

/**
 * ClassName: LinkedListStackDemo
 * Package: com.hong.stack
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/9/17 14:16
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class LinkedListStackDemo {
    //测试LinkedListStack是否正确
    @Test
    public void test1() {
        LinkedListStack stack = new LinkedListStack();
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner sc = new Scanner(System.in);
        while (loop) {
            System.out.println("show:表示显示栈");
            System.out.println("exit:表示退出程序");
            System.out.println("push:表示添加数据到栈(入栈)");
            System.out.println("pop:表示从栈取出数据(出栈)");

            System.out.println("请输入指令");
            key = sc.next();
            switch (key) {
                case "show" -> stack.show();
                case "exit" -> loop = false;
                case "push" -> {
                    System.out.println("请输入要入栈的数据");
                    int value = sc.nextInt();
                    stack.push(new Node(value));
                    System.out.println("入栈成功");
                }
                case "pop" -> {
                    try {
                        System.out.println("出栈成功，出栈的数据为：" + stack.pop());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
    }
}


//使用链表来模拟栈是不存在栈满的情况的
class LinkedListStack {
    Node top; //用来记录栈顶


    //判断栈是否为空
    public boolean isFull() {
        return top == null;
    }

    //入栈
    public void push(Node node) {
        if (isFull()) {
            top = node;
        } else {
            node.setPre(top);
            top = node;
        }
    }

    //出栈
    public Node pop() {
        if (isFull()) {
            throw new RuntimeException("栈为空，无法取出数据");
        }

        Node temp = top;
        top = top.getPre();
        return temp;
    }

    //遍历
    public void show() {
        if (isFull()) {
            System.out.println("栈为空，无法遍历");
            return;
        }
        Node temp = top; //使用临时变量来遍历
        while (true) {
            System.out.println(temp);
            if (temp.getPre() == null) {
                break;
            }
            temp = temp.getPre();
        }
    }
}

class Node {
    int value;
    private Node pre;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }
}