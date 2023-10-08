package com.hong.recursion;

/**
 * ClassName: RecursionTest
 * Package: com.hong.recursion
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/9/20 15:12
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a true gift,
 * this is why  it is called a present
 */
public class RecursionTest {
    public static void main(String[] args) {
        test(4);

        System.out.println(factorial(3));
    }

    //打印问题
    public static void test(int n){
        if (n > 2){
            test(n-1);
        }
        System.out.println("n= " + n);
    }

    //阶乘问题
    public static int factorial(int n){
        if (n == 1){
            return 1;
        }else{
            return factorial(n - 1) * n ; //factorial(1) * 2
        }
    }
}
