package come.hong2.tree;

import java.util.Arrays;

/**
 * ClassName: HeapSort
 * Package: come.hong2.tree
 * Description:
 *  复习下堆排序
 *  堆排序的前提，需要知道什么是大顶堆（树中的每个节点上的数都要比他们的子节点大）
 *  步骤：
 *  1.将一组无序数组转换成大顶堆
 *  2.将堆顶元素和末尾元素进行交换
 *  3.将剩余的n-1个元素转换成大顶堆
 *  4.重复2,3两个步骤，直到数组有序
 *
 * @Author honghuaijie
 * @Create 2023/10/6 10:02
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HeapSort {

    public static void main(String[] args) {
        //要求将数组进行升序排列
        int[] arr = {8,2,6,4,9,3,1,5,7,10,-11};

        //调用堆排序
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    //将传入的无序数组进行堆排序
    public static void heapSort(int[] arr ){
        //1. 将一组无序数组转换成大顶堆
        //  从下而上的进行转换，也就是从最后一个叶子节点开始转换
        for (int i = arr.length / 2 -1; i >= 0; i--) {
            adjustHeap(arr,i,arr.length);
        }

        int temp;
        for (int i = arr.length-1; i >0 ; i--) {

             //2.将堆顶元素和末尾元素进行交换
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
             //3.将剩余的n-1个元素转换成大顶堆
            adjustHeap(arr,0,i);
        }


    }

    //将传入的以非叶子节点i为根节点的子树转换成大顶堆
    public static void adjustHeap(int[] arr,int i, int length){
        int temp = arr[i];


        for (int j = 2 * i + 1; j <length; j = 2 * j + 1) {
            //找出左右子节点哪个大
            if (j+1 < length &&  arr[j] < arr[j+1]){  //右节点大
                j++;
            }

            if (temp < arr[j]){
                arr[i] = arr[j]; //将大的元素朝上移动
                i = j;
            }else{
                break; //如果当前节点大于两个子节点，那么就说明大顶堆已经完成了
            }
        }
        arr[i] = temp;
    }
}
