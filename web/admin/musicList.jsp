<%@ page import="java.util.*" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="../titile.jsp" />
    <link rel="stylesheet" href="../css/userinfo.css" type="text/css" /><!--引用外部css-->
    <link rel="stylesheet" href="../css/style.css" type="text/css" /><!--引用外部css-->
    <link rel="shortcut icon" href="../images/logo.ico" type="image/x-icon"/>
    <style>
        body,td,th {
            font-family: Helvetica , calibri , "宋体" , Arial , sans-serif , "微软雅黑";
        }
    </style>
</head>
<body>
<jsp:include page="../head.jsp?index=0"/>
<jsp:include page="../user/leftHead.jsp?index=3"/>
<%
    Map<String , Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
    if (userinfo == null){
        out.println("<script> alert(\"请先登录！！\")");
        out.println("window.location = \"../login.jsp\"</script>");
        return;
    }
    int power = (int) userinfo.get("power");
    if (power < 1){//不是管理员
        out.println("<script> alert(\"您不是管理员！！\")");
        out.println("window.location = \"../login.jsp\"</script>");
        return;
    }
    //获取音乐列表
    int limit = 10;//限制每次查询条数
    int pagee;//当前页面为
    try {
        pagee = Integer.parseInt(request.getParameter("page"));
    }catch (Exception e){
        pagee = 1;
    }
    Map<String , Object> musiclist = Query.getMusicList(pagee , limit);
%>
<section class="rt_wrap content mCustomScrollbar" style="overflow-y: scroll;overflow-x: hidden;">
    <div class="rt_content" style="margin: 30px;">
        <h1>
            <strong style="color:grey;font-size: 32px;">音乐管理</strong>
            <a href="<%=request.getContextPath() + "/admin/musicAdd.jsp"%>" title="添加新的音乐" style="font-size: 20px;font-weight: bold;">添加音乐</a>
        </h1>
        <table class="table">
            <tr>
                <th>id</th>
                <th>歌名</th>
                <th>歌手</th>
                <th>类型</th>
                <th>添加时间</th>
                <th>操作</th>
            </tr>
            <%
                Set<Integer> idList = new HashSet<>();
                for (int i = 1 ; i <= limit ; i ++){
                    Map<String , Object> musicItem = (Map<String, Object>) musiclist.get("music_" + i);
                    if(musicItem == null){
                        continue;
                    }
                    int id = (int) musicItem.get("id");//音乐id
                    idList.add(id);
                }
                Map<Integer , String> typeMap = Query.getMusicType(idList);
                if(typeMap == null) typeMap = new HashMap<>();
                Map<Integer , String> singerMap = Query.getMusicSinger(idList);
                if(singerMap == null) singerMap = new HashMap<>();
                for (int i = 1; i <= limit ; i++){//遍历数组
                    Map<String , Object> musicItem = (Map<String, Object>) musiclist.get("music_" + i);
                    if(musicItem == null){
                        continue;
                    }
                    int id = (int) musicItem.get("id");
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + musicItem.get("musicName") + "</td>");
                    out.println("<td>" + singerMap.get(id) + "</td>");
                    out.println("<td>" + typeMap.get(id) + "</td>");
                    out.println("<td>" + main.java.business.Article.getArtTimeAll((Date) musicItem.get("regDate")) + "</td>");
                    out.println("<td><a href=\"" + request.getContextPath() + "/admin/musicDelete.jsp?id=" + id + "\">删除</a></td>");
                    out.println("<tr>");
                }
            %>
        </table>
        <h1 style="font-weight: bolder;font-size: 16px;padding: 10px;">
            <%
                if (pagee > 1){
                    out.println("<a href=\"musicList.jsp?page=" + (pagee - 1) + "\"> 上一页 </a>");
                }else {
                    out.println("<a href=\"#\"> 无上一页 </a>");
                }
            %>
            <%=musiclist.get("page")%> / <%=musiclist.get("pages")%>
            <%
                if(pagee < (int)musiclist.get("pages")){
                    out.println("<a href=\"musicList.jsp?page=" + (pagee + 1) + "\"> 下一页 </a>");
                }else {
                    out.println("<a href=\"#\"> 无下一页 </a>");
                }
            %>
        </h1>
    </div>
    <jsp:include page="../bottom.jsp" />
</section>
</body>
</html>
