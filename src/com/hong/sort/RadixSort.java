package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: RadixSort
 * Package: com.hong.sort
 * Description:
 * 基数排序(位数越小，排的越快)
 * 前提条件，需要准备10个桶（二维数组）  一个计数的（一维数组）
 * 一开始首先将数组中的个位取出，并将其放入相对于的桶（如果个位是5，就将其放入下标为5的桶）
 * 数据都放入后，依次从0的桶开始取出数据，放入数组中
 * 然后将数组中的十位取出，并将其放入相对于的桶（如果十位是5，就将其放入下标为5的桶）
 * *   数据都放入后，依次从0的桶开始取出数据，放入数组中
 * 直到数组有序才截止
 *
 * @Author honghuaijie
 * @Create 2023/9/29 14:12
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        radixSort(arr);

    }


    // 测试基数排序的速度
    @Test
    public void test1(){
        int arr[] = new int[8000000];
        int[] temp = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SS");
        System.out.println("排序前" + simpleDateFormat.format(date1));
        radixSort(arr);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));
    }

    public static void radixSort(int[] arr) {
        //前提条件：
        //定义一个二维数组（代表10个桶），每个桶是一位数组
        //说明
        //1.二维数组包含10个一维数组
        //2.为了防止溢出，每个一维数组都必须为arr.length
        //3.由此可以明确，基数排序是以空间换之间
        int[][] bucket = new int[10][arr.length];

        //为了记录每个桶中存放多少个数据，我们定义一个一维数组来存放各个桶中存放的个数
        int[] bucketElementCounts = new int[10];


        //根据推导，将其写成循环
        //如何确定循环多少轮？ 找到该最大的数并确定他的位数
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int maxLength = (max + ("")).length();

        //digit是用来确定每次取出的是哪一位上的数
        for (int i = 0,digit = 1; i < maxLength; i++,digit *= 10) {
            //第一步：将数据放入相对于的桶中
            //（针对每个元素的对应位进行排序处理），第一次是个位，第二次是十位，第三次是百位....
            for (int j = 0; j < arr.length; j++) {
                //
                int digitOfElement = arr[j] / digit % 10;
                //将其放入相对于的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                //将下标为digitOfElement的桶的个数加1
                bucketElementCounts[digitOfElement]++;
            }

            //第二步：从桶中依次取出数据
            int index = 0; //用来记录arr的下标位置
            //遍历每一个桶，并将桶中的数据放入arr
            for (int k = 0; k < bucket.length; k++) {
                //如果bucketElementCounts[k]为0，说明桶中没有数据，就不进行操作
                if (bucketElementCounts[k] != 0) {
                    //循环取出数据放入arr中
                    for (int p = 0; p < bucketElementCounts[k]; p++) {
                        arr[index] = bucket[k][p];
                        index++;
                    }
                    //将桶的个数清零
                    bucketElementCounts[k] = 0;
                }
            }
        }

//        //第一轮：（针对个位数进行排序）
//        //第一步：将数据放入相对于的桶中
//        for (int j = 0; j < arr.length; j++){
//            //取出个位数
//            int digitOfElement = arr[j] % 10;
//            //将其放入相对于的桶中
//            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
//            //将下标为digitOfElement的桶的个数加1
//            bucketElementCounts[digitOfElement]++;
//        }
//
//        //第二步：从桶中依次取出数据
//        int index = 0; //用来记录arr的下标位置
//
//        for (int k = 0; k < bucket.length; k++){
//            //如果bucketElementCounts[k]为0，说明桶中没有数据，就不进行操作
//            if (bucketElementCounts[k] !=0){
//
//                //循环取出数据放入arr中
//                for (int i = 0; i < bucketElementCounts[k]; i++) {
//                    arr[index] = bucket[k][i];
//                    index++;
//                }
//                //将桶的个数清零
//                bucketElementCounts[k] = 0;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//
//        //第二轮：（针对十位数进行排序）
//        //第一步：将数据放入相对于的桶中
//        for (int j = 0; j < arr.length; j++){
//            //取出十位数
//            int digitOfElement = arr[j] /10 % 10;
//            //将其放入相对于的桶中
//            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
//            //将下标为digitOfElement的桶的个数加1
//            bucketElementCounts[digitOfElement]++;
//        }
//
//        //第二步：从桶中依次取出数据
//        index = 0; //用来记录arr的下标位置
//
//        for (int k = 0; k < bucket.length; k++){
//            //如果bucketElementCounts[k]为0，说明桶中没有数据，就不进行操作
//            if (bucketElementCounts[k] !=0){
//
//                //循环取出数据放入arr中
//                for (int i = 0; i < bucketElementCounts[k]; i++) {
//                    arr[index] = bucket[k][i];
//                    index++;
//                }
//                //将桶的个数清零
//                bucketElementCounts[k] = 0;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//
//        //第三轮：（针对百位数进行排序）
//        //第一步：将数据放入相对于的桶中
//        for (int j = 0; j < arr.length; j++){
//            //取出百位数
//            int digitOfElement = arr[j] /100 % 10;
//            //将其放入相对于的桶中
//            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
//            //将下标为digitOfElement的桶的个数加1
//            bucketElementCounts[digitOfElement]++;
//        }
//
//        //第二步：从桶中依次取出数据
//        index = 0; //用来记录arr的下标位置
//
//        for (int k = 0; k < bucket.length; k++){
//            //如果bucketElementCounts[k]为0，说明桶中没有数据，就不进行操作
//            if (bucketElementCounts[k] !=0){
//
//                //循环取出数据放入arr中
//                for (int i = 0; i < bucketElementCounts[k]; i++) {
//                    arr[index] = bucket[k][i];
//                    index++;
//                }
//                //将桶的个数清零
//                bucketElementCounts[k] = 0;
//            }
//        }
//        System.out.println(Arrays.toString(arr));
    }
}
