package top.testeru.system.stu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author www.testeru.top
 * @Description 学生管理系统
 * @Date 2023/4/13 15:04
 */
public class StudentManagerPro {

    public static void main(String[] args) {
        //创建集合对象，用于保存学生数据信息
        List<Student> students = new ArrayList<>();
        // 初始化学生列表
        students.add(new Student("1001","Alice", 20, 85.0));
        students.add(new Student("1002","Bob", 22, 90.0));
        students.add(new Student("1003","Charlie", 18, 78.5));
        students.add(new Student("1004","Dave", 21, 88.5));
        students.add(new Student("1005","Eve", 19, 91.0));

        SManagerMethod sManagerMethod = new SManagerMethod(students);


        //用循环完成再次回到主界面
         while (true) {
             //用输出语句完成主界面的编写
             System.out.println("--------欢迎来到学生管理系统--------");
             System.out.println("1 添加学生信息");
             System.out.println("2 查看学生信息");
             System.out.println("3 查看年龄小于20的学生姓名");
             System.out.println("4 修改学生信息");
             System.out.println("5 学生毕业");
             System.out.println("6 退出");
             System.out.println("请输入你的选择：");
             //用Scanner实现键盘录入数据
             Scanner sc = new Scanner(System.in);
             String line = sc.nextLine();
             //用switch语句完成操作的选择
             switch (line) {
                 case "1":
                     System.out.println("添加学生功能实现");
                     sManagerMethod.addStudent();
                     break;
                 case "2":
                     System.out.println("查看所有学生功能实现");
                     sManagerMethod.findAllStudent();
                     break;
                 case "3":
                     System.out.println("查看年龄小于20的学生姓名");
                     // 使用 Stream 进行学生数据处理
                     List<String> names = students
                             .stream() // 创建 Stream
                             .filter(s -> s.getAge() < 20) // 过滤年龄小于 20 的学生
                             .sorted((s1, s2) -> Double.compare(s2.getScore(), s1.getScore())) // 按分数降序排序
                             .map(Student::getName) // 提取学生姓名
                             .collect(Collectors.toList()); // 转换为 List
                     System.out.println(names);
                     break;
                 case "4":
                     System.out.println("修改学生功能实现");
                     sManagerMethod.updateStudent();
                     break;
                 case "5":
                     System.out.println("删除学生功能实现");
                     sManagerMethod.deleteStudent();
                     break;

                 case "6":
                     System.out.println("退出功能实现");
                     System.out.println("谢谢使用");
                     System.exit(0); //JVM退出
                     break;
             }
         }
    }
}