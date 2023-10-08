package com.hong.stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * ClassName: PolandNotation
 * Package: com.hong.stack
 * Description:
 *   逆波兰表达式
 *     3 4 + 5 * 6 -
 *     思路：从右向左一个一个取出数据
 *     如果遇到数字就直接放入栈中，
 *     如果遇到字符就从数字栈中取出两个数字，与之做运算，然后将结果放入数字栈中
 *
 * @Author honghuaijie
 * @Create 2023/9/17 18:03
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a true present
 */
public class PolandNotation {
    @Test
    public void test1() {
        //先定义一个逆波兰表达式
        //(3+4)×5-6 => 3 4 + 5 * 6 -;
        //为了方便，逆波兰表达式数字和符号使用空格隔开
        String syffixExpression = "3 4 + 5 * 6 -";
        Stack<Integer> numStack = new Stack<>();
        int index = 0; //索引
        int sum = 0; //保存两位数
        int num1 = 0;
        int num2 = 0;
        int res = 0;
        char ch = ' ';
        boolean flag = false; //判断是可以将数字放入栈中

        while (true){
            ch = syffixExpression.substring(index,index+1).charAt(0);
            //判断是否是一个符号
            if (isOper(ch)){
                num1 = numStack.pop();
                num2 = numStack.pop();
                res = cal(num1,num2,ch);
                numStack.push(res);
            }else if (ch >'0' && ch < '9'){ //判断是否是数字
                sum = sum *10 +(ch - 48);
                flag = true;
            }else{ //判断是否是空格
                if (flag){
                    numStack.push(sum);
                    sum = 0;
                    flag = false;
                }
            }

            index++;
            if (index >= syffixExpression.length()){
                break;
            }
        }

        System.out.printf("表达式%s = %d",syffixExpression,numStack.pop());


    }


    //判断是不是一个运算符
    public static boolean isOper(char val){
        return val == '+' || val == '/' || val == '*' || val == '-';
    }

    public static int cal(int num1,int num2,int oper){
        int res = 0; //res用于存放运算的结果

        switch (oper){
            case '+'-> res = num2 + num1;
            case '-'-> res = num2 - num1;
            case '*'-> res = num2 * num1;
            case '/'-> res = num2 / num1;
        }
        return res;
    }


    /*
    * 思路
    * 4 * 5 - 8 + 60 + 8/2 =>  4 5 * 8 - 60 + 8 2 / +
    * 1. 先将"3 4 + 5 * 6 -" 放入到一个Arraylist中
    * 2. 将ArrayList 传递给一个方法，遍历ArrayList配合栈完成计算
    * */
    @Test
    public void test2(){

        //将中缀表达式转换成后缀表达式
        /*
         * 思路
         * 1.创建两个栈，一个符号栈一个数字栈
         * 2.从字符串左边依次取出一个字符，那么就有两个情况
         * 3.情况一：如果是数字就直接入数字栈
         * 4.情况二：如果是符号那么有三种情况
         * 4.1 如果符号栈中没有符号，或者栈顶是( 那么就直接入栈
         * 4.2 如果当前符号优先级大于栈顶运算符，也将运算符压入栈
         * 4.3 如果当前符号优先级小于等于栈顶运算符，则将栈顶运算符取出，压入数字栈，
         *      再重新比较当前符号和栈顶符号的优先级
         * 5.如果遇到括号：
         * 5.1遇到（ 直接压入栈中
         * 5.2 遇到） 将符号栈的符号依次放入数字栈中 找到遇到（ 将（） 舍弃
         * 6.重复执行，直接表达式查找结束
         * 7.将符号栈中剩余的符号依次取出，放入数字栈
         * 8.依次弹出数字栈并输出，结果的逆序就是中缀表达式对应的后缀表达式
         * */

        //完成一个中缀表达式转换成后缀表达式的功能
        //1.将"1+((2+3)*4)-5" 转换成 中缀表达式
        String nifixExpression = "1+((2+3)*4)-5";
//        String nifixExpression = "(30+4)*5-6";
        ArrayList<String> nifixList = toInfixExpressionList(nifixExpression);
        System.out.println("中缀表达式：" + nifixList);
        //2.将中缀表达式转换成后缀表达式
        ArrayList<String> suffixExpressionList = nifixToSyffix(nifixList);
        System.out.println("后缀表达式：" + suffixExpressionList);

        //3.将后缀表达式求出来
        int res = calculate(suffixExpressionList);
        System.out.printf("表达式%s = %d\n",nifixExpression,res);

    }


    //返回运算符的优先级,优先级是程序员来确定的，优先级使用数字表示，数组越大，优先级越高
    public int priority(int oper){
        if (oper == '*' || oper == '/'){
            return 1;
        }else if(oper == '+' || oper == '-'){
            return 0;
        }else{
            return -1; //假定目前的表达式只有+-*/  4种运算符
        }
    }

    //完成中缀表达式转换成后缀表达式的操作

    /**
     *
     * @param nifixList 传入的是一个中缀表达式的列表
     * @return  后缀表达式的列表
     */
    public ArrayList<String> nifixToSyffix(ArrayList<String> nifixList){
        //定义两个栈
        Stack<String> operStack = new Stack<>(); //符号栈
        //由于numstack没有出栈操作，并且后续还需要反转，所有这里用列表
        ArrayList<String> numStack = new ArrayList<>(); // 数字栈

        //依次遍历nifixList()
        for (String ele: nifixList){
            //遇到(的情况
            if (ele.equals("(")){
                operStack.push(ele);
            }else if(ele.equals(")")) { //遇到“)”的情况
                while (true) {
                    String oper = operStack.pop();
                    //直到遇到“(”才结束
                    if (oper.equals("(")) {
                        break;
                    }
                    numStack.add(oper);
                }
            }else if (ele.matches("\\d+")){ //遇到数字的情况
                numStack.add(ele);
            }else{ //遇到是符号的情况
                //如果栈中的优先级比当前符号的优先级高，需要将栈中的符号放入数字栈中
                while (operStack.size() !=0 && priority(operStack.peek().charAt(0)) >= priority(ele.charAt(0))){
                    numStack.add(operStack.pop());
                }
                operStack.add(ele);
            }
        }

        //执行到这里列表已经扫描结束了，接下来只要将符号栈中剩余的符号依次取出，放入数字栈
        while (operStack.size() !=0){
            numStack.add(operStack.pop());
        }

        return numStack;  //注意因为是存放到list中，因此顺序输出就是对应的后缀表达式对应的list

    }

    //方法：将中缀表达式转成对应的List
    //s = "1+((2+3)*4)-5"
    public ArrayList<String> toInfixExpressionList(String s){
        ArrayList<String> ls = new ArrayList<>();
        //定义一个列表，用来存放中缀表达式的内容
        int i = 0;
        String str =""; //用来保存多位数
        char c = ' '; //用来存放遍历到的字符
        while (i < s.length()){
            //如果c是非数字，就直接放入列表中
            if ((c=s.charAt(i)) <48 || (c=s.charAt(i)) > 57){
                ls.add(String.valueOf(c));
                i++;
            }else{
                str = "";
                while (i < s.length() && (c=s.charAt(i)) >=48 && (c=s.charAt(i)) <= 57){
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        }
        return ls;
    }

    //1.传入一个表达式，将其分割，并依次放入列表中
    public ArrayList<String> getListString(String syffixExpression){
        String[] split = syffixExpression.trim().split(" ");
        ArrayList<String> list = new ArrayList<>();

        for (String ele: split){
            list.add(ele);
        }
        return list;
    }

    //2.完成对逆波兰表达式的运算
    public int calculate(ArrayList<String> list){
        Stack<Integer> numStack = new Stack<>();

        for (String ele: list){
            if (ele.matches("\\d+")){  //匹配的是多位数
                //入栈
                numStack.push(Integer.parseInt(ele));
            }else{ //如果不是一个数，就pop出两个数并运算，再入栈
                int num2 = numStack.pop();
                int num1 = numStack.pop();
                int res= 0;
                if (ele.equals("+")){
                    res = num1 + num2;
                }else if(ele.equals("-")){
                    res = num1 - num2;
                }else if(ele.equals("*")){
                    res = num1 * num2;
                }else if(ele.equals("/")){
                    res = num1 / num2;
                }else{
                    throw new RuntimeException("运算符有误");
                }
                numStack.push(res);
            }
        }

        return numStack.pop();

    }
}
