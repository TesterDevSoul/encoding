package top.testeru.system.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author www.testeru.top
 * @Description 回合制游戏
 * @Date 2023/4/13 16:24
 */
public class HeroManagerPro {
    public static void main(String[] args) {
        //创建集合对象，用于保存英雄数据信息
        //List<Hero> heros = new ArrayList<Hero>();
        // 初始化英雄列表
        //heros.add(new Hero("1001","辅助",300,10));
        //heros.add(new Hero("1002","肉盾",500,30));

        List<Hero> heros = new ArrayList<>(){{
            add(new Hero("1001","小兵",30,1));
            add(new Hero("1002","辅助",300,10));
            add(new Hero("1003","肉盾",500,30));
        }};

        //用循环完成再次回到主界面
        while (true) {
            //用输出语句完成主界面的编写
            System.out.println("--------欢迎来到回合制游戏--------");
            System.out.println("1 创建英雄");
            System.out.println("2 查看英雄信息");
            System.out.println("3 查看血量小于100的英雄名");
            System.out.println("4 打斗");
            System.out.println("5 修改英雄信息");
            System.out.println("6 删除英雄");
            System.out.println("7 退出系统");
            System.out.println("请输入你的选择：");

            //用Scanner实现键盘录入数据
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();

            //用switch语句完成操作的选择
            switch (line) {
                case "1":
                    System.out.println("创建英雄功能实现");
                    addHero(heros);
                    break;
                case "2":
                    System.out.println("查看英雄信息功能实现");
                    findAllHero(heros);
                    break;
                case "3":
                    System.out.println("查看血量小于100的英雄名");
                    findHpLessthan100(heros);
                    break;
                case "4":
                    System.out.println("打斗");
                    fight(heros);
                    break;
                case "5":
                    System.out.println("修改英雄信息功能实现");
                    updateHero(heros);
                    break;
                case "6":
                    System.out.println("删除英雄功能实现");
                    deleteHero(heros);
                    break;
                case "7":
                    System.out.println("退出功能实现");
                    System.out.println("谢谢使用");
                    System.exit(0); //JVM退出
                    break;
            }
        }

    }
    private static void findHpLessthan100(List<Hero> heros) {
        // 使用 Stream 进行英雄数据处理
        List<String> names = heros
                .stream() // 创建 Stream
                .filter(hero -> hero.getHp() < 100) // 过滤血量小于100 的英雄
                .sorted((h1, h2) -> Integer.compare(h2.getPower(), h1.getPower())) // 按攻击力降序排序
                .map(Hero::getName) // 提取英雄姓名
                .collect(Collectors.toList());// 转换为 List
        System.out.println(names);
    }
    //打斗方法
    public static String fight(List<Hero> heroes){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入我方出战英雄的编号：");
        String mId = sc.nextLine();

        System.out.println("请输入敌方出战英雄的编号：");
        String enemyId = sc.nextLine();

        //根据ID获取我方英雄对象
        Hero myHero = heroes
                .stream() // 创建 Stream
                .filter(hero -> hero.getId().equals(mId))
                .findFirst()
                .get();
        //根据ID获取敌方英雄对象
        Hero enemyHero = heroes
                .stream() // 创建 Stream
                .filter(hero -> hero.getId().equals(enemyId))
                .findFirst()
                .get();

        System.out.println("开始KO！");

        String result = "";
        //打斗了多少回合
        int i = 0;
        //打斗不是一次结束，是来回打好几轮 while循环
        while(true){
            i++;
            //英雄打斗后自己的血量 = 自己的血量 - 敌人的攻击力
            int myHp = myHero.getHp() - enemyHero.getPower();
            myHero.setHp(myHp);
            //敌人打斗后的血量 = 敌人的血量 - 自己的攻击力
            int enemyFinalHp = enemyHero.getHp() - myHero.getPower();
            enemyHero.setHp(enemyFinalHp);

            System.out.println("我" + myHero.getName() + "的血量为：" + myHero.getHp());
            System.out.println("敌人" + enemyHero.getName() + "的血量为：" + enemyHero.getHp());


            //什么时候比赛结束？英雄、敌人双方血量谁先为0谁就输
            if(enemyHero.getHp() <= 0 | myHero.getHp() <= 0){
                break;
            }
        }
        if(myHero.getHp() > enemyHero.getHp()){
            //我的血量多
            result = myHero.getName().concat( "赢了");
        } else if (myHero.getHp() < enemyHero.getHp()) {
            //我的血量少
            result = enemyHero.getName().concat( "赢了");
        } else {
            result = myHero.getName() + "与" + enemyHero.getName() + "平局";
        }
        System.out.println("最终打斗了" + i + "回合，结果为：" + result);
        return result;
    }
    public static void addHero(List<Hero> heros){
        //键盘录入学生对象所需要的数据,显示提示信息，提示要输入何种信息
        Scanner sc = new Scanner(System.in);
        String id;
        while (true) {
            System.out.println("请输入英雄编号：");
            id = sc.nextLine();
            boolean flag = isUsed(heros, id);
            if (flag) {
                System.out.println("你输入的编号已经被占用，请重新输入");
            } else {
                break;
            }
        }
        System.out.println("请输入英雄姓名：");
        String name = sc.nextLine();
        System.out.println("请输入英雄血量：");
        int hp = sc.nextInt();
        System.out.println("请输入英雄攻击力：");
        int power = sc.nextInt();

        //创建英雄对象，把键盘录入的数据赋值给英雄对象的成员变量
        Hero h = new Hero(id,name,hp,power);
        //将英雄对象添加到集合中
        heros.add(h);
        //给出添加成功提示
        System.out.println("添加英雄成功");
    }
    //定义一个方法，判断编号是否被使用
    private static boolean isUsed(List<Hero> array, String id) {
        //如果与集合中的某一个英雄编号相同，返回true;如果都不相同，返回false
        boolean flag = array
                .stream() // 创建 Stream
                .map(Hero::getId) // 提取英雄id
                .anyMatch(id::equals);
        return flag;
    }
    //定义一个方法，用于查看英雄信息
    public static void findAllHero(List<Hero> heros) {
        String fileName = "hero.txt";
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件删除成功.");
            } else {
                System.out.println("文件删除失败.");
            }
        } else {
            System.out.println("文件不存在.");
        }


        //显示表头信息
        // \t其实是一个tab键的位置
        String tab = "编号\t\t姓名\t\t\t血量\t\t攻击力\n";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            System.out.print(tab);
            bufferedWriter.write(tab);
            heros
                .stream()
                .forEach(
                    hero ->{
                        String format = String.format(
                                "%s\t%s\t\t\t%s\t\t%s\n",
                                hero.getId(),
                                hero.getName(),
                                hero.getHp(),
                                hero.getPower()
                        );
                        try {
                            bufferedWriter.write(format);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.print(format);
                    }
                );
            bufferedWriter.close();

            System.out.println("写入文件.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //定义一个方法，用于修改英雄信息
    public static void updateHero(List<Hero> heros) {
        //键盘录入要修改的英雄编号，显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要修改的英雄的编号：");
        String id = sc.nextLine();
        //键盘录入要修改的英雄信息
        System.out.println("请输入英雄姓名：");
        String name = sc.nextLine();
        System.out.println("请输入英雄血量：");
        int hp = sc.nextInt();
        System.out.println("请输入英雄攻击力：");
        int power = sc.nextInt();

        heros
                .stream()
                .forEach(hero -> {
                    if(hero.getId().equals(id)){
                        hero.setId(id);
                        hero.setName(name);
                        hero.setHp(hp);
                        hero.setPower(power);
                    }
                });
        //给出修改成功提示
        System.out.println("修改英雄成功");
    }
    //定义一个方法，用于删除英雄信息
    public static void deleteHero(List<Hero> heros) {
        //键盘录入要删除的英雄编号,显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你要删除的英雄的编号：");
        String id = sc.nextLine();
        //在删除/修改英雄操作前，对学号是否存在进行判断
        //如果不存在，显示提示信息
        //如果存在，执行删除/修改操作
        int index = -1;
        for (int i = 0; i < heros.size(); i++) {
            Hero h = heros.get(i);
            if (h.getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("该信息不存在，请重新输入");
        } else {
            heros.remove(index);
            //给出删除成功提示
            System.out.println("删除英雄成功");
        }
    }
}
