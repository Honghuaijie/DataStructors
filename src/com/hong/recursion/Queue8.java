package com.hong.recursion;

/**reverepolishcal
 * ClassName: Queue8
 * Package: com.hong.recursion
 * Description:
 *      解决8皇后问题：在8*8的棋盘上，存在8个皇后，每个皇后之间不能相互攻击
 *      即：每个皇后在每行每列每个斜线上不能遇到其他皇后
 * @Author honghuaijie
 * @Create 2023/9/23 21:52
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a true present
 */
public class Queue8 {
    int max = 8 ; //定义有多少个皇后
    //保存皇后放置位置的结果：比如arr={0,4,7,5,2,6,1,3}
    //需要注意的是第一个皇后就存放在第一行也就是下标0，即下标就代码他们存放在哪个行上
    int[] array = new int[max]; //存放的是每个皇后存放在哪个列上
    int num = 0;
    int checkNum = 0;
    public static void main(String[] args) {
        Queue8 q8 = new Queue8();
        q8.check(0);
        System.out.println(q8.num);
        System.out.println(q8.checkNum);
    }
    //放置第n个皇后
    //特别注意：check是每一次递归时，进入到check中都有for (int i = 0; i < max; i++)循环
    //因此会有回溯
    public  void check(int n){
        if (n == max) { //由于n是从0开始的，如果n=8了，说明已经放完8个皇后了，第9个就不用放了
            print();
            num++;
            return;
        }

        //由于n已经控制行了，所以i是控制列的
        for (int i = 0; i < max; i++) {
            //将第n个皇后放在第i列上
            array[n] = i;
            //判断第n个皇后的位置是否与前面n-1个皇后的位置冲突
            // 如果不冲突就接着放第n+1个皇后
            // 如果冲突就继续放下一个列上
            if (judge(n)){
                check(n+1);
                checkNum++;
            }
        }
    }

    //查看当我们摆放完第n个皇后时，是否与前面n-1个皇后冲突
    //也就是判断第n个皇后所在的位置的行、列、斜线上有没有其他皇后
    // 如何判断写斜线上是否有其他元素 abs(行-行) == abs(列-列)
    /**
     *
     * @param n 表示传入第n+1个皇后  n从0开始，最大为7
     * @return 当n与前面n-1个皇后不冲突时，返回true
     */
    private boolean judge(int n ){
        for (int i = 0; i < n; i++) {
            //1、array[n] == array[i]  判断是否列冲突
            //2、Math.abs(n - i) ==Math.abs(array[n] - array[i]) 判断是否斜线上冲突
            //3、没有必要判断是否在同一行，因为n是不断增加的
            if (array[n] == array[i] || Math.abs(n - i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    //输出一下皇后摆放的位置
    private void print(){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }




}
