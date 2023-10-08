package come.hong2.huffmanCode;

import java.util.*;

/**
 * ClassName: HuffmanCode
 * Package: come.hong2.huffmanCode
 * Description:
 *   复习哈夫曼编码
 *   需求：传入一个字符串，要求返回一个压缩后的哈夫曼编码
 *   1. 首先将字符串转换成字节数组，然后写一个方法将字节数组中的元素以及元素出现的次数转换成节点
 *      放入集合中
 *   2. 传入一个集合，将其转换成哈夫曼树
 *   3. 传入一个哈夫曼树的根节点，将其哈夫曼树的所有叶子节点按照策略生成一个哈夫曼编码，放入huffmanMap中
 *   4.传入原始的字节数据和哈夫曼编码表，按照哈夫曼编码表将原始字节中的元素编码，生成一个字符串
 *      并将字符串按照每8位一组转换成10进制，放入huffmanCodeByte中
 * @Author honghuaijie
 * @Create 2023/10/8 9:52
 * @Version 1.0
 * Yesterday is history,tomorrow is a mystery,
 * but today is a gift.That is why it's called the present
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] huffmanCodeByte = huffmanZip(content);
        System.out.println(Arrays.toString(huffmanCodeByte));
        System.out.println(huffmanCodeByte.length);
    }

    public static byte[] huffmanZip(String content){
        //1.将数组中的元素进行整理放入集合中
        byte[] contentByte = content.getBytes();
        System.out.println(Arrays.toString(contentByte));
        List<Node> nodes = getNodes(contentByte);

        //2.将集合转换成哈夫曼树
        Node root = createHuffmanTree(nodes);
        root.preOrder();


        //3.为每一个叶子结点编码
        Map<Byte, String> huffmanMapCode = huffmanCode(root);
        System.out.println(huffmanMapCode);

        //4.将原始字节数组转换成哈夫曼编码
        byte[] huffmanCodeByte = zip(contentByte, huffmanMapCode);
        return huffmanCodeByte;


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
        String codes = "";

        for (byte data : contentByte){
            codes += huffmanCodeMaps.get(data);
        }
        System.out.println(codes);
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
