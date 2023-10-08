package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: SelectSort
 * Package: com.hong.sort
 * Description:
 *  选择排序
 * @Author honghuaijie
 * @Create 2023/9/24 17:02
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class SelectSort {

    public static void main(String[] args) {
        int arr[] = {101,34,119,1};

//        System.out.println("排序前：" + Arrays.toString(arr));
//        selectSort(arr);
//        System.out.println("排序后：" + Arrays.toString(arr));


    }

    //测试80000个数据，判断时间
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
        selectSort(arr);
        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序前的时间：" +date2Str);
    }


    //算法应该是 简单=> 复杂
    public static void selectSort(int arr[]){
        int k ; //用来记录最小值下标
        int temp;
        for (int i = 0; i < arr.length-1; i++) {
            k = i;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[k] > arr[j]){
                    k = j;
                }
            }
            if (k!=i){
                temp = arr[i];
                arr[i] = arr[k];
                arr[k] = temp;
            }
        }
    }
}
