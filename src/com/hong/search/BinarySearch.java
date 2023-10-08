package com.hong.search;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ClassName: BinarySearch
 * Package: com.hong.search
 * Description:
 * 二分查找:前提：一定得是一个有序数组
 *
 * @Author honghuaijie
 * @Create 2023/9/30 12:55
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int resIndex = binarySearch(arr, 0, arr.length - 1, 1000);
        System.out.println(resIndex);
    }

    //测试binarySearch2
    @Test
    public void test1(){
        int[] arr = {1, 8, 10, 89, 1000,1000,1000,1000,1000,1000,1000,1000, 1000};
        ArrayList<Integer> findIndexs = new ArrayList<>();
        ArrayList<Integer> resIndexList = binarySearch2(arr, 0, arr.length - 1, 1000);
        System.out.println(resIndexList);
    }

    /**
     * @param arr     数组
     * @param left    左边的索引
     * @param right   右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        int mid;
        if (left <= right) {
            mid = (left + right)/2;
            if (findVal < arr[mid]) {
                return binarySearch(arr, left, mid - 1, findVal);  //向左递归
            } else if (findVal > arr[mid]) {
                return binarySearch(arr, mid + 1, right, findVal);
            } else {
                return mid;
            }
        } else {
            return -1;
        }
    }

    //完成一个思考题：
    /**
     * 当一个有序数组中，存在多个相同的值，如何将所有的数值都找到，比如这里的1000
     * {1,8,10,89,1000,1000,1234}
     */

    /**
     * @param arr     数组
     * @param left    左边的索引
     * @param right   右边的索引
     * @param findVal 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回-1
     */
    public static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        int mid;
        if (left <= right) {
            mid = (left + right)/2;
            if (findVal < arr[mid]) {
                return binarySearch2(arr, left, mid - 1, findVal);  //向左递归
            } else if (findVal > arr[mid]) {
                return binarySearch2(arr, mid + 1, right, findVal);
            } else {
                int leftMid = mid-1;
                int rightMid = mid+1;
                ArrayList<Integer> resIndexList = new ArrayList<>();
                //向左扫描
                while ( leftMid >=0 && arr[leftMid] == findVal ){
                    resIndexList.add(leftMid);
                    leftMid--;
                }

                resIndexList.add(mid);
                //向右扫描
                while (rightMid <=arr.length-1 && arr[rightMid] == findVal  ){
                    resIndexList.add(rightMid) ;
                    rightMid++;
                }
                return resIndexList;

            }
        } else {
            ArrayList<Integer> resIndexList = new ArrayList<Integer>();
            resIndexList.add(-1);
            return  resIndexList;
        }
    }


}
