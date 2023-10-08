package com.hong.sort;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: MergetSort
 * Package: com.hong.sort
 * Description:
 *   归并排序
 *   首先非两大步
 *   第一步：分解
 *      将一个序列分解为若干个序列，直到每一个序列中只有一个数据
 *   第二步：合并
 *      相邻的序列进行二路归并，直接合成一个新的有序序列
 * @Author honghuaijie
 * @Create 2023/9/29 10:29
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MergetSort {
    public static void main(String[] args) {
//        int[] arr = {8,4,5,7,1,3,6,2};
        int[] arr = {9,8,7,6,5,4,3,2,1};
        int[] temp = new int[arr.length];
        mergeSort(arr,0,arr.length-1,temp);
        System.out.println(Arrays.toString(arr));

    }

    //测试归并排序的速度
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
        mergeSort(arr,0,arr.length-1,temp);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));
    }

    //分解和合并的的方法
    public static void mergeSort(int arr[],int left , int right, int[] temp){

        if (left < right){
            int mid = (left + right) / 2; //中间索引(左边序列的最后一个元素)
            //向左递归分解
            mergeSort(arr,left,mid,temp);
            //向右递归分解
            mergeSort(arr,mid+1,right,temp);

            //合并
            merge(arr,left,mid,right,temp);
//            System.out.println("xxxxx: left:" +left  + "right" + right);
        }

    }

    //合并的方法
    /**
     *
     * @param arr 排序的原始数组
     * @param left  左边有序序列的起始位置
     * @param mid  右边有序序列的最后位置
     * @param right  右边有序序列的最后位置
     * @param temp  中转的数组
     */
    public static void merge(int[] arr,int left, int mid, int right,int temp[]){
        int i = left; //左边有序序列的起始位置
        int j = mid + 1;//右边有序序列的起始位置
        int t = 0; //指向temp数组的当前索引（指向数组中最后元素的后一位位置）

        //第一步
        //先把两边数据按照规则依次填充到temp中
        //直到有一边序列的数据全部填充到temp才结束
        while (i <= mid && j <= right){
            //按照规则进行判断(将比较后小的数据放入temp)
            if (arr[i] <= arr[j]){ //此时arr[i]这个元素小，将其放入temp中
                temp[t] = arr[i];
                t += 1;
                i += 1;
            }else{
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }
        //第二步
        //将还有剩余数据的那个序列的剩余数据全部填充到temp中
        while (i <= mid ){
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }

        while (j <= right ){
            temp[t] = arr[j];
            t += 1;
            j += 1;
        }
        //第三步
        //将temp中的数据填充到arr中
        //注意：不是每次都要拷贝所有
        int lefttemp = left;
        t = 0;
        while (lefttemp <= right){
            arr[lefttemp] = temp[t];
            lefttemp += 1;
            t += 1;
        }
    }
}
