<%@ page import="java.util.*" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.business.MusicItem" %>
<%@ page import="main.java.business.User" %>
<%@ page import="main.java.business.UserCF" %>
<%@ page import="static main.java.business.Article.getHeatToString" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    Map<String , Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
%>
<link href="css/foundMusic.css" type="text/css" rel="stylesheet">
<div id="discover-module" class="g-bd1 f-cb" style="margin-top: 64px;">
    <div class="g-mn1">
        <div class="g-mn1c">

            <div class="g-wrap3">
                <div class="n-rcmd">
                    <div class="v-hd2">
                    <span class="tit">个性化推荐</span>
                </div>
                    <ul class="m-cvrlst">
                        <%
                            finish:{
                                //执行用户协同过滤算法
                                int limit = 20;//随机获取用户数量
                                List<Map<String, Object>> userRand = Query.getUserRand(limit);//随机获取用户
                                if (userRand == null) userRand = new ArrayList<>();
                                if(userinfo == null || userinfo.size() <= 0){
                                    List<Map<String , Object>> musicList = Query.getRandMusicInfo(4);//获取随机音乐
                                    if (musicList == null || musicList.size() <= 0) break finish;
                                    for(Map<String , Object> musicItem : musicList){//遍历数组
                                        int id;
                                        try{
                                            id = (int) musicItem.get("id");
                                        }catch (Exception e){
                                            continue ;
                                        }
                                        MusicItem musicItem1 = new MusicItem(id , (Integer) musicItem.get("heat"));
                                        Random random = new Random();
                                        musicItem1.setInterestedProgram(random.nextInt(100));//随机喜欢程度
                                        String outHtml = main.java.business.Article.getHtmlFountMusic(id + "",
                                                (String) musicItem.get("musicName"),
                                                request.getContextPath(),musicItem1);
                                        //写入html
                                        out.println(outHtml);
                                    }
                                    break finish;
                                }
                                int id = (Integer) userinfo.get("id");
                                User Master = new User(id,
                                        (String) userinfo.get("nickname"),
                                        (String) userinfo.get("qq"),
                                        (String) userinfo.get("likeItems"),
                                        (Integer) userinfo.get("power"),
                                        (Date) userinfo.get("regDate"));
                                //初始化主人信息
                                User[] Masses = new User[userRand.size()];
                                for (int i = 0; i < userRand.size(); i++) {//遍历数组
                                    Map<String, Object> tempMap = userRand.get(i);
                                    User temp = new User((Integer) tempMap.get("id"),
                                            (String) tempMap.get("nickname"),
                                            (String) tempMap.get("qq"),
                                            (String) tempMap.get("likeItems"),
                                            (Integer) tempMap.get("power"),
                                            (Date) tempMap.get("regDate"));
                                    Masses[i] = temp;//赋值
                                }
                                //处理完用户消息
                                UserCF userCF = new UserCF(Master, Masses);//进行协同处理
                                //Set<Integer> musicSet = userCF.getMusicItemSet();//获取音乐列表id,用于快速查询数据库
                                List<MusicItem> musicItems = userCF.getMusicItems();//获取可能感兴趣物品，感兴趣程度从到校排序
                                int showItemCount = 0;//展示数量
                                for (MusicItem musicItem : musicItems) {//循环显示，只显示4个
                                    Map<String, Object> musicItemMysql = Query.getMusicInfo(musicItem.getId());
                                    if (musicItemMysql == null) musicItemMysql = new HashMap<>();
                                    out.println(main.java.business.Article.getHtmlFountMusic(musicItem.getId() + "", (String) musicItemMysql.get("musicName"),
                                            request.getContextPath(), musicItem));

                                    showItemCount++;
                                    if (showItemCount >= 4) {//只展示四个
                                        break;//跳出循环体
                                    }
                                }
                                if (showItemCount < 4){//展示数量不够4个
                                    int showCound = 4 - showItemCount;
                                    List<Map<String , Object>> musicList = Query.getRandMusicInfo(showCound);//获取随机音乐
                                    if (musicList == null || musicList.size() <= 0) break finish;
                                    for(Map<String , Object> musicItem : musicList){//遍历数组
                                        int idd;
                                        try{
                                            idd = (int) musicItem.get("id");
                                        }catch (Exception e){
                                            continue ;
                                        }

                                        MusicItem musicItem1 = new MusicItem(idd , (Integer) musicItem.get("heat"));
                                        Random random = new Random();
                                        musicItem1.setInterestedProgram(random.nextInt(100));//随机喜欢程度
                                        String outHtml = main.java.business.Article.getHtmlFountMusic(idd + "",
                                                (String) musicItem.get("musicName"),
                                                request.getContextPath(),musicItem1);
                                        //写入html
                                        out.println(outHtml);
                                    }
                                }
                            }
                        %>
                    </ul>
                </div>
                <div class="n-clmnad">
                    <div class="j-flag"></div>
                </div>
            </div>
        </div>
    </div>




    <div class="g-sd1">
        <div class="n-user-profile">
            <div class="n-myinfo">
                <div class="f-cb">
                    <a href="#" class="head f-pr">
                        <img src="<%
                            if(userinfo == null) {
                                out.println("images/404.png");
                            }else {
                                out.println("http://q2.qlogo.cn/headimg_dl?dst_uin=" + userinfo.get("qq") + "&spec=100&v=0.06244462880334645");
                            }%>">
                    </a>
                    <div class="info">
                        <h4><a href="#" class="nm"><%
                                if(userinfo == null) {
                                    out.println("未登录");
                                }else {
                                    out.println(userinfo.get("nickname"));
                                }%></a></h4>
                    </div>
                </div>
                <ul class="dny s-fc3">
                    <li><a href="#">
                        <strong id="event_count">0</strong>
                        <span>动态</span>
                    </a></li>
                    <li class="vertical-split"></li>
                    <li><a href="#">
                        <strong id="follow_count"><%
                            if(userinfo == null) {
                                out.println("-1");
                            }else {
                                String likeItems = (String) userinfo.get("likeItems");
                                if(likeItems.length() <= 0){
                                    out.println(0);
                                }else {
                                    String[] likeItem = likeItems.split(";");
                                    out.println(likeItem.length);
                                }
                            }
                        %></strong><span>喜欢</span>
                    </a></li>
                    <li class="vertical-split"></li>
                    <li><a href="#">
                        <strong id="fan_count">0</strong><span>粉丝</span>
                    </a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="g-mn1"><!-- 热门推荐!-->
        <div class="g-mn1c">

            <div class="g-wrap3">
                <div class="n-rcmd">
                    <div class="v-hd2">
                        <span class="tit">热门推荐</span>
                    </div>
                    <ul class="m-cvrlst">
                        <%
                            finish:{
                                int limit = 8;//限制条数
                                Map<String, Map<String, Object>> MusicMap = Query.getMusicHeat(limit);
                                if (MusicMap == null) MusicMap = new HashMap<>();
                                for (Map.Entry<String, Map<String, Object>> musicItemEntry : MusicMap.entrySet()) {//循环遍历
                                    Map<String, Object> musicItem = musicItemEntry.getValue();
                                    if (musicItem == null || musicItem.size() <= 0) continue;
                                    out.println(main.java.business.Article.getHtmlFountMusic(musicItem.get("id").toString(), (String) musicItem.get("musicName"),
                                            request.getContextPath(), getHeatToString((Integer) musicItem.get("heat"))));
                                }
                            }
                        %>
                    </ul>
                </div>
                <div class="n-clmnad">
                    <div class="j-flag"></div>
                </div>
            </div>
        </div>
    </div><!-- 热门推荐 end!-->
    <div class="g-mn1"><!-- 最近新歌!-->
        <div class="g-mn1c">

            <div class="g-wrap3">
                <div class="n-rcmd">
                    <div class="v-hd2">
                        <span class="tit">最近新歌</span>
                    </div>
                    <ul class="m-cvrlst">
                        <%
                            finish:{
                                int limit = 8;//限制条数
                                Map<String, Map<String, Object>> MusicMap = Query.getMusicNew(limit);
                                if (MusicMap == null) MusicMap = new HashMap<>();
                                for (Map.Entry<String, Map<String, Object>> musicItemEntry : MusicMap.entrySet()) {//循环遍历
                                    Map<String, Object> musicItem = musicItemEntry.getValue();
                                    if (musicItem == null || musicItem.size() <= 0) continue;
                                    out.println(main.java.business.Article.getHtmlFountMusic(musicItem.get("id").toString(), (String) musicItem.get("musicName"),
                                            request.getContextPath()));
                                }
                            }
                        %>
                    </ul>
                </div>
                <div class="n-clmnad">
                    <div class="j-flag"></div>
                </div>
            </div>
        </div>
    </div><!-- 最近新歌 end!-->

</div>