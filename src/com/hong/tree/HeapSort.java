package com.hong.tree;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName: HeapSort
 * Package: com.hong.tree
 * Description:
 *  堆排序---使用顺序存储二叉树来实现
 *      1.在顺序存储二叉树中 最后一个非叶子节点的存储位置就是(arr.length/2) -1 而非叶子节点是连续的
 *        也就是说找到了最后一个非叶子节点的索引后，该索引的前面的索引就全是非叶子节点了
 *
 *  步骤
 *  1.先将整个数组排列成一个大顶堆
 *
 *  2.将根节点和末尾元素交换，最后将末尾索引去掉，再将整个数组排列成一个大顶堆，找到数组有序
 * @Author honghuaijie
 * @Create 2023/10/5 10:41
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HeapSort {
    public static void main(String[] args) {
        //要求将数组进行升序排列
        int[] arr = {8,2,6,4,9,3,1,5,7,10};

        //调用堆排序
        heapSort(arr);

    }

    //测试堆排序的速度
    @Test
    public void test1(){
        int arr[] = new int[8000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 8000000);
        }
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SS");
        System.out.println("排序前" + simpleDateFormat.format(date1));
        heapSort(arr);
        Date date2 = new Date();
        System.out.println("排序后" + simpleDateFormat.format(date2));
    }

    //编写一个堆排序的方法
    public static void heapSort(int[] arr){
        int temp;
//        System.out.println("堆排序！！");

        //将此无序数组变为大顶堆（从下往上，从右往左）
        for (int i = arr.length / 2  -1 ; i >=0; i--){
            adjustHeap(arr,i,arr.length);
        }
        //
//        System.out.println(Arrays.toString(arr));

        //2.将堆顶元素与末尾元素交换，将最大元素“沉”到数组末端
        //3. 重新调整结构，使其满足堆定义，然后继续交换堆顶元素和末尾元素，反复执行，直到程序结束
        for (int j = arr.length-1; j>0 ; j--){
            //堆顶元素与末尾元素交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            //重新调整结构，使最大数保存在堆顶
            adjustHeap(arr,0,j);
        }
//        System.out.println(Arrays.toString(arr));

    }

    //将一个数组（二叉树），调整成一个大顶堆

    /**
     * 功能： 完成将以i为非叶子结点的树调整成大顶堆
     * 举例int arr[] = {4,6,8,5,9} => i = 1 => adjustHeap => arr[] = {4,9,8,5,6}
     * 如果我们再次调用adjustHeap 传入的i = 0 => arr[] = {9,6,8,5,4}
     *
     *
     * @param arr  要调整的数组
     * @param i  表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整，length是在逐渐减少的
     */
    public static void adjustHeap(int[] arr,int i,int length){
        int temp = arr[i]; //用来保存 非叶子节点的

        for (int k = 2 * i + 1; k < length; k = 2 * k + 1){
            //先找出左右子节点中最大的那个节点
            // 在比较左右节点之前，需要先判断右节点是否存在k+1 < length
            if (k+1 < length && arr[k] < arr[k+1]){ //说明右节点大
                k++; //k指向右子节点
            }
            //判断当前节点和子节点中最大的那个节点，哪个大
            if (temp < arr[k] ){
                arr[i] = arr[k];
                i = k;
            }else{
                //为什么可以break，因为如果当前节点比子节点大就不需要进行操作了
                //因为子节点就算有节点也肯定是比子节点小的，因为我们是从下往上进行比较的
                break;
            }
        }

        //当for循环结束后，我们已经将以i为父节点的数的最大值放在了最顶部
        arr[i] = temp;
    }
}
