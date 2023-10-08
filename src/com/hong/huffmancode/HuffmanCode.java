package com.hong.huffmancode;

import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * ClassName: HuffmanCode
 * Package: com.hong.huffmancode
 * Description:
 * 哈夫曼编码   利用哈夫曼树将一段字符串进行编码
 * //压缩
 * 1.传入字符串
 * 2.将字符以节点的方式传入集合中
 * 3.将指定集合转换成哈夫曼树
 * 4.使用一个map记录哈夫曼树中的叶子结点，和他们的路径，向右的路径为1，向左的路径为0，生成一个哈夫曼编码
 * 5.遍历字符串，根据每个字符的编码，连接所有字符对应的编码,并将获取到的编码每8位为一组放入到huffmanCodeByte中
 *
 *  //解压： 将压缩后的字节数组[-88,...]，转换为原来的字节数组[105....]
 *  1.将压缩后的字节数组还原成字符串的形式
 *  2.按照赫夫曼编码表，将字符串中的字符依次转换为字节形式
 *
 *
 *
 * @Author honghuaijie
 * @Create 2023/10/7 10:31
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        //原始数组
        byte[] contentByte = content.getBytes();
        //压缩后的数组
        byte[] huffmanCodeByte = huffmanZip(contentByte);
//        System.out.println(Arrays.toString(huffmanCodeByte));

//        System.out.println(bytetoBitString((byte) -28));
        byte[] decode = decode(mapNodes, huffmanCodeByte);

        //解压后的数组
        String decodeContent = new String(decode);
