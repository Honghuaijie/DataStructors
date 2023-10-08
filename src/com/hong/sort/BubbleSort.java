package com.hong.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: BubbleSort
 * Package: com.hong.sort
 * Description:
 * 冒泡排序:从小到大排序
 *
 * @Author honghuaijie
 * @Create 2023/9/24 16:14
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class BubbleSort {

    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, 20};
//        int arr[] = {1,2,3,4,5,6};
        int arr[] = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间：" +date1Str);
        //测试冒泡排序
        bubbleSort(arr);
        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序前的时间：" +date2Str);


    }


    //冒泡排序的时间复杂度为O（n^2)
    //将冒泡排序封装成一个方法
    public static void bubbleSort(int[] arr){

        int temp = 0;
        //对冒泡排序进行优化
        boolean falg =false;  //判断是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            falg = false; //重置falg进行下次判断
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    falg = true;
                }
            }
            //System.out.println("第" + (i + 1) +"趟的排序结果为：" );
            //System.out.println(Arrays.toString(arr));
            if (!falg){
                break;
            }
        }
    }
}
