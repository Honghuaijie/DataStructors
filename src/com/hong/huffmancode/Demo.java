package com.hong.huffmancode;

import org.junit.Test;

import java.util.BitSet;

/**
 * ClassName: Demo
 * Package: com.hong.huffmancode
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/10/8 15:37
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class Demo {
    @Test
    public void test1(){
        byte b = -88;
        int i = b;

        String s = Integer.toBinaryString(i);  //返回的是i对应的二进制补码
        System.out.println(s);
    }
}
