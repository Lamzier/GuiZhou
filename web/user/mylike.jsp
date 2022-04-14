
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
<jsp:include page="../head.jsp?index=3"/>
<jsp:include page="leftHead.jsp?index=2"/>
<%
    Map<String , Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
    if (userinfo == null){
        out.println("<script> alert(\"请先登录！！\")");
        out.println("window.location = \"../login.jsp\"</script>");
        return;
    }
%>
<section class="rt_wrap content mCustomScrollbar" style="overflow-y: scroll;overflow-x: hidden;">
    <div class="rt_content" style="margin: 30px;">
        <h1><strong style="color:grey;font-size: 32px;">我的喜欢</strong></h1>
        <table class="table">
            <tr>
                <th>id</th>
                <th>歌名</th>
                <th>类型</th>
                <th>歌手</th>
                <th>操作</th>
            </tr>
            <%
                String likeItems = (String) userinfo.get("likeItems");
                String[] likeItemArray = likeItems.split(";");
                Set<Integer> idList = new HashSet<>();
                for (int i = 0 ; i < likeItemArray.length ; i ++){//遍历数组
                    String itemString = likeItemArray[i];
                    if (itemString.length() <= 0 || !itemString.matches("[-]?[0-9]+")){
                        continue;
                    }//过滤异常数据
                    int id = Integer.parseInt(itemString);
                    idList.add(id);//添加到id列表
                }
                Map<Integer , String> typeMap = Query.getMusicType(idList);
                if (typeMap == null) typeMap = new HashMap<>();
                Map<Integer , String> singerMap = Query.getMusicSinger(idList);
                if (singerMap == null) singerMap = new HashMap<>();
                for (int id : idList){//遍历id
                    Map<String , Object> musicInfo = Query.getMusicInfo(id);
                    if(musicInfo == null || musicInfo.size() <= 0){
                        continue;
                    }//找不到该歌
                    out.println("<tr>");
                    out.println("<td>" + musicInfo.get("id") + "</td>");
                    out.println("<td>" + musicInfo.get("musicName") + "</td>");
                    out.println("<td>" + typeMap.get(id) + "</td>");
                    out.println("<td>" + singerMap.get(id) + "</td>");
                    out.println("<td><a href=\"" + request.getContextPath() + "/user/delLikeItem.jsp?id=" + id + "\">删除</a></td>");
                    out.println("</tr>");
                }
            %>
        </table>
    </div>
    <jsp:include page="../bottom.jsp" />
</section>
</body>
</html>
