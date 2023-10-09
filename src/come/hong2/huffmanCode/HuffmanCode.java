package come.hong2.huffmanCode;

import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * ClassName: HuffmanCode
 * Package: come.hong2.huffmanCode
 * Description:
 *   复习哈夫曼编码
 *   编码：
 *   需求：传入一个字符串，要求返回一个压缩后的哈夫曼编码
 *   1. 首先将字符串转换成字节数组，然后写一个方法将字节数组中的元素以及元素出现的次数转换成节点
 *      放入集合中
 *   2. 传入一个集合，将其转换成哈夫曼树
 *   3. 传入一个哈夫曼树的根节点，将其哈夫曼树的所有叶子节点按照策略生成一个哈夫曼编码，放入huffmanMap中
 *   4.传入原始的字节数据和哈夫曼编码表，按照哈夫曼编码表将原始字节中的元素编码，生成一个字符串
 *      并将字符串按照每8位一组转换成10进制，放入huffmanCodeByte中
 *   解码：将编码后的哈夫曼字节数组转换为原来的初始数组
 *   1.先将编码后的哈夫曼字节数组【-88,....】转换为二进制的字符串10111.
 *   2.按照赫夫曼编码表将此字符串转换为原始的字节数组
 *
 *   文件压缩
 *
 *
 *   文件解压
 * @Author honghuaijie
 * @Create 2023/10/8 9:52
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HuffmanCode {
    public static void main(String[] args) {
//        String content = "i like like like java do you like a java";
//        byte[] huffmanCodeByte = huffmanZip(content);
//        System.out.println(Arrays.toString(huffmanCodeByte));
//        System.out.println(huffmanCodeByte.length);
    }

    //编码
    @Test
    public void test1(){
        String content = "i like like like java do you like a java";
        byte[] contentByte = content.getBytes();
        //编码后的字节数组
        byte[] huffmanCodeByte = huffmanZip(contentByte);
//        System.out.println(Arrays.toString(huffmanCodeByte));
//        System.out.println(huffmanCodeByte.length);
//        System.out.println(huffmanCodeMaps);

        //解码
//        Map<Byte,String> huffmanCodes = new HashMap<>(huffmanCodeMaps);
//        byte[] oriByte = unCode(huffmanCodeByte, huffmanCodes);
//        String s = new String(oriByte);
    }

    //文件压缩测试
    @Test
    public void test2(){
        String srcFile = "F://src.bmp";
        String dstFile = "F://dst1.zip";
        zipFile(srcFile,dstFile);
        System.out.println("压缩成功！");
    }

    //文件解压测试
    @Test
    public void test3(){
        String srcFile = "F://dst1.zip";
        String dstFile = "F://src3.bmp";
        unZipFile(srcFile,dstFile);
        System.out.println("解压成功！");
    }



    //文件压缩

    /**
     *
     * @param srcFile  要压缩的文件路径
     * @param dstFile  文件压缩后存放的位置
     */
    public static void zipFile(String srcFile,String dstFile){
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = new FileInputStream(srcFile);
            byte[] contentByte = new byte[is.available()];
            //1.使用字节流将源文件的内容读到byte[]中
            is.read(contentByte); //将文件中的内容以字节的形式放入contentByte中
            //2.将byte[]进行编码
            //2.1获取到编码后的字节数组
            byte[] huffmanBytes = huffmanZip(contentByte);
            //2.2获取到其对应的哈夫曼编码表
            Map<Byte,String> huffmanCodes = huffmanCodeMaps;

            //3.将编码后的字节数组和其对应哈夫曼编码表，以对象流的形式放入到dstFile中
            os = new FileOutputStream(dstFile);
            oos = new ObjectOutputStream(os);

            //传入编码后的字节数组
            oos.writeObject(huffmanBytes);
            //传入对应的哈夫曼编码表(非常重要，如果没有这个哈夫曼编码表，就无法进行解压)
            oos.writeObject(huffmanCodes);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                is.close();
                oos.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //文件的解压

    /**
     * 思路：
     * 1. 使用对象流从srcFile中压缩后的字节数组和哈夫曼编码表取出
     * 2. 利用字节数组和哈夫曼编码表进行解码
     * 3. 将解码后的字节数组输出到dstFile中
     *
     * @param srcFile 要解压的文件的路径
     * @param dstFile 解压后文件的存放路径
     */
    public static void unZipFile(String srcFile,String dstFile){
        FileInputStream is = null;
        ObjectInputStream ois = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            ois = new ObjectInputStream(is);
            //1. 使用对象流从srcFile中压缩后的字节数组和哈夫曼编码表取出
            //取出字节数组
            byte[] huffmanBytes =(byte[]) ois.readObject();
            //取出哈夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            //2.解码
            byte[] bytes = unCode(huffmanBytes,huffmanCodes);

            //将byte输出到dstFile中
             os = new FileOutputStream(dstFile);
             os.write(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ois.close();
                os.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //传入一个字节数组，将其按照赫夫曼编码
    public static byte[] huffmanZip(byte[] contentByte){
        //1.将数组中的元素进行整理放入集合中
        List<Node> nodes = getNodes(contentByte);
        //2.将集合转换成哈夫曼树
        Node root = createHuffmanTree(nodes);


        //3.为每一个叶子结点编码
        Map<Byte, String> huffmanMapCode = huffmanCode(root);
        //4.将原始字节数组转换成哈夫曼编码
        byte[] huffmanCodeByte = zip(contentByte, huffmanMapCode);

        return huffmanCodeByte;


    }


    //解码：

    /**
     *
     * @param huffmanCodeByte 编码后的哈夫曼字节数组
     * @param huffmanCodes   哈夫曼编码表
     * @return   返回一个原始的字节数组
     */
    public static byte[] unCode(byte[] huffmanCodeByte , Map<Byte,String> huffmanCodes){
        //1.将huffmanCodeByte数组，转换为二进制字符串
        StringBuffer strb = new StringBuffer();
        Boolean flag ;
        for (int i = 0; i < huffmanCodeByte.length; i++) {
            byte b = huffmanCodeByte[i];
            //只有当当前元素是数组的最后一个元素，并且该元素还是正数，才不用补零
            flag = !(i == huffmanCodeByte.length -1 && b >=0);
            strb.append(byteToString(b,flag));

        }


        //2.按照哈夫曼编码表，转换为原始字节数组
        //2.1 反转哈夫曼编码，方便使用编码找byte
        Map<String,Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> entry : huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
//        //2.2 依次截取字符串，将字符串还原成原始的字节数组
        //由于并不知道byte数组有多少个长度，所以先用一个集合，临时存放byte
        List<Byte> list = new ArrayList<>();
        int i = 0; //用来遍历strb，并且决定从哪个位置开始截取
        int j = 1; //决定从i位置上取j个元素
        while (i+j <= strb.length()){
            String subString = strb.substring(i, i+j);  //获取编码
            Byte b = map.get(subString); //查看该编码对应的value
            if (b !=null){  //说明当前编码在编码表中
                //将当前byte 放入集合中
                list.add(b);
                i +=j;
                j = 1;
            }else{
                j++;
            }
        }

        byte[] bytes = new byte[list.size()];
        for (int k = 0; k < bytes.length; k++) {
            bytes[k] = list.get(k);
        }
        return bytes;
    }


    //写一个方法，将传入的byte转换为2进制字符串

    /**
     *
     * @param b   要转换的byte
     * @param flag  决定是否需要补零
     * @return  返回一个转换二进制字符串
     */
    private static String byteToString(byte b,boolean flag){
        int temp = b;

        if (flag){
            temp |= 256; //高位补零
        }
        String str = Integer.toBinaryString(temp);
        if (flag){ //判断是否需要截取后8位
            return str.substring(str.length()-8);
        }else{
            return str;
        }
    }


    //1.传入一个字节数组，统计其元素出现的位置，并放入集合中（以节点的方式)
    private static List<Node>  getNodes(byte[] contentByte){
        //1.使用map来记录byte中元素的个数
        Map<Byte,Integer> counts = new HashMap<>();
        for (byte data : contentByte){
            Integer count = counts.get(data);
            if (count ==null){ //说明此元素还没开始计数
                counts.put(data,1);
            }else{
                counts.put(data,count+1);
            }
        }


        //2.遍历map中的元素，组成Node节点，放入集合中
        List<Node> nodes = new ArrayList<>();
        for (Map.Entry<Byte,Integer> entry : counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }

        return nodes;
    }

    //2.传入一个集合，构造一个哈夫曼树，并返回该数的根节点
    private static Node createHuffmanTree(List<Node> nodes){

        while (nodes.size()>1){
            //1.首先对集合进行排序
            Collections.sort(nodes);
            //2.取出最小值和次小值
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            //3.根据这两个值计算出他们的父节点
            Node parent = new Node(left.getWeight() + right.getWeight());
            //4.将这两个值赋给父节点的左右节点
            parent.setLeft(left);
            parent.setRight(right);
            //5.删除这两个值，并把父节点添加到集合中
            nodes.remove(left);
            nodes.remove(right);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    private static Map<Byte,String> huffmanCodeMaps = new HashMap<>();
    //3.根据哈夫曼树 构造出一个哈夫曼编码表
    private static Map<Byte,String> huffmanCode(Node root){
        if (root != null){
            //根节点没有编码
            getCode(root,"");
        }else{
            System.out.println("该树为空");
        }

        return huffmanCodeMaps;
    }
    //3.1 传入一个节点，将这个节点以及节点的编码放入到map中
    // 编码的策略就是左为0，右为1
    /**
     *
     * @param node 当前节点
     * @param code 当前节点的编码
     */
    private static void getCode(Node node,String code){
        if (node !=null){
            //判断当前节点是否是叶子节点
            if (node.getData() !=null){
                //当前节点是叶子节点，需要进行编码
                huffmanCodeMaps.put(node.getData(),code);
            }
            //左递归
            getCode(node.getLeft(),code + "0");
            //右递归
            getCode(node.getRight(),code + "1");
        }
    }

    //传入一个原始字节数组，返回一个编码后的数组
    private static byte[] zip(byte[] contentByte,Map<Byte,String> huffmanCodeMaps ){
        //1.将原始字节数组按照赫夫曼编码表，生成一个字符串编码
        StringBuffer codes = new StringBuffer(); //由于下面需要不断的进行拼接所以使用StringBuffer 使用String的话会很慢
        for (byte data : contentByte){
            codes.append(huffmanCodeMaps.get(data));
        }
        //2.将字符串编码，按照每8位为一组，转换成10进制放入huffmanCodeByte数组中
        //2.1按照字符串的长度，计算出huffmanCodeByte的数组长度
        int len ;
        if (codes.length() % 8 == 0){
            len = codes.length() / 8;
        }else{
            len = codes.length() / 8 + 1;
        }


        byte[] huffmanCodeByte = new byte[len];
        int index = 0;
        for (int i = 0; i < codes.length(); i += 8) {
            if (i +8 < codes.length()){
                huffmanCodeByte[index++] = (byte)Integer.parseInt(codes.substring(i,i+8),2);
            }else{
                huffmanCodeByte[index++] = (byte)Integer.parseInt(codes.substring(i),2);
            }
        }

        return huffmanCodeByte;


    }
}

class Node implements Comparable<Node>{
    private Byte data;
    private Integer weight;
    private Node left;
    private Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    public Node( int weight) {
        this.weight = weight;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);

        if (this.left !=null){
            this.left.preOrder();
        }
        if (this.right !=null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }




    public Byte getData() {
        return data;
    }

    public void setData(Byte data) {
        this.data = data;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
}
