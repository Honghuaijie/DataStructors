package com.hong.sparseArray;

import java.io.*;

/**
 * ClassName: SparesArray
 * Package: com.hong.sparseArray
 * Description:
 *      稀疏数组
 * @Author honghuaijie
 * @Create 2023/9/11 9:29
 * @Version 1.0
 * 不积跬步无以至千里
 */
public class SparesArray {
    public static void main(String[] args) {
        //首先将棋盘转换为二维数组
        //0表示没有棋子，1表示黑子、2表示蓝子
        int[][] cheesArr1 = new int[11][11];
        cheesArr1[1][2]=1;
        cheesArr1[2][3]=2;
        cheesArr1[4][5]=2;

        //将二维数组转换为稀疏数组
        //1.遍历二维数组，根据数组中的非0值，创建稀疏数组
        int sum = 0;
        for (int[] row : cheesArr1){
            for (int data: row){
                if(data !=0) {
                    sum += 1;
                }
            }
        }
        //2.创建对应的稀疏数组
        int[][] sparseArr1 = new int[sum+1][3];
        //3.给稀疏数组赋值
        //稀疏数组的第一行用来记录二维数组的行高和列高，还有实际值的个数
        sparseArr1[0][0]=11;
        sparseArr1[0][1]=11;
        sparseArr1[0][2]=sum;
        int count = 1; //用来记录第几个非0数据
        //遍历二维数组，将非0值放到sparseArr1数组中
        for (int i = 0; i < cheesArr1.length;i++){
            for (int j = 0; j < cheesArr1[i].length ; j++){
                if(cheesArr1[i][j] !=0){
                    sparseArr1[count][0] = i;
                    sparseArr1[count][1] = j;
                    sparseArr1[count++][2] = cheesArr1[i][j];
                }
            }
        }
        System.out.println("稀疏1数组：");
        for (int[] sparserow : sparseArr1){
            for (int data : sparserow){
                System.out.print(data + "\t");
            }
            System.out.println();
        }

        //将稀疏数组存入到当前项目下map.data
        try (
                FileWriter fw = new FileWriter("map.data");
        ){
            fw.write("row" + "\t" + "low" + "\t" +"value" + "\n");
            for (int[] row3: sparseArr1){
                fw.write(row3[0] + "\t" + row3[1] + "\t" + row3[2] + "\n" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从文件map.data下去取出疏数组放入sparseArr2中
        System.out.println("取出稀疏数组");
        int sparserow = 0;//用来记录稀疏数组的行数
        //1.首先遍历文件，计算出稀疏数组的行数
        try (
                FileReader fr = new FileReader("map.data");
                BufferedReader br = new BufferedReader(fr);
        ){
            String data;
            data = br.readLine(); //过滤第一行标题
            while ((data = br.readLine()) !=null){
                sparserow++;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("行数为：" + sparserow);
        //2.根据行数创建稀疏数组
        int[][] sparseArr2 = new int[sparserow][3];
        int sparseCount = 0; //记录稀释数组已经赋值到第几行了
        //3.遍历文件，将数据放入数组中
        try (
                FileReader fr = new FileReader("map.data");
                BufferedReader br = new BufferedReader(fr);
        ){
            String data;
            data = br.readLine(); //过滤第一行标题
            while ((data = br.readLine()) !=null){
                String[] split = data.split("\t");
                sparseArr2[sparseCount][0] = Integer.parseInt(split[0]);
                sparseArr2[sparseCount][1] = Integer.parseInt(split[1]);
                sparseArr2[sparseCount++][2] = Integer.parseInt(split[2]);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("取出的稀疏数组：");
        for (int i = 0; i < sparseArr2.length; i++) {
            for (int i1 = 0; i1 < sparseArr2[i].length; i1++) {
                System.out.print(sparseArr2[i][i1] +"\t");
            }
            System.out.println();
        }


        //将稀疏数组转换成二维数组
        //1.先根据稀疏数组的第一行数据，创建原始的二维数组
        int row = sparseArr2[0][0];
        int low = sparseArr2[0][1];
        int[][] chessArray2 = new int[row][low];
        //2.读取稀疏数组后几行的数据，并赋给原始的二维数组
        for (int i = 1; i < sparseArr2.length; i++) {
            chessArray2[sparseArr2[i][0]][sparseArr2[i][1]] = sparseArr2[i][2];
        }

        System.out.println("稀疏数组转换成二维数组：");
        for (int[] row2 : chessArray2){
            for (int data2 : row2){
                System.out.print(data2 + "\t");
            }
            System.out.println();
        }

    }
}
