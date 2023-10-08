package com.hong.recursion;

/**
 * ClassName: MiGong
 * Package: com.hong.recursion
 * Description:
 *
 * @Author honghuaijie
 * @Create 2023/9/20 16:12
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a true present
 */
public class MiGong {
    public static void main(String[] args) {
        //先创建一个二维数组，模拟地图
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        //左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[3][1] = 1;
        map[3][2] = 1;

        map[2][3] = 1;
        map[3][3] = 1;


        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        setWay(map,1,1);

        System.out.println("小球走过，并标记过的地图");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    //说明
    //1.map 表示地图
    //2.i,j 表示从地图的哪个位置开始出发（1,1）
    //3. 如果小球能到map[6][5]位置，则说明通路找到
    //4. 约定：当map[i][j] 为0 表示该点没有走过，当为1时表示墙；2表示通路可以走；
    //  3表示该店已经走过，但走不通
    //5.在走迷宫时，需要确定一个策略（方法）下->右->上->左，如果该点走不通，再回溯

    /**
     *
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j
     * @return 如果找到通路，就返回true，如果没有就返回false
     */
    public static boolean setWay(int[][] map, int i , int j){
        if (map[6][5] == 2){ //说明已经找到路了
            return true;
        }else{
            if (map[i][j] == 0){
                // 策略下->右->上->左，
                map[i][j] = 2; //假设当前路可以走
                if (setWay(map,i+1,j)){ //向下走
                    return true;
                }else if (setWay(map,i,j+1)){//向右走
                    return true;
                }else if (setWay(map,i-1,j)) {  //向上走
                    return true;
                }else if (setWay(map,i,j-1)){ //向左走
                    return true;
                }else{  //下右上左都走不通，就说明是死路
                    map[i][j] = 3;
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    //修改策略，改成上->右->下->左
    public static boolean setWay2(int[][] map, int i , int j){
        if (map[6][5] == 2){ //说明已经找到路了
            return true;
        }else{
            if (map[i][j] == 0){
                // 策略上->右->下->左，
                map[i][j] = 2; //假设当前路可以走
                if (setWay2(map,i-1,j)){ //向上走
                    return true;
                }else if (setWay2(map,i,j+1)){//向右走
                    return true;
                }else if (setWay2(map,i+1,j)) {  //向下走
                    return true;
                }else if (setWay2(map,i,j-1)){ //向左走
                    return true;
                }else{  //下右上左都走不通，就说明是死路
                    map[i][j] = 3;
                    return false;
                }
            }else{
                return false;
            }
        }
    }

}
