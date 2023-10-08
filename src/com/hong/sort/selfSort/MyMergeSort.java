package com.hong.sort.selfSort;

import java.util.Arrays;

/**
 * ClassName: MyMergeSort
 * Package: com.hong.sort.selfSort
 * Description:
 *   复习归并排序
 *   归并排序主要分为 分解+合并
 *   分解是将数组中的元素分为一个一个的
 *   合并是将相邻的一个一个合并，直接合成一个新的数组
 * @Author honghuaijie
 * @Create 2023/9/30 10:56
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyMergeSort {

    public static void main(String[] args) {
        int[] arr = {9,8,7,6,5,4,3,2,1};
        int[] temp = new int[arr.length];
        myMegerSort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));
    }


    //分解

    /**
     *
     * @param arr 原数组
     * @param left  起始位置
     * @param right  终止位置
     * @param temp
     */
    public static void myMegerSort(int[] arr, int left, int right, int[] temp){
        int mid = (left + right) / 2; //作为中轴值

        if (left < right){
            //左递归
            myMegerSort(arr,left,mid,temp);
            //右递归
            myMegerSort(arr,mid+1,right,temp);

            //此时，肯定分解到最后了，可以进行相邻序列的合并了
            myMerge(arr,left,mid,right,temp);
        }



    }

    //合并

    /**
     *
     * @param arr 原数组
     * @param left  左边序列的第一个元素的下标
     * @param mid  左边序列的最后一个元素的下标
     * @param right  右边序列的最后一个元素的下标
     * @param temp  临时数组
     */
    public static void myMerge(int[] arr, int left,int mid,int right,int[] temp){
        int i = left; //左边序列的第一个元素的下标
        int j = mid + 1; //右边序列的第一个元素的下标
        int t = 0; //记录temp数组的索引

        //1.将左边序列和右边序列进行比较，小的数放入temp中，直到有一个序列走到尽头，终止循环
        while (i <= mid && j <= right){
            if (arr[i] <= arr[j]){
                temp[t] = arr[i];
                t++;
                i++;
            }else{
                temp[t] = arr[j];
                t++;
                j++;
            }
        }

        //2.将有剩余数据的序列中的剩余数据依次放入temp中
        while (i <= mid ){
            temp[t] = arr[i];
            t++;
            i++;
        }

        while (j <= right){
            temp[t] = arr[j];
            t++;
            j++;
        }
        //3.将temp中的数据依次放入arr的指定位置上
        int leftIndex =left;

        for (int k = 0; k < t; k++) {
            arr[leftIndex] = temp[k];
            leftIndex += 1;
        }

    }
}
