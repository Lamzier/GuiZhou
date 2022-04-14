package main.java.business;

import java.util.*;

/**
 * 用户类
 */
public class User implements Comparable<User>{//实现接口

    private int id;//用户id
    private String nickname;//用户昵称
    private String qq;//用户QQ
    private Set<Integer> likeItmes = new HashSet<>();//用户喜欢列表
    private int power;//用户权限
    private Date regDate;//用户注册时间
    private double Similarity;//相似度

    /**
     * 初始化用户
     */
    public User(int id, String nickname, String qq, String likeItmes,
                int power, Date regDate){
        this.id = id;
        this.nickname = nickname == null ? "" : nickname;
        this.qq = qq == null ? "" : qq;
        String[] likeItemArray = likeItmes.split(";");
        for (String likeItem : likeItemArray){//遍历数组
            try {
                int ida = Integer.parseInt(likeItem);
                this.likeItmes.add(ida);
            }catch (Exception e){
                System.out.println("ida数据异常，但是影响不大！");
            }
        }
        this.power = power;
        this.regDate = regDate == null ? new Date() : regDate;
    }

    /**
     * 是否有这个喜欢的item
     * @return 有
     */
    public boolean hasLikeItem(String item){
        return likeItmes.contains(item);
    }

    public void setSimilarity(double similarity) {
        this.Similarity = similarity;
    }

    public double getSimilarity() {
        return Similarity;
    }

    public int getId() {
        return id;
    }

    public Date getRegDate() {
        return regDate;
    }

    public int getPower() {
        return power;
    }

    public Set<Integer> getLikeItmes() {
        return likeItmes;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public int compareTo(User o) {
        return (int)(this.Similarity * 1000);
    }
}
