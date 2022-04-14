<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
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
<jsp:include page="../user/leftHead.jsp?index=4"/>
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
    //获取用户列表
    int limit = 10;//限制每次查询条数
    int pagee;//当前页面为
    try {
        pagee = Integer.parseInt(request.getParameter("page"));
    }catch (Exception e){
        pagee = 1;
    }
    Map<String , Object> userlist = Query.getUserList(pagee , limit);
%>
<section class="rt_wrap content mCustomScrollbar" style="overflow-y: scroll;overflow-x: hidden;">
    <div class="rt_content" style="margin: 30px;">
        <h1>
            <strong style="color:grey;font-size: 32px;">用户管理</strong>
        </h1>
        <table class="table">
            <tr>
                <th>id</th>
                <th>昵称</th>
                <th>QQ</th>
                <th>用户组</th>
                <th>注册时间</th>
                <th>操作</th>
            </tr>
            <%
                //循环输出查询到的用户列表
                for (int i = 1 ; i <= limit ; i++){
                    Map<String , Object> otherUserinfo = (Map<String, Object>) userlist.get("userinfo_" + i);
                    if (otherUserinfo == null || otherUserinfo.size() <= 0){//没有数据跳过
                        continue;
                    }
                    //有数据输出
                    out.println("<tr>");
                    int id = (int) otherUserinfo.get("id");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + otherUserinfo.get("nickname") + "</td>");
                    out.println("<td>" + otherUserinfo.get("qq") + "</td>");
                    out.println("<td>" + main.java.business.Article.getPowerStr((Integer) otherUserinfo.get("power")) + "</td>");
                    out.println("<td>" + main.java.business.Article.getArtTimeAll((Date) otherUserinfo.get("regDate")) + "</td>");
                    out.println("<td><a href=\"" + request.getContextPath() + "/admin/userDelete.jsp?id=" + id + "\">删除</a></td>");
                    out.println("</tr>");
                }
            %>
        </table>
        <h1 style="font-weight: bolder;font-size: 16px;padding: 10px;">
            <%
                if (pagee > 1){
                    out.println("<a href=\"userList.jsp?page=" + (pagee - 1) + "\"> 上一页 </a>");
                }else {
                    out.println("<a href=\"#\"> 无上一页 </a>");
                }
            %>
            <%=userlist.get("page")%> / <%=userlist.get("pages")%>
            <%
                if(pagee < (int)userlist.get("pages")){
                    out.println("<a href=\"userList.jsp?page=" + (pagee + 1) + "\"> 下一页 </a>");
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
