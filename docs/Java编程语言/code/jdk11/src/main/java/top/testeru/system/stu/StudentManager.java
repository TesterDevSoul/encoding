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
public class StudentManager {
    public static void main(String[] args) {
        //创建集合对象，用于保存学生数据信息
        List<Student> students = new ArrayList<Student>();
        // 初始化学生列表
        students.add(new Student("1001","Alice", 20, 85.0));
        students.add(new Student("1002","Bob", 22, 90.0));
        students.add(new Student("1003","Charlie", 18, 78.5));
        students.add(new Student("1004","Dave", 21, 88.5));
        students.add(new Student("1005","Eve", 19, 91.0));


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
                     addStudent(students);
                     break;
                 case "2":
                     System.out.println("查看所有学生功能实现");
                     findAllStudent(students);
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
                     updateStudent(students);
                     break;
                 case "5":
                     System.out.println("删除学生功能实现");
                     deleteStudent(students);
                     break;

                 case "6":
                     System.out.println("退出功能实现");
                     System.out.println("谢谢使用");
                     System.exit(0); //JVM退出
                     break;
             }
         }
    }



    //定义一个方法，用于添加学生信息
    public static void addStudent(List<Student> array) {
        //键盘录入学生对象所需要的数据,显示提示信息，提示要输入何种信息
        Scanner sc = new Scanner(System.in);
        String sid;
        while (true) {
            System.out.println("请输入学生学号：");
            sid = sc.nextLine();
            boolean flag = isUsed(array, sid);
            if (flag) {
                System.out.println("你输入的学号已经被占用，请重新输入");
            } else {
                break;
            }
        }
        System.out.println("请输入学生姓名：");
        String name = sc.nextLine();
        System.out.println("请输入学生年龄：");
        int age = sc.nextInt();
        System.out.println("请输入学生成绩：");
        double score = sc.nextDouble();

        //创建学生对象，把键盘录入的数据赋值给学生对象的成员变量
        Student s = new Student();
        s.setSid(sid);
        s.setName(name);
        s.setAge(age);
        s.setScore(score);
        //将学生对象添加到集合中
        array.add(s);
        //给出添加成功提示
        System.out.println("添加学生成功");
     }

    //定义一个方法，判断学号是否被使用
    private static boolean isUsed(List<Student> array, String sid) {
        //如果与集合中的某一个学生学号相同，返回true;如果都不相同，返回false
         boolean flag = false;
        for (int i = 0; i < array.size(); i++) {
            Student s = array.get(i);
            if (s.getSid().equals(sid)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //定义一个方法，用于查看学生信息
    public static void findAllStudent(List<Student> array) {
        //显示表头信息
        // \t其实是一个tab键的位置
        System.out.println("学号\t\t姓名\t\t\t\t年龄\t\t\t成绩");
        //将集合中数据取出按照对应格式显示学生信息，年龄显示补充“岁”
        for (int i = 0; i < array.size(); i++) {
            Student s = array.get(i);
            String name = s.getName();
            int nameLengh = name.length();
            if(nameLengh < 10){
                for (int j = 1; j < 10-nameLengh; j++) {
                    name += " ";
                }

            }
            System.out.println(s.getSid() + "\t" + name + "\t\t" + s.getAge() + "岁\t\t" + s.getScore());
        }
    }

    //定义一个方法，用于修改学生信息
    public static void updateStudent(List<Student> array) {
        //键盘录入要修改的学生学号，显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要修改的学生的学号：");
        String sid = sc.nextLine();
        //键盘录入要修改的学生信息
        System.out.println("请输入学生新姓名：");
        String name = sc.nextLine();
        System.out.println("请输入学生新年龄：");
        int age = sc.nextInt();
        System.out.println("请输入学生成绩：");
        double score = sc.nextDouble();
        //创建学生对象
        Student s = new Student();
        s.setSid(sid);
        s.setName(name);
        s.setAge(age);
        s.setScore(score);
        //遍历集合修改对应的学生信息
        for (int i = 0; i < array.size(); i++) {
            Student student = array.get(i);
            if (student.getSid().equals(sid)) {
                array.set(i, s);
            }
        }
        //给出修改成功提示
        System.out.println("修改学生成功");
    }

    //定义一个方法，用于删除学生信息
    public static void deleteStudent(List<Student> array) {
        //键盘录入要删除的学生学号,显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要删除的学生的学号：");
        String sid = sc.nextLine();
        //在删除/修改学生操作前，对学号是否存在进行判断
        //如果不存在，显示提示信息
        //如果存在，执行删除/修改操作
        int index = -1;
        for (int i = 0; i < array.size(); i++) {
            Student s = array.get(i);
            if (s.getSid().equals(sid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("该信息不存在，请重新输入");
        } else {
            array.remove(index);
            //给出删除成功提示
            System.out.println("删除学生成功");
        }
    }
}