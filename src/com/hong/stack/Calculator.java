package com.hong.stack;


/**
 * ClassName: Calculator
 * Package: com.hong.stack
 * Description:
 * 计算一个表达式的结果：
 *      思路：
 *      1.创建两个栈，一个用来存放数字，一个用来存放符号
 *      2.使用index(索引)来一个一个的取出表达式中的字符，判断是数字还是符号
 *      3.如果是数字，就直接放入到数字栈中
 *      4.如果是字符，那么有两种情况
 *      4.1.如果是符号，并且符号栈中为空， 就直接放入符号栈
 *      4.2.如果是符号，并且符号栈中不为空，首先判断当前符号和栈顶的优先级
 *      如果当前操作符小于或等于栈顶的优先级，就pop出来两个数字，一个符号，并进行运算。
 *              结果放入数字栈中，然后将当前运算符入栈
 *      如果当前操作符大于栈顶的优先级，就直接放入符号栈
 *      5.当表达式扫描完毕，就顺序的取出数字栈和符号栈中的数据，依次运算
 *      6.当数字栈中只剩下一个数字，那么该数字就为表达式的结果
 *
 * @Author honghuaijie
 * @Create 2023/9/17 15:20
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a true present
 */
public class Calculator {
    public static void main(String[] args) {
        String expression = "7*2*2-5+1-5+3-4";
        //创建两个栈，一个数字栈，一个符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        //定义需要的相关变量
        int index = 0; // 索引
        int sum = 0; //用来处理两位数
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' '; //将每次扫描得到的char放入ch中
        while (true){
            //依次得到expression的每一个字符
            ch = expression.substring(index,index+1).charAt(0);
            //判断ch是什么然后做相应的处理//是符号的情况
            if (operStack.isOper(ch)){
                //先将sum放入数字栈中
                numStack.push(sum);
                sum = 0;
                if (operStack.isEmpty()){ //判断符号栈是否为空，如果为空直接入栈
                    operStack.push(ch);
                }else{ //符号栈不为空，需要比较运算符优先级
                    int curpro = operStack.priority(ch);//当前运算符的优先级
                    int toppro = operStack.priority(operStack.getTopValue());//栈顶运算符的优先级
                    //比较优先级
                    if (curpro <= toppro){
                        //如果当前操作符小于或等于栈顶的优先级，就pop出来两个数字，一个符号，并进行运算。
                        //    结果放入数字栈中，然后将当前运算符入栈
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1,num2,oper);
                        numStack.push(res); //结果放入数字栈
                        operStack.push(ch);// 当前符号放入符号栈
                    }else{ //如果当前符号优先级比栈顶大，就直接放入符号栈
                        operStack.push(ch);
                    }
                }
            }else{
                sum = sum *10 + (ch-48);
            }

            //如果这个字符是最后一位了，就直接将sum入栈
            if (index == expression.length() -1){
                //将最后一个数字放入数字栈中
                numStack.push(sum);
            }


            index++;
            if (index >=expression.length()){
                break;
            }
        }



        //顺序的取出数字栈和符号栈的数据，依次运算
        while (true){
            if (operStack.isEmpty()){
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1,num2,oper);

            numStack.push(res); //结果放入数字栈
        }
        //数字栈只有一个数字，该数字就为表达式的结果
        res = numStack.getTopValue();
        System.out.printf("表达式%s = %d\n",expression,res);
    }
}

//创建一个栈。直接使用前面创建好的
//ArrayStack2表示一个栈，需要扩展一些功能
class ArrayStack2{
    private int maxSize; //栈的大小
    private int[] stack; //数组模拟栈，数据都存放入数组中
    private int top = -1; //指向的是栈顶, 默认为-1

    public ArrayStack2(int maxSize){
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //获取栈顶元素
    public int getTopValue(){
        return stack[top];
    }

    //判断栈是否已满
    public boolean isFull(){
        return top == maxSize-1;
    }

    //判断栈是否为空
    public boolean isEmpty(){
        return top == -1;
    }
    //入栈
    public void push(int value){
        //首先判断栈是否已满，满了就不能添加了
        if (isFull()){
            System.out.println("栈已满，无法添加");
            return;
        }
        top++;
        stack[top] = value;
    }
    //出栈:将栈顶的数据返回，并将top--
    public int pop(){
        //首先判断栈是否为空
        if (isEmpty()){
            throw  new RuntimeException("栈为空，无法出栈");
        }

        int value = stack[top];
        top--;
        return value;

    }

    //遍历栈的情况[遍历栈],
    public void list(){
        if (isEmpty()){
            System.out.println("栈空， 没有数据");
            return;
        }
        for (int i = top; i>=0; i--){
            System.out.printf("stack[%d]= %d\n",i,stack[i]);
        }
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

    //判断是不是一个运算符
    public boolean isOper(char val){
        return val == '+' || val == '/' || val == '*' || val == '-';
    }

    //计算
    /**
     *
     * @param num1 先pop出来的数
     * @param num2 后pop出来的数
     * @param oper 运算符
     * @return 运算的结果
     */
    public int cal(int num1,int num2,int oper){
        int res = 0; //res用于存放运算的结果

        switch (oper){
            case '+'-> res = num2 + num1;
            case '-'-> res = num2 - num1;
            case '*'-> res = num2 * num1;
            case '/'-> res = num2 / num1;
        }
        return res;
    }

}
