package top.testeru.system.game;

/**
 * @Author www.testeru.top
 * @Description
 * @Date 2023/4/13 16:23
 */
public class Hero {
    //英雄编号
    private String id;
    //英雄姓名
    private String name;
    //血量
    private int hp;
    //攻击力
    private int power;


    @Override
    public String toString() {
        return "Hero{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", power=" + power +
                '}';
    }

    public Hero() {
    }

    public Hero(String id, String name, int hp, int power) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.power = power;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }



   
}
