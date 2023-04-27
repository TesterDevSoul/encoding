package top.testeru.system.stu;

import top.testeru.system.stu.Student;

import java.util.List;
import java.util.Scanner;

/**
 * @Author www.testeru.top
 * @Description 提取
 * @Date 2023/4/14 16:00
 */
public class SManagerMethod {
    List<Student> students;

    public SManagerMethod(List<Student> students) {
        this.students = students;
    }

    //定义一个方法，用于添加学生信息
    public void addStudent() {
        //键盘录入学生对象所需要的数据,显示提示信息，提示要输入何种信息
        Scanner sc = new Scanner(System.in);
        String sid;
        while (true) {
            System.out.println("请输入学生学号：");
            sid = sc.nextLine();
            boolean flag = isUsed(students, sid);
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
        students.add(s);
        //给出添加成功提示
        System.out.println("添加学生成功");
    }

    //定义一个方法，判断学号是否被使用
    private boolean isUsed(List<Student> array, String sid) {
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
    public void findAllStudent() {
        //显示表头信息
        // \t其实是一个tab键的位置
        System.out.println("学号\t\t姓名\t\t\t\t年龄\t\t\t成绩");
        //将集合中数据取出按照对应格式显示学生信息，年龄显示补充“岁”
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
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
    public void updateStudent() {
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
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getSid().equals(sid)) {
                students.set(i, s);
            }
        }
        //给出修改成功提示
        System.out.println("修改学生成功");
    }

    //定义一个方法，用于删除学生信息
    public void deleteStudent() {
        //键盘录入要删除的学生学号,显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要删除的学生的学号：");
        String sid = sc.nextLine();
        //在删除/修改学生操作前，对学号是否存在进行判断
        //如果不存在，显示提示信息
        //如果存在，执行删除/修改操作
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getSid().equals(sid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("该信息不存在，请重新输入");
        } else {
            students.remove(index);
            //给出删除成功提示
            System.out.println("删除学生成功");
        }
    }
}
