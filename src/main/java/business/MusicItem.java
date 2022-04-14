package main.java.business;

import java.util.Objects;

/**
 * 物品类
 */
public class MusicItem implements Comparable<MusicItem>{//实现接口

    private int id;//物品id
    private int InterestedProgram;//感兴趣程序
    private int heat;

    public MusicItem(int id , int heat){
        this.id = id;
        this.heat = heat;
    }

    public void setInterestedProgram(int interestedProgram) {
        InterestedProgram = interestedProgram;
    }

    public int getInterestedProgram() {
        return InterestedProgram;
    }

    public int getId() {
        return id;
    }

    public int getHeat() {
        return heat;
    }

//    @Override
    public boolean equals(MusicItem musicItem) {
        if (this == musicItem) return true;//等于自己
        if (musicItem == null) return false;
        return getId() == musicItem.getId();//仅判断id是否相同
    }

    @Override
    public int hashCode() {//仅用id判断音乐是否相同
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(MusicItem o) {//返回id
        return this.getId();
    }
}
