# String
## 本章要点
1. String 概述【理解】
1. String特点【理解】
1. String 构造方法【掌握】
1. String 创建的区别【理解】
1. 字符串的比较【掌握】


### String 概述


String 类声明的变量代表字符串。

>Java 中的所有字符串文字「例如"abc"」都被实现为此类的实例。也就是说，Java 程序中所有的双引号字符串，都是 String 类的对象。

String 类在 java.lang 包下，所以使用的时候不需要导包。


### String特点

1. 字符串不可变，它们的值在创建后不能被更改。
2. 字符串可以被共享。
   >虽然 String 的值是不可变的，但是它们可以被共享。
3. 字符串**效果**上相当于**字符数组**(char[])，但是**底层**原理是**字节数组**( byte[] )。

### String 构造方法
String可以根据下面构造方法来进行字符串的创建。

|方法名|说明|
|---|---|
|public String()|创建一个空白字符串对象，不含有任何内容|
|public String(char[] chs)|根据字符数组的内容，来创建字符串对象|
|public String(byte[] bys)|根据字节数组的内容，来创建字符串对象|
|String s = “abc”;| 直接赋值的方式创建字符串对象，内容就是abc|


#### 示例代码

```java
public class Str1 {
    public static void main(String[] args) {
        //public String()：创建一个空白字符串对象，不含有任何内容
         String s1 = new String();
         System.out.println("s1:" + s1);//s1:

        // public String(char[] chs)：根据字符数组的内容，来创建字符串对象
         char[] chs = {'a', 'b', 'c'};
         String s2 = new String(chs);
         System.out.println("s2:" + s2);//s2:abc

        // public String(byte[] bys)：根据字节数组的内容，来创建字符串对象
         byte[] bys = {97, 98, 99};
         String s3 = new String(bys);
         System.out.println("s3:" + s3);//s3:abc

        // String s = “abc”; 直接赋值的方式创建字符串对象，内容就是abc
         String s4 = "abc";
         System.out.println("s4:" + s4);//s4:abc
    }
}
```

### String 创建的区别

#### 通过构造方法创建

通过 new 创建的字符串对象，每一次 new 都会申请一个内存空间，虽然内容相同，但是地址值不同。

```java
char[] chs = {'a', 'b', 'c'};
String s1 = new String(chs);
String s2 = new String(chs);
```

上面的代码中，JVM会首先创建一个字符数组，然后每一次new的时候都会有一个新的地址，只不过s1和s2参考的字符串内容是相同的。

**字符串内容相同，内存地址不同。**

#### 直接赋值方式创建

以""方式给出的字符串，只要字符序列相同(顺序和大小写)，无论在程序代码中出现几次，JVM 都只会建立一个 String 对象，并在字符串池中维护。

```java
String s3 = "abc";
String s4 = "abc";
```

第一行代码，JVM 会建立一个 String 对象放在字符串池中，并给 s3 参考；<br>第二行则让 s4 直接参考字符串池中的 String 对象，也就是说它们本质上是同一个对象。

**字符串内容相同，内存地址也相同。**


### 字符串的比较

字符串有两种比较方式：一种是直接使用双等号==进行比较，一种是使用String的equals方法进行比较。

#### ==

==双等号如果比较的是基本数据类型，则比较的是当前具体的值。

==双等号如果比较的是引用数据类型，则比较的是当前对象的地址值。

#### equals()

比较两个字符串**内容是否相同**、**区分大小写**。

```java
public boolean equals(String s) 
```


#### 示例代码

```java
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
```

### 应用
#### 用户登录案例
##### 案例需求

已知用户名和密码，请用程序实现模拟用户登录。


总共给三次机会，登录之后，给出相应的提示。


##### 思路

1. 已知用户名和密码。
   >定义两个字符串表示即可。

2. 键盘录入要登录的用户名和密码。
   >用 Scanner 实现。

3. 拿键盘录入的用户名、密码和已知的用户名、密码进行比较，给出相应的提示。
   
   如果登录失败提示：**登录失败，你还有几次机会**。
   
   最后一次登录失败提示：**你的账户被锁定，请与管理员联系**。
   
   如果登录成功提示：**登录成功**。

   >字符串的内容比较，用 equals() 方法实现。

4. 用循环实现3次机会，这里的次数明确。
   >采用for循环实现，并在登录成功的时候，使用break结束循环。


##### 代码实现
```java
public class UserLogin {
    public static void main(String[] args) {
        //已知用户名和密码，定义两个字符串表示即可
         String username = "Lily";
         String password = "goodluck123";
        // 用循环实现多次机会，这里的次数明确，采用for循环实现，并在登录成功的时候，使用break结束循环
         for(int i=0; i<3; i++) {
            // 键盘录入要登录的用户名和密码，用 Scanner 实现
             Scanner sc = new Scanner(System.in);
             System.out.println("请输入用户名：");
             String name = sc.nextLine();
             System.out.println("请输入密码：");
             String pwd = sc.nextLine();
            // 拿键盘录入的用户名、密码和已知的用户名、密码进行比较，
           // 给出相应的提示。字符串的内容比较， 用equals() 方法实现
             if (name.equals(username) && pwd.equals(password)) {
                 System.out.println("登录成功"); break;
             } else {
                 if(2-i == 0) {
                     System.out.println("你的账户被锁定，请与管理员联系");
                 } else {
                    //2,1,0
                    // i,0,1,2
                    System.out.println("登录失败，你还有" + (2 - i) + "次机会");
                 }
             }
        }
    }
}
```

#### 遍历字符串案例

##### 案例需求

键盘录入一个字符串，使用程序实现在控制台遍历该字符串。


##### 思路

1. 键盘录入一个字符串，用 Scanner 实现 
2. 遍历字符串，首先要能够获取到字符串中的每一个字符 
public char charAt(int index)：返回指定索引处的char值，字符串的索引也是从0开始的 
3. 遍历字符串，其次要能够获取到字符串的长度 
public int length()：返回此字符串的长度 数组的长度：数组名.length 字符串的长度：字符串对象.length() 
4. 遍历字符串的通用格式

##### 代码实现

## 总结
- 总结一
- 总结二
- 总结三
https://github.com/Wechat-ggGitHub/Awesome-GitHub-Repo

[项目演示地址](https://github.com/testeru-pro/junit5-demo/tree/main/junit5-basic)


# 学习反馈

1. SpringBoot项目的父工程为( )。

   - [x] A. `spring-boot-starter-parent`
   - [ ] B.`spring-boot-starter-web`
   - [ ] C. `spring-boot-starter-father`
   - [ ] D. `spring-boot-starter-super`
