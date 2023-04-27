package top.testeru.system.stu;

/**
 * @Author www.testeru.top
 * @Description 学生实体类
 * @Date 2023/4/13 15:02
 */

public class Student {
    // 学生id
    private String sid;
    // 学生姓名
    private String name;
    // 学生年龄
    private int age;
    // 学生成绩
    private double score;

    //getter、setter、toString、constructor


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Student() {
    }

    public Student(String sid, String name, int age, double score) {
        this.sid = sid;
        this.name = name;
        this.age = age;
        this.score = score;
    }
}
