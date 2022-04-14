<%@ page import="java.util.*" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="titile.jsp" />
    <link rel="stylesheet" href="css/style.css" type="text/css" /><!--引用外部css-->
<%--    <link rel="stylesheet" href="css/search.css" type="text/css" /><!--引用外部css-->--%>
    <link rel="stylesheet" href="css/search2.css" type="text/css" /><!--引用外部css-->
    <link rel="shortcut icon" href="images/logo.ico" type="image/x-icon"/>


    <style>
        body,td,th {
            font-family: Helvetica , calibri , "宋体" , Arial , sans-serif , "微软雅黑";
        }
    </style>
</head>
<body>
<jsp:include page="head.jsp?index=2" />
<div class="g-bd" style="margin: 0 15% 0 15%;border: #A8A8A8 solid 1px;">
    <div class="g-wrap">
        <div class="ztag j-flag" id="auto-id-KEsGlTiIwDXkMwSW">
            <div class="n-srchrst">
                <div class="srchsongst">
                    <div class="item f-cb"><%--标题--%>
                        <div class="td">
                            <div class="hd">
                            </div>
                        </div>
                        <div class="td w0">
                            <div class="sn">
                                <div class="text">
                                        <text style="font-weight: bolder;">歌名</text>
                                </div>
                            </div>
                        </div>
                        <div class="td">
                            <div class="opt hshow">
                                <a class="u-icn u-icn-81 icn-add" href="#"></a>
                                <span class="icn icn-fav"></span>
                                <span class="icn icn-dl"></span>
                            </div>
                        </div>
                        <div class="td w1">
                            <div class="text">
                                <text style="font-weight: bolder;">歌手</text>
                            </div>
                        </div>
                        <div class="td w2">
                            <div class="text">
                                <text class="s-fc3" style="font-weight: bolder;">类型</text>
                            </div>
                        </div>
                        <div class="td" style="font-weight: bolder;">操作</div>
                    </div>
                    <%
                        String searchKey = request.getParameter("search");
                        Set<Integer> idSet;
                        if (searchKey == null || searchKey.length() <= 0){//没有内容
                            idSet = Query.searchMusic("");
                        }else {//有搜索内容
                            idSet = Query.searchMusic(searchKey);
                        }
                        //把id写入
                        if(idSet == null) idSet = new HashSet<>();
                        if (idSet.size() <= 0){//没有数据
                            out.println("<div class=\"item f-cb\">");
                            out.println("<div class=\"td\">");
                            out.println("<div class=\"hd\">");
                            out.println("<a href=\"#\" target=\"_black\" title=\"播放\">");//播放地址
                            out.println("<img height=\"100%\" width=\"100%\" src=\"images/music_play.png\">");
                            out.println("</a>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"td w0\">");
                            out.println("<div class=\"sn\">");
                            out.println("<div class=\"text\">");
                            out.println("<a href=\"#\">");
                            out.println("<b title=\"暂无结果\">暂无结果</b>");//歌名
                            out.println("</a>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"td\">");
                            out.println("<div class=\"opt hshow\">");
                            out.println("<a class=\"u-icn u-icn-81 icn-add\" href=\"#\"></a>");
                            out.println("<span class=\"icn icn-fav\"></span>");
                            out.println("<span class=\"icn icn-dl\"></span>");
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"td w1\">");
                            out.println("<div class=\"text\">");
                            out.println("<a title=\"暂无结果\">暂无结果</a></div>");//歌手
                            out.println("</div>");
                            out.println("<div class=\"td w2\">");
                            out.println("<div class=\"text\">");
                            out.println("<a class=\"s-fc3\" title=\"暂无结果\">暂无结果</a>");//类型
                            out.println("</div>");
                            out.println("</div>");
                            out.println("<div class=\"td\"><a href=\"\">喜欢</a></div>");
                            out.println("</div>");
                        }else {
                            Map<Integer, String> typeMap = Query.getMusicType(idSet);
                            if (typeMap == null) typeMap = new HashMap<>();
                            Map<Integer, String> singerMap = Query.getMusicSinger(idSet);
                            if (singerMap == null) singerMap = new HashMap<>();
                            Map<Integer , Map<String, Object>> musicMap = Query.getMusicInfo(idSet);
                            if (musicMap == null) musicMap = new HashMap<>();
                            boolean isgrey = false;
                            for (Map.Entry<Integer, Map<String, Object>> musicItemEntry : musicMap.entrySet()) {//遍历数组
                                int id = musicItemEntry.getKey();
                                Map<String , Object> musicInfo = musicItemEntry.getValue();
                                out.println("<div class=\"item f-cb" + (isgrey ? " even" : "") + "\">");
                                out.println("<div class=\"td\">");
                                out.println("<div class=\"hd\">");
                                String path = request.getContextPath() + "/playMusic.jsp?id=" + id;
                                out.println("<a href=\"" + path + "\" target=\"_black\" title=\"播放\">");//播放地址
                                out.println("<img height=\"100%\" width=\"100%\" src=\"images/music_play.png\">");
                                out.println("</a>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("<div class=\"td w0\">");
                                out.println("<div class=\"sn\">");
                                out.println("<div class=\"text\">");
                                out.println("<a href=\"#\">");
                                String name = (String) musicInfo.get("musicName");
                                out.println("<b title=\"" + name + "\">" + name + "</b>");//歌名
                                out.println("</a>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("<div class=\"td\">");
                                out.println("<div class=\"opt hshow\">");
                                out.println("<a class=\"u-icn u-icn-81 icn-add\" href=\"#\"></a>");
                                out.println("<span class=\"icn icn-fav\"></span>");
                                out.println("<span class=\"icn icn-dl\"></span>");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("<div class=\"td w1\">");
                                out.println("<div class=\"text\">");
                                String singer = singerMap.get(id);
                                out.println("<a title=\"" + singer + "\">" + singer + "</a></div>");//歌手
                                out.println("</div>");
                                out.println("<div class=\"td w2\">");
                                out.println("<div class=\"text\">");
                                String type = typeMap.get(id);
                                out.println("<a class=\"s-fc3\" title=\"" + type + "\">" + type + "</a>");//类型
                                out.println("</div>");
                                out.println("</div>");
                                out.println("<div class=\"td\"><a href=\"" + request.getContextPath() + "/user/addLikeItem.jsp?id=" + id + "\">喜欢</a></div>");
                                out.println("</div>");
                                isgrey = !isgrey;
                            }
                        }
                    %>






                </div>
            </div>
        </div>

    </div>
</div>



<jsp:include page="bottom.jsp" />
</body>
</html>
