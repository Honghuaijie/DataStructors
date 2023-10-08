package com.hong.search;

import java.util.Arrays;

/**
 * ClassName: FibonacciSearch
 * Package: com.hong.search
 * Description:
 * 斐波那契排序法：需要有序数数组
 * 通过斐波那契数列，找到mid值
 *
 * @Author honghuaijie
 * @Create 2023/9/30 15:47
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class FibonacciSearch {
    public static int maxSize = 20; //决定生成多少个斐波那契数

    public static void main(String[] args) {
        int[] arr = {1,8, 10, 89, 1000, 1234};
        System.out.println(fibSearch(arr, 1234));
    }

    //生产斐波那契数列
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f;
    }

    /**
     * @param arr 原数组
     * @param key 要查找的哪个数
     * @return 找到就返回该数的下标， 没找到就返回-1
     */
    public static int fibSearch(int[] arr, int key) {
        int left = 0; //数组的左边
        int high = arr.length -1; //数组的右边
        int k = 0; //就是来确定这个数组的长度应该是斐波那契数列中的哪一个具体的数；比如应该是5、8、13
        int mid; //黄金分割点
        int[] f = fib(); //得到斐波那契数列

        //计算出数组长度应该是斐波那契数列中哪一个具体的数
        //为什么需要f[k]-1 ， 因为数组是从0开始的
        while (high > f[k]-1){
            k ++;
        }
        //对数组进行扩容，使数组长度为斐波那契数
        int[] temp = Arrays.copyOf(arr,f[k]);
        //由于扩容后，high后面的元素都为0
        //将high位置后的元素全部修改成arr[high]
        for (int i = high+1; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        System.out.println(Arrays.toString(temp));
        //开始循环查找，直到找到key 结束
        while (left <= high){
            //计算出黄金分割点
            //首先我们需要知道斐波那契数可以分两段，一段的长度是f[k-1]，另一端的长度是f[k-2]
            //我们将第一段的最后一个元素，看做是黄金分割点也是f[k-1] 在数组中的表现就是f[k-1]-1
            mid = left + f[k-1] -1;

            if (key < temp[mid]) {  //向数组的左边插座
                high = mid - 1;
                k -=1;
            }else if (key > temp[mid]){ //向数组的右边查找
                left = mid + 1;
                k -=2;
            }else {
                if (mid <= high){
                    return mid;
                }else{
                    //[1, 8, 10, 89, 1000, 1234, 1234, 1234]假如找的数是1234，而最后查到1234的下标是6
                    //那么可以直接输出high值
                    return high; //加入
                }
            }
        }

        return -1;

    }
}
