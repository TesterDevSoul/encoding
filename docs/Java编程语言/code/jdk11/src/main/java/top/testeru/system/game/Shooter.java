//package top.testeru.system.game;
//
///**
// * @Author www.testeru.top
// * @Description 射手英雄
// * @Date 2023/4/13 17:49
// */
//public class Shooter extends Hero{
//
//    private int defense;//护甲
//
//    @Override
//    public String fight(Hero enemy) {
//        String result = "";
//        //打斗了多少回合
//        int i = 0;
//        //打斗不是一次结束，是来回打好几轮 while循环
//        while(true){
//            i++;
//            //英雄打斗后自己的血量 = 自己的血量 - 敌人的攻击力
//            this.setHp(this.getHp() - enemy.getPower());
//            //敌人打斗后的血量 = 敌人的血量 - 自己的攻击力
//            int enemyFinalHp = enemy.getHp() - this.getPower();
//            enemy.setHp(enemyFinalHp);
//            System.out.println("我" + this.getName() + "的血量为：" + this.getHp());
//            System.out.println("敌人" + enemy.getName() + "的血量为：" + enemy.getHp());
//
//            if(this.getHp() > enemy.getHp()){
//                //我的血量多
//                result = this.getName().concat( "赢了");
//            } else if (this.getHp() < enemy.getHp()) {
//                //我的血量少
//                result = enemy.getName().concat( "赢了");
//            } else {
//                result = this.getName() + "与" + enemy.getName() + "平局";
//            }
//            //什么时候比赛结束？英雄、敌人双方血量谁先为0谁就输
//            if(enemy.getHp() <= 0 | this.getHp() <= 0){
//                break;
//            }
//        }
//        System.out.println("最终打斗了" + i + "回合，结果为：" + result);
//        return result;
//
//
//    }
//
//    @Override
//    public String toString() {
//        return "Shooter{" +
//                "defense=" + defense +
//                "} " + super.toString();
//    }
//
//    public Shooter() {
//
//    }
//
//    public Shooter(String name, int hp, int power, int defense) {
//        super(name, hp, power);
//        this.defense = defense;
//    }
//
//    public int getDefense() {
//        return defense;
//    }
//
//    public void setDefense(int defense) {
//        this.defense = defense;
//    }
//}
