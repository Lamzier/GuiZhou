package main.java.business;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态常量类
 */
public class FinalAll {

    public final static String PROJECT_NAME = "GuiZhou";//项目名称
    public final static String PROJECT_NAME_CN = "归舟";//项目名称中文
    public final static int COOKIE_OUTTIME_DAY = 30;//cookie过期时间天
    public final static int CODE_OUTTIME_MIN = 30;//验证码过期时间分钟
    public final static List<String> MUSIC_SUFFIX = new ArrayList<>();//歌曲名字后缀
    public final static String LOCAL_SAVE_PATH = PROJECT_NAME + "/";//本地存储地址

    /**
     * 常量初始化
     */
    public static void init(){
        MUSIC_SUFFIX.add("mp3");
        MUSIC_SUFFIX.add("wav");
    }


}
