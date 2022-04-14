<%@ page import="java.util.Map" %>
<%@ page import="main.java.business.Article" %>
<%@ page import="java.util.Date" %>
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
<jsp:include page="leftHead.jsp?index=1"/>
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
        <h1><strong style="color:grey;font-size: 32px;">我的资料</strong></h1>
        <ul class="ulColumn2" style="font-size: 16px;">
            <li>
                <span class="item_name" style="width:120px;">ID：</span>
                <span class="item_name"><%=userinfo.get("id")%></span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">头像：</span>
                <span class="item_name"><img src="http://q2.qlogo.cn/headimg_dl?dst_uin=<%=userinfo.get("qq")%>&spec=100&v=0.06244462880334645" alt="用户头像" width="260" height="260"/></span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">昵称：</span>
                <span class="item_name"><%=userinfo.get("nickname")%></span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">QQ：</span>
                <span class="item_name"><%=userinfo.get("qq")%></span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">邮箱：</span>
                <span class="item_name"><%=userinfo.get("qq")%>@qq.com</span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">用户组：</span>
                <span class="item_name"><%=Article.getPowerStr((Integer) userinfo.get("power"))%></span>
            </li>
            <li>
                <span class="item_name" style="width:120px;">注册时间：</span>
                <span class="item_name"><%=Article.getArtTimeAll((Date) userinfo.get("regDate"))%></span>
            </li>
        </ul>
    </div>
    <jsp:include page="../bottom.jsp" />
</section>
</body>
</html>
