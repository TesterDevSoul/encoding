package top.testeru.str;

/**
 * @Author www.testeru.top
 * @Description 字符串比较
 * 内存地址比较  ==
 * 内容比较   equals()
 * @Date 2023/4/10 17:27
 */
public class Str2 {
    public static void main(String[] args) {

        //构造方法的方式得到对象
         char[] chs = {'a', 'b', 'c'};
         String s1 = new String(chs);
         String s2 = new String(chs);

        //直接赋值的方式得到对象
         String s3 = "abc";
         String s4 = "abc";

         //比较字符串对象地址是否相同
         System.out.println(s1 == s2);//false
         System.out.println(s1 == s3);//false
         System.out.println(s3 == s4);//true
         System.out.println("--------");

         //比较字符串内容是否相同
         System.out.println(s1.equals(s2));//true
         System.out.println(s1.equals(s3));//true
         System.out.println(s3.equals(s4));//true

    }
}