//        System.out.println(decodeContent);
//        System.out.println(decodeContent.equals(content));

    }


    @Test
    public void test1(){
        //测试压缩文件
        String srcFile = "F://src.bmp";
        String dstFile = "F://dst.zip";
        zipFile(srcFile,dstFile);
        System.out.println("压缩文件OK");
    }

    @Test
    public void test2(){
        //测试解压文件
        String srcFile = "F://src2.bmp";
        String dstFile = "F://dst.zip";
        unZipFile(dstFile,srcFile);
        System.out.println("解压文件");
    }
    //编写一个方法，对一个文件进行压缩
    /**
     *
     * 思路：
     * 1.将源文件使用字节流的方式读出，放入byte数组中
     * 2.将此byte数组，进行哈夫曼编码压缩，得到哈夫曼编码字节数组和哈夫曼编码表
     * 3.将哈夫曼编码字节数组和哈夫曼编码表以对象流输出到文件中（为了方便解压）
     * @param srcFile 需要压缩的文件的全路径
     * @param dstFile 压缩后将压缩文件放到哪个目录下
     */
    public static void zipFile(String srcFile,String dstFile){
        FileInputStream is = null;
        FileOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = new FileInputStream(srcFile);
            //is.available是得到该流中剩余字节的长度，由于我们没有对流进行读取，所以返回的就是流中所有字节的长度
            byte[] b = new byte[is.available()];
            //1.将流中的数据全部读取到b数组中
            is.read(b);
            is.close();
            //2.直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //这里使用对象输入流，可以方便我们后期获取压缩后的字节数组和哈夫曼编码表
            os = new FileOutputStream(dstFile);
            oos = new ObjectOutputStream(os);
            //3.把哈夫曼编码后的字节数组写入到压缩文件中
            oos.writeObject(huffmanBytes);
            //注意，一定要将哈夫曼编码表放入压缩文件中，因为你没有表，就无法解压
            //因为mapNodes是静态的无法进行序列化，所以需要使用map来中转下
            Map<Byte,String> map = new HashMap<>(mapNodes);
            oos.writeObject(map);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            try {
                oos.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    //编写一个方法，来对文件进行解压

    /**
     * 思路：
     * 1.首先将dstFile文件中压缩后的字节数组取出，再取出该字节数组对应的哈夫曼表
     * 2.利用取出后的字节数组和哈夫曼表进行decode解码
     * 3.将解码后返回的字节数组，利用文件输出流，输出到srcFile文件中
     * @param dstFile  压缩文件的路径
     * @param srcFile   解压后放入的文件要放入的路径
     */
    public static void unZipFile(String dstFile,String srcFile){
        FileInputStream is = null;
        ObjectInputStream ois = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(dstFile);
            ois = new ObjectInputStream(is);
            //将decode输入到srcFile文件中
            os = new FileOutputStream(srcFile);

            //获取压缩后的哈夫曼字节数组
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //获取该压缩后的哈夫曼字节数组对应的哈夫曼编码表
            Map<Byte,String> huffmanCodes  = (Map<Byte, String>) ois.readObject();
            //对该字节数组进行解码
            byte[] decode = decode(huffmanCodes, huffmanBytes);

            os.write(decode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try {
                ois.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try {
                os.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Map<Byte, String> mapNodes;
    /**
     * 压缩字符串
     *
     * @param contentByte 原始的字节数组
     * @return 返回一个压缩后的huffmanCodeByte数组
     */
    //综合： 传入一个原始的字符串，返回一个压缩后的huffmanCodeByte数组
    private static byte[] huffmanZip(byte[] contentByte) {
        //1.获取原始的字节数组转换成的集合
        List<Node> nodes = getNodes(contentByte);
        //2.获取哈夫曼树的根节点
        Node root = createHuffmanTree(nodes);
        //3.获取哈夫曼编码表(根据哈夫曼树来创建）
         mapNodes = huffmanCode(root);
        //4.根据生成的哈夫曼编码表，压缩得到压缩后的哈夫曼编码字节数组
        byte[] huffmanCodeByte = zip(contentByte, mapNodes);
        return huffmanCodeByte;
    }


    //对压缩后的数组进行解码
    /*
     *  思路：
     *   1.首先将压缩后的数组haffumanCodeByte 转换成二进制字符串
     *   2.根据二进制字符串重新转换 成原来的字符串(对照哈夫曼编码表)
     * */

    //编写一个方法，完成对压缩数据的解码
    /**
     *
     * @param huffmanCodes 哈夫曼编码表 map
     * @param huffmanCodeByte 原始字节数组通过哈夫曼编码表压缩后的字节数组
     * @return  原始的字节数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanCodeByte){
        //1.将huffmanCodeByte 转换成对应的二进制串1010100010111111110...
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < huffmanCodeByte.length; i++){
            byte b = huffmanCodeByte[i];
            boolean flag = (i == huffmanCodeByte.length-1 && b >=0); //只有最后一个元素是整数的情况下才不用补零
            //注意，核心步骤就是将一个字节转换为二进制
            stringBuffer.append(byteToBitString(!flag,b));
        }

        //2.将字符串按照指定的赫夫曼编码进行解码

        //2.1把哈夫曼编码表进行调换，因为反向查询，根据编码找到对应的byte
        //举例 97->100   转换成100 ->97
        Map<String,Byte> map = new HashMap<>();
        //这里一定 要遍历传入的huffmanCodes，不能使用该类中的static属性的哪个mapNodes
        for (Map.Entry<Byte,String> entry: huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
        //如何计算出decodeByte的长度 创建一个集合，用来存放byte，最后集合的长度就是byte数组的长度
        List<Byte> list = new ArrayList<>();
        int i = 0; //用来遍历stringBuffer，并且决定了他是从哪个位置开始截取字符
        int j = 1; //用来决定每次从i这个位置取几个字符
        //2.2遍历字符串，找到编码对应的byte
        while (i+j <= stringBuffer.length()){
            String key = stringBuffer.substring(i, i + j);
            if (map.get(key) !=null){  //说明这个编码在编码表里面
                list.add(map.get(key));
                i +=j;
                j = 0;
            }else{
                j++;  //i不动，让count移动
            }
        }
        //将list中的数据放入到byte[]中
        byte[] b = new byte[list.size()];
        for (int k = 0; k < b.length; k++) {
            b[k] = list.get(k);
        }
        return b;
    }

    /**
     * 将byte转成一个二进制的字符串
     * 该方法中有几个难点
     * 1.我们使用的是Integer.toBinaryString去得到该字节的二进制字符，
     * 而Integer返回的是32位二进制，而我们需要的是8位，因此我们只需要取后8位就行了
     * 而正数转换为2进制可能不足8位，那么就需要高位补零了
     * 2.如果数组的最后一个是正数，那么就不需要补零
     * @param flag 判断是否需要补高位，如果是true表示需要补高位
     * @param b  表示一个byte数
     * @return 是该b对应的二进制的字符串（注意是按补码返回的）
     */
    private static String byteToBitString(boolean flag,byte b) {
        int temp = b; //b

        //如果是正数，我们存在补高位的情况
        //原理就是256作为2进制是9位，最高为是1,后面8位是0，所以不会改变temp化为2进制后，后8位的值
        //同时，如果是负数，那么temp的值不会进行改变
        //举例 -88 和 1
        //    10101000     00000001
        // | 100000000  | 100000001
        //   110101000    100000001   此时，只取后8位，并没有改变原始值的大小
        //总结：如果我们想对一个8位二进制（肯定是对正数补零）进行补零操作，就可以使用1 0000 0000 对这个二进制进行逻辑或的操作，
        //                                  然后只取后8位就可以了

        if (flag){
            temp |= 256; //如果存在正数，那么就补位
        }


        String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制补码

        if (flag){
            return str.substring(str.length() - 8);
        }else{ //如果没有补高位。也就是说该数是数组的最后一个元素
            return str;
        }
    }



    //压缩的方法：
    //1.传入一个整型数组，要求将数组中的每一个数据转换成节点，并将其放入到集合中

    /**
     * @param bytes 一个字节数组
     * @return 返回一个集合 形式：[Node[data=32,weight = 9],[data=100,weight = 1]...]
     */
    public static List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();

        //利用map，来记录数组中每一个元素出现的次数
        Map<Byte, Integer> maps = new HashMap<Byte, Integer>();
        for (byte value : bytes) {
            Integer count = maps.get(value);
            if (count == null) { //map还没有这个字符数据，第一次
                maps.put(value, 1);
            } else {
                maps.put(value, count + 1);
            }
        }
        for (Map.Entry<Byte, Integer> entry : maps.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }


    //2.通过一个list，创建 一个哈夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);

            Node parent = new Node(left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);

            nodes.add(parent);

            nodes.remove(left);
            nodes.remove(right);
        }

        return nodes.get(0);
    }


    //3.通过创建的哈夫曼树，生成其所有叶子结点的编码，并将其放入map中
    public static Map<Byte, String> huffmanCode(Node root) {
        Map<Byte, String> nodes = new HashMap<>();
        if (root != null) {
            getCode(nodes, root, "");
        } else {
            System.out.println("该二叉树为空");
        }

        return nodes;
    }


    //3.1生成所有叶子结点编码的核心步骤

    /**
     * @param nodes 存放所有叶子节点，以及他们的编号
     * @param node  节点
     * @param code  路径，左节点是0 ，右节点是1
     */
    public static void getCode(Map<Byte, String> nodes, Node node, String code) {
        if (node != null) {  //如果当前节点为空，就不进行处理
            if (node.getData() != null) {  //data不为空，说明当前节点是叶子结点
                nodes.put(node.getData(), code);
            } else { //当前节点为非叶子节点
                //左递归
                if (node.getLeft() != null) {
                    getCode(nodes, node.getLeft(), code + "0");
                }
                //右递归
                if (node.getRight() != null) {
                    getCode(nodes, node.getRight(), code + "1");
                }
            }
        }
    }


    /**
     * @param bytes        这是原始字符串对应的byte数组
     * @param huffmanCodes 生成的哈夫曼编码map
     * @return 返回哈夫曼编码处理后的byte数组
     * 举例：String content = "i like like like java do you like a java";
     * 将content转换成byte数组，井将byte数组中的每一个数据于，哈夫曼编码一一对应，生成一段String codes
     * 并将codes每8位进行转换，放入huffmanCodeByte数组中，最后将huffmanCodeByte数组返回
     * <p>
     * 而由于huffmanCodeByte是byte类型，byte只有8位，因此数组中会出现负数
     */
    public static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //1.将原始数组按照哈夫曼编码表，转换成哈夫曼编码对应的字符串
        StringBuffer codes = new StringBuffer();
        for (byte key : bytes) {
            codes.append(huffmanCodes.get(key));
        }

        //2.将生成的字符串编码codes，每8位为一组，转换成byte类型，放入huffmanCodeByte中
        //2.1计算huffmanCodeByte的长度
        int len;
        if (codes.length() % 8 == 0) {
            len = codes.length() / 8;
        } else {
            len = codes.length() / 8 + 1;
        }
        //2.2 创建存储压缩后的byte数组，用于返回
        byte[] huffmanCodeByte = new byte[len];

        int i = 0;//用来遍历压缩后的编码
        int index = 0;//用来保存huffmanCodeByte的当前索引
        //2.3每8位为一组，转换成byte类型，放入huffmanCodeByte中
        while (i < codes.length()) {
            if (i + 8 < codes.length()) {
                huffmanCodeByte[index++] = (byte) Integer.parseInt(codes.substring(i, i + 8), 2);
            } else { //说明取到最后了，而最后剩余的位数小于8,将codes中剩余的全部取出来
                huffmanCodeByte[index++] = (byte) Integer.parseInt(codes.substring(i), 2);
            }
            i += 8;
        }
        return huffmanCodeByte;
    }


}


class Node implements Comparable<Node> {
    private Byte data; //存放数据本身，比如'a' = 97;
    private Integer weight; //权值，存放字符出现的次数
    private Node left;
    private Node right;

    public Node(Byte data, Integer weight) {
        this.data = data;
        this.weight = weight;
    }

    public Node(Integer weight) {
        this.weight = weight;
    }

    /**
     * @param nodes 将每个字符的编码，保存在key和value中
     * @param code  编码
     */
    //方法的作用就是判断本节点是否是叶子节点，如果是就将其字符和编号放入nodes中
    public void huffmanCode(Map<Byte, String> nodes, String code) {
        //处理当前节点
        //先判断当前节点是否是叶子结点（data不为null）
        if (this.data != null) { //说明当前节点是叶子节点，将其编码保存在nodes中
            nodes.put(this.data, code);
        }
        //左子节点是0，右子节点是1
        //处理左子树
        if (this.left != null) {
            //如果左子树不为空，就提前将做子节点的路径传入
            this.left.huffmanCode(nodes, code + "0");
        }
        //处理右子树
        if (this.right != null) {
            this.right.huffmanCode(nodes, code + "1");
        }


    }


    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
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
