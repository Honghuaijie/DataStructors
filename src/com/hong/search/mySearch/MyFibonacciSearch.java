package com.hong.search.mySearch;

import java.util.Arrays;

/**
 * ClassName: MyFibonacciSearch
 * Package: com.hong.search.mySearch
 * Description:
 *      斐波那契查找
 *      其实也是一种二分查找，只不过是将mid看做是一个黄金分割点
 *      那么如何才能找到黄金分割点呢，就需要借助斐波那契数列
 *      f[k] = f[k-1] + f[k-2] 将f[k-1]这个数列的最后一个数看做是黄金分割点
 *
 * @Author honghuaijie
 * @Create 2023/10/1 10:12
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class MyFibonacciSearch {
    public static int maxSize = 20;
    public static void main(String[] args) {
        int[] arr = {1,8, 10, 89, 1000, 1234};
        System.out.println(fibSearch(arr, 1234));
    }

    //返回一个斐波那契数列
    public static int[] fib(){
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;

        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f;
    }


    public static int fibSearch(int[] arr, int key){
        int left = 0; //数组的开始位置
        int right = arr.length -1 ;// 数组的结束位置
        int k = 0; //记录数组长度为哪一个斐波那契数
        int[] f = fib(); //获取斐波那契数列
        int mid ; //黄金分割点
        //扩容arr，将arr的长度，扩容成仅大于当前数组长度的斐波那契数
        while (arr.length > f[k]){
            k++;
        }

        //通过Arrays工具类，扩容arr
        int[] temp = Arrays.copyOf(arr,f[k]);

        //将temp末尾为0的元素，修改成arr的末尾元素
        for (int i = right + 1; i <temp.length; i++) {
            temp[i] = arr[right];
        }

        //开始循环找黄金分割点，直接在数组中找到key值
        while (left <= right){
            //计算出黄金分割点
            //说明，我们将数组分成两部分，一部分的长度为f[k-1]-1 一部分的长度为f[k-2]-1
            //我们将f[k-1]-1这一部分的最后一个元素的位置，看做是黄金分割点
            //这里为什么要加上left，因为f[k-1]-1只是相对位置，需要加上lefy才是具体位置
            mid = left +  f[k-1] -1;

            if (key < temp[mid]){//说明key在第一部分，而第一部分的长度为f[k-1]-1 所以k要-1
                right = mid -1;
                k -=1;
            }else if(key > temp[mid]){ //说明key在第二部分，而第二部分的长度为f[k-2]-1 所以k要-2
                left = mid + 1;
                k -=2;
            }else{
                //因为mid很有可能找到扩容的位置，而扩容的位置上的元素都是right位置上的元素
                //所以直接输出right的坐标就可以了
                if (mid <= right){
                    return mid;
                }else{
                    return right;
                }
            }
        }
        return -1;
    }



}
