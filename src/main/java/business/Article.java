package main.java.business;

import main.java.business.MusicItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 文章字符串处理类
 */
public class Article {

    private static SimpleDateFormat articleAllFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    /**
     * 获取详细时间
     */
    public static String getArtTimeAll(Date date){
        return articleAllFormat.format(date);
    }

    /**
     * 获取文章指定长度
     * @param len 长度
     */
    public static String getShowContent(String content , int len){
        if (content.length() < len) return content;//没超过指定长度，直接输出
        return content.substring(0 , len) + "……";
    }

    /**
     * 获取热度到文本
     */
    public static String getHeatToString(int heat){
        if (heat < 1000){//千以内
            return heat + "";
        }else if(heat < 10000){//万以内
            return (heat / 100 / 10.0) + "千";
        }else if(heat < 100000000){//亿以内
            return (heat / 1000 / 10.0) + "万";//保留一位小数
        }else{
            return heat / 10000000 / 10.0 + "亿";
        }
    }

    /**
     * 获取用户组称呼
     */
    public static String getPowerStr(int power){
        if (power < 0){
            return "游客";
        }else if (power == 0){
            return "会员";
        }else {
            return "管理员";

        }
    }

    /**
     * 数组转换为String
     * @return
     */
    public static String getListToString(List<String> lists){
        if (lists == null){
            return "";
        }
        StringBuilder re = new StringBuilder();
        for (int i = 0 ; i < lists.size() ; i++){
            re.append(lists.get(i));
            if (i != lists.size() - 1){
                re.append(";");
            }
        }
        return re.toString();
    }

    /**
     * 推荐歌曲html代码
     */
    public static String getHtmlFountMusic(String id , String musicName , String contextPath,
                                      MusicItem musicItem){
        String path = contextPath + "/playMusic.jsp?id=" + id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<li>\n");
        stringBuilder.append("<div class=\"u-cover u-cover-1\">");
        Random random = new Random();//用于区分url
//        stringBuilder.append("<img src=\"https://www.dmoe.cc/random.php?text=" + random.nextInt(100) + "\">");//封面 这是随机封面api，网上找的api
        stringBuilder.append("<img src=\"https://api.ixiaowai.cn/mcapi/mcapi.php?text=" + random.nextInt(100) + "\">");//封面 这是随机封面api，网上找的api
        stringBuilder.append("<a title=\"" + musicName + "\" target=\"_black\" href=\"" + path + "\" class=\"msk\"></a>");//标题
        stringBuilder.append("<div class=\"bottom\">");
        stringBuilder.append("<a title=\"播放\" class=\"icon-play f-fr\" href=\"#\"></a>");
        stringBuilder.append("<a href=\"javascript:;\" class=\"icon-headset\"></a>");
        int like = musicItem.getInterestedProgram();
        if (like <= 0){
            like = random.nextInt(100);
        }
        stringBuilder.append("<span class=\"nb\" style=\"color: #000000;font-size: bolder;\">喜欢程度：" + like + "</span>");//喜欢程度
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("<p class=\"dec f-brk\">");
        stringBuilder.append("<a title=\"" + musicName + "\" class=\"tit s-fc0\" target=\"_black\" href=\"" + path + "\">" +musicName + "</a>");//标题
        stringBuilder.append("</p>");
        stringBuilder.append("<p class=\"idv f-brk s-fc4\" title=\"猜你喜欢\">");
        stringBuilder.append("<em>猜你喜欢</em>");
        String likePath = contextPath + "/user/addLikeItem.jsp?id=" + id;
        stringBuilder.append("<a class=\"btn\" target=\"_black\" href=\"" + likePath + "\"> 添加喜欢</a>");//播放
        stringBuilder.append("</p>");
        stringBuilder.append("</li>");
        return stringBuilder.toString();
    }

    /**
     * 热门歌曲html代码
     */
    public static String getHtmlFountMusic(String id , String musicName , String contextPath, String heat){
        String path = contextPath + "/playMusic.jsp?id=" + id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<li>\n");
        stringBuilder.append("<div class=\"u-cover u-cover-1\">");
        Random random = new Random();//用于区分url
        stringBuilder.append("<img src=\"https://api.mtyqx.cn/api/random.php?text=" + random.nextInt(100) + "\">");//封面 这是随机封面api，网上找的api
        stringBuilder.append("<a title=\"" + musicName + "\" target=\"_black\" href=\"" + path + "\" class=\"msk\"></a>");//标题
        stringBuilder.append("<div class=\"bottom\">");
        stringBuilder.append("<a title=\"播放\" class=\"icon-play f-fr\" href=\"#\"></a>");
        stringBuilder.append("<a href=\"javascript:;\" class=\"icon-headset\"></a>");
        stringBuilder.append("<span class=\"nb\" style=\"color: #000000;font-size: bolder;\"></span>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("<p class=\"dec f-brk\">");
        stringBuilder.append("<a title=\"" + musicName + "\" class=\"tit s-fc0\" target=\"_black\" href=\"" + path + "\">" +musicName + "</a>");//标题
        stringBuilder.append("</p>");
        stringBuilder.append("<p class=\"idv f-brk s-fc4\" title=\"猜你喜欢\">");
        stringBuilder.append("<em>热度：" + heat + "</em>");
        String likePath = contextPath + "/user/addLikeItem.jsp?id=" + id;
        stringBuilder.append("<a class=\"btn\" target=\"_black\" href=\"" + likePath + "\"> 添加喜欢</a>");//播放
        stringBuilder.append("</p>");
        stringBuilder.append("</li>");
        return stringBuilder.toString();
    }

    /**
     * 新歌歌曲html代码
     */
    public static String getHtmlFountMusic(String id , String musicName , String contextPath){
        String path = contextPath + "/playMusic.jsp?id=" + id;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<li>\n");
        stringBuilder.append("<div class=\"u-cover u-cover-1\">");
        Random random = new Random();//用于区分url
        stringBuilder.append("<img src=\"https://www.dmoe.cc/random.php?text=" + random.nextInt(100) + "\">");//封面 这是随机封面api，网上找的api
        stringBuilder.append("<a title=\"" + musicName + "\" target=\"_black\" href=\"" + path + "\" class=\"msk\"></a>");//标题
        stringBuilder.append("<div class=\"bottom\">");
        stringBuilder.append("<a title=\"播放\" class=\"icon-play f-fr\" href=\"#\"></a>");
        stringBuilder.append("<a href=\"javascript:;\" class=\"icon-headset\"></a>");
        stringBuilder.append("<span class=\"nb\" style=\"color: #000000;font-size: bolder;\"></span>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("<p class=\"dec f-brk\">");
        stringBuilder.append("<a title=\"" + musicName + "\" class=\"tit s-fc0\" target=\"_black\" href=\"" + path + "\">" +musicName + "</a>");//标题
        stringBuilder.append("</p>");
        stringBuilder.append("<p class=\"idv f-brk s-fc4\" title=\"猜你喜欢\">");
        //stringBuilder.append("<em>热度：" + heat + "</em>");
        String likePath = contextPath + "/user/addLikeItem.jsp?id=" + id;
        stringBuilder.append("<a class=\"btn\" target=\"_black\" href=\"" + likePath + "\"> 添加喜欢</a>");//播放
        stringBuilder.append("</p>");
        stringBuilder.append("</li>");
        return stringBuilder.toString();
    }

}
