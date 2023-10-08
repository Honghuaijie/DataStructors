package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: InsertionSort
 * Package: com.hong.sort
 * Description:
 *  插入排序
 * @Author honghuaijie
 * @Create 2023/9/24 18:47
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class InsertionSort {

    public static void main(String[] args) {
        int arr[] = {101,34,119,1,-1,89};
//        int arr[] = {17,3,25,14,20,9};

        System.out.println("排序前：" + Arrays.toString(arr));
        insertionSort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));
    }


    //测试80000个数据用插入排序需要多长时间
    @Test
    public void test1(){
        int arr[] = new int[80000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间：" +date1Str);
        //测试选择排序
        insertionSort(arr);
        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序前的时间：" +date2Str);
    }

    //位置只有在最后一次循环才会固定
    public static void insertionSort(int[] arr){
        int temp;
        int insertIndex ;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            insertIndex = i-1;

            //arr[insertIndex] >temp 说明待插入的数还没有找到适当的位置
            while (insertIndex >=0 && arr[insertIndex] >temp){
                arr[insertIndex+1] = arr[insertIndex];
                insertIndex--;
            }

            arr[insertIndex +1] = temp;
        }
    }
}
