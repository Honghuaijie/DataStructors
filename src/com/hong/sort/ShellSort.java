package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: ShellSort
 * Package: com.hong.sort
 * Description:
 * 希尔排序  将数组分组
 * 分组的策略就是将数组的长度进行平分，直到平方到0
 * 每组进行插入排序，
 *
 *  希尔排序的目的就是为了将小的数尽可能向前面 移，避免小的数在排序后面移动很多次
 *  希尔排序就是为了优化插入排序
 *
 * @Author honghuaijie
 * @Create 2023/9/24 17:02
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0,-1};
        int[] arr1 = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        shellSort(arr);

        System.out.println(Arrays.toString(arr));

    }

    // //测试80000个数据用希尔排序需要多长时间
    //测试 shellSort 交换法
    @Test
    public void test1(){
        int arr[] = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SS");
        System.out.println("排序前" + simpleDateFormat.format(date1));
        shellSort(arr);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));


    }

    //测试 shellSort2 移位法
    @Test
    public void test2(){
        int arr[] = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SS");
        System.out.println("排序前" + simpleDateFormat.format(date1));
        shellSort2(arr);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));

    }


    //使用逐步推导的方式来编写希尔排序
    //交换法
    public static void shellSort(int[] arr) {
        int temp;
        int count = 0;
        //根据前面的逐步分析，使用循环处理
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }else{
                        break;
                    }
                }
            }
            System.out.println("第" + (++count) + "轮排序的结果：" + Arrays.toString(arr));
        }
//
//        //第一步，将数组分为5组
//
//        for (int i = 5; i < arr.length; i++) {
//            for (int j = i-5; j>=0; j -= 5) {
//                if (arr[j] > arr[j+5]){
//                    temp = arr[j];
//                    arr[j] = arr[j+5];
//                    arr[j+5] = temp;
//                }
//            }
//        }
//
//        System.out.println("第一轮排序的结果：" + Arrays.toString(arr));
//
//        //第二轮，将数组分为5/2 = 2组
//
//        for (int i = 2; i < arr.length; i++) {
//            for (int j = i-2; j>=0; j -= 2) {
//                if (arr[j] > arr[j+2]){
//                    temp = arr[j];
//                    arr[j] = arr[j+2];
//                    arr[j+2] = temp;
//                }
//            }
//        }
//
//        System.out.println("第二轮排序的结果：" + Arrays.toString(arr));
//
//        //第三轮，将数组分为2/2= 1组
//
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = i-1; j>=0; j -= 1) {
//                if (arr[j] > arr[j+1]){
//                    temp = arr[j];
//                    arr[j] = arr[j+1];
//                    arr[j+1] = temp;
//                }
//            }
//        }
//
//        System.out.println("第三轮排序的结果：" + Arrays.toString(arr));
    }

    //移位法
    public static void shellSort2(int[] arr) {
        int temp = 0;
        int count = 0;
        int j;
        //根据前面的逐步分析，使用循环处理
        for (int gap = arr.length/2; gap>0; gap /=2){
            for (int i = gap; i < arr.length; i++) {
                temp = arr[i]; //temp存储无序组的第一个元素
                j = i - gap;  //j 指向有序组的最后一个元素
                while (j >= 0 && arr[j] > temp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = temp;
            }
//            System.out.println("第" + (++count)+"轮排序的结果：" + Arrays.toString(arr));
        }

        //第一步，将数组分为5组
//        for (int i = 5; i < arr.length; i++) {
//            temp = arr[i];
//            j = i - 5;
//            while (j >= 0 && arr[j] > temp) {
//                arr[j + 5] = arr[j];
//                j -= 5;
//            }
//            arr[j + 5] = temp;
//        }
//
//        System.out.println("第一轮排序的结果：" + Arrays.toString(arr));
//
//        //第二步，将数组分为5/2=2组
//        for (int i = 2; i < arr.length; i++) {
//            temp = arr[i];
//            j = i - 2;
//            while (j >= 0 && arr[j] > temp) {
//                arr[j + 2] = arr[j];
//                j -= 2;
//            }
//            arr[j + 2] = temp;
//        }
//
//        System.out.println("第二轮排序的结果：" + Arrays.toString(arr));
//
//        //第三步，将数组分为2/2=1组
//        for (int i = 1; i < arr.length; i++) {
//            temp = arr[i];
//            j = i - 1;
//            while (j >= 0 && arr[j] > temp) {
//                arr[j + 1] = arr[j];
//                j -= 1;
//            }
//            arr[j + 1] = temp;
//        }
//
//        System.out.println("第三轮排序的结果：" + Arrays.toString(arr));
    }
}
