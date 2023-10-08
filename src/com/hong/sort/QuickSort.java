package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: QuickSort
 * Package: com.hong.sort
 * Description:
 *  快速排序：对冒泡排序的一种简化
 * @Author honghuaijie
 * @Create 2023/9/26 13:13
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {-9,78,0,23,-567,70};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }


    //测试快速排序的速度
    @Test
    public void test1(){
        int arr[] = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SS");
        System.out.println("排序前" + simpleDateFormat.format(date1));
        quickSort(arr,0,arr.length-1);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));

    }

    /*
    * 思路 取一个中轴值（自己选择，可以是中间也可以是开头、末尾），将数组分为两部分
    * 左半部分的数据小于中轴值
    * 右半部分的数据大于中轴值
    * 之后将这两部分进行左右递归，直到整个数组是有序的
    *
    * */
    /**
     *
     * @param arr 传入要排序的数组
     * @param left 传入要排序的序列的最左边的索引
     * @param right 传入要排序的序列的最右边的索引
     */

    public static void quickSort(int[] arr,int left, int right){
        int l = left; //记录左边界索引
        int r = right; //记录右边界索引
        int pivot = arr[(left + right) / 2]; //记录中轴值
        int temp;
        //while循环的目的就是为了将比中轴值小的放在左边，比中轴值大的放在右边

        while (l < r){
            //从左边开始找到比中轴值大的，就停止
            while (arr[l] < pivot){
                l++;
            }
            //从右边开始找到比中轴值小的，就停止
            while (arr[r] > pivot){
                r--;
            }

            //如果l>=r 那么就说明已经将比中轴值小的放在左边，比中轴值大的放在右边了
            if (l >= r){
                break;
            }

            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //为了避免遇到两个相同的值出现死循环
            if (arr[l] == pivot) {
                r--;
            }

            if (arr[r] == pivot){
                l++;
            }
        }

        //如果l==r那么他们指向的是中轴值，就需要将中轴值排除掉
        if (l == r){
            l++;
            r--;
        }

        //进行左递归
        if (left < r){
            quickSort(arr,left,r);
        }

        //进行右递归
        if (right > l){
            quickSort(arr,l,right);
        }


    }

}
