<%@ page import="java.util.Map" %>
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
<jsp:include page="leftHead.jsp?index=5"/>
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
        <h1><strong style="color:grey;font-size: 32px;">基础设置</strong></h1>
<%--        修改用户名--%>
        <form action="changeinfo" method="post" accept-charset="utf-8">
            <h2 style="font-size: 20px;padding: 10px;">修改昵称</h2>
            <ul class="ulColumn2" style="font-size: 16px;">
                <li>
                    <span class="item_name" style="width:120px;">您的昵称：</span>
                    <input type="text" name="nickname" value="<%=userinfo.get("nickname")%>"/>
                </li>
            </ul>
            <input type="submit" style="padding: 10px;font-weight: bolder;" value="保存" />
        </form>
        <hr />
<%--        修改密码--%>
        <form action="changepassword" method="post" accept-charset="utf-8" onsubmit="return cpsw()">

            <h2 style="font-size: 20px;padding: 10px;">修改密码</h2>
            <ul class="ulColumn2" style="font-size: 16px;" id="changePasswordForm">
                <li>
                    <span class="item_name" style="width:120px;">您的密码：</span>
                    <input type="password" name="password" id="changePassword"/>
                </li>
                <li>
                    <span class="item_name" style="width:120px;">确认密码：</span>
                    <input type="password" id="changePssword2"/>
                </li>
            </ul>
            <input type="submit" style="padding: 10px;font-weight: bolder;" value="确认修改密码" />
        </form>
        <script>
            function cpsw(){
                const psw = document.getElementById("changePassword").value;
                const psw2 = document.getElementById("changePssword2").value;
                if(psw !== psw2){
                    window.alert("密码不一致")
                    return false
                }
                if (psw.length <= 5){
                    window.alert("密码长度不能小于5")
                    return false
                }
                return true

            }
        </script>
        <h2 style="font-size: 20px;padding: 10px;"><a href="outlogin.jsp">登出</a></h2>
    </div>
    <jsp:include page="../bottom.jsp" />
</section>
</body>
</html>
