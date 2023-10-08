package com.hong.sort.selfSort;

import java.util.Arrays;

/**
 * ClassName: MyRadixSort
 * Package: com.hong.sort.selfSort
 * Description:
 *     复习基数排序(桶排序)----只解决正数整数
 *     它将数组中的数按照低位开始排序，直到高位排完
 * @Author honghuaijie
 * @Create 2023/9/30 10:42
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyRadixSort {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        myRadixSort(arr);
    }

    public static void myRadixSort(int[] arr){
        //前提条件
        //1 存放各位数的桶（二维数组）
        int[][] bucket = new int[10][arr.length];
        //2.存放每个桶中存放的个数(一维数组)
        int[] bucketElementCounts = new int[10];

        //找到数组中最大数，用来确定循环的轮数
        int max= 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max){
                max = arr[i];
            }
        }
        //求出最大数的位数
        int maxLength = (max + "").length();
        int digitElement;
        //进行循环，每次循环都将指定位数上的数放入桶中
        //digit是用来指定每次取哪一位上的数，从个位开始取
        for (int i = 0, digit = 1; i <maxLength; i++, digit *= 10) {

            //循环取出指定位上的数，放入桶中
            for (int j = 0; j < arr.length; j++) {
                digitElement  = arr[j] / digit % 10;
                bucket[digitElement][bucketElementCounts[digitElement]] = arr[j];
                bucketElementCounts[digitElement]++;
            }

            //从桶中依次取出放入arr中
            int index = 0; //用来确定arr的索引
            for (int j = 0; j < bucketElementCounts.length; j++) {
                if (bucketElementCounts[j] !=0){

                    for (int k = 0; k < bucketElementCounts[j]; k++) {
                        arr[index] = bucket[j][k];
                        index += 1;
                    }

                    bucketElementCounts[j] = 0; //从该桶中取完数据后，将计数清零
                }
            }

            System.out.println("第" + (i + 1) + "轮:" + Arrays.toString(arr));
        }


    }
}
