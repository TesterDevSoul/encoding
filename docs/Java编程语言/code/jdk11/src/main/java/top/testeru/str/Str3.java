package top.testeru.str;

/**
 * @Author www.testeru.top
 * @Description 字符串拼接
 * 方式一："+"
 * 方式二：concat()
 * @Date 2023/4/10 17:27
 */
public class Str3 {
    public static void main(String[] args) {
        

        //构造方法的方式得到对象
         char[] chs = {'a', 'b', 'c'};
         String s1 = new String(chs);
         String s2 = new String(chs);

        //直接赋值的方式得到对象
         String s3 = "abc";
         String s4 = "abc";

         //比较字符串对象地址是否相同
         System.out.println(s1 == s2);
         System.out.println(s1 == s3);
         System.out.println(s3 == s4);
         System.out.println("--------");

         //比较字符串内容是否相同
         System.out.println(s1.equals(s2));
         System.out.println(s1.equals(s3));
         System.out.println(s3.equals(s4));
        //1. 字符串创建
        String str1 = "Hello";
        String str2 = "Java";
        //2. 字符串拼接："+" 、concat()
        String result = str1 + " " + str2;
        System.out.println(result);//Hello Java
        String result1 = str1.concat(str2);
        System.out.println(result1);//HelloJava
    }
}
