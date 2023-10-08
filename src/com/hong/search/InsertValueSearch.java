package com.hong.search;

import java.util.Arrays;

/**
 * ClassName: InsertValueSearch
 * Package: com.hong.search
 * Description:
 * 插值算法
 *
 * @Author honghuaijie
 * @Create 2023/9/30 14:17
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class InsertValueSearch {
    public static void main(String[] args) {
//        int[] arr = new int[100];
//        for (int i = 1; i <= 100; i++) {
//            arr[i - 1] = i;
//        }
        int[] arr = {1, 8, 10, 89, 1000, 1234};


        System.out.println(insertValueSearch(arr, 0, arr.length - 1, 89));

    }

    //编写插值查找算法
    //说明，插值查找算法也要求数组是有序的
    public static int insertValueSearch(int[] arr, int left, int right, int findValue) {
        System.out.println("hello");
        //如果要查找的值小于数组最小值，或大于数组最大值， 就直接退出，没必要查找
        //注意:findValue< arr[0] || findValue > arr[arr.length-1] 必须需要
        //否则我们得到的mid可能越界
        //由于findValue参与了mid的运算，所以必须在参与运算前检验下findValue值
        if (left >= right || findValue< arr[0] || findValue > arr[arr.length-1]){
            return -1;
        }

        //求出mid ： 自适应的一种写法
        int mid = left + (findValue - arr[left]) * (right - left) / (arr[right] - arr[left]);
        if (!(left <= mid && mid <= right)){
            return -1;
        }
        if (findValue > arr[mid]){ //向右递归
            return insertValueSearch(arr,mid +1,right,findValue);
        }else if (findValue < arr[mid]){
            return insertValueSearch(arr,left,mid -1,findValue);
        }else{
            return mid;
        }

    }

}
