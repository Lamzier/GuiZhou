<%@ page import="java.util.Map" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.encoder.Md5" %>
<%@ page import="main.java.mysql.Update" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="titile.jsp" />
    <style>
        body {
            background: url('images/login_background.png') no-repeat;
            background-size: 100% 130%;
        }
        #login_box {
            width: 20%;
            height: 400px;
            background-color: #00000060;
            margin: auto;
            margin-top: 10%;
            text-align: center;
            border-radius: 10px;
            padding: 50px 50px;
        }
        h2 {
            color: #ffffff90;
            margin-top: 5%;
        }
        #input-box {
            margin-top: 5%;
        }
        span {
            color: #fff;
        }
        input {
            border: 0;
            width: 60%;
            font-size: 15px;
            color: #fff;
            background: transparent;
            border-bottom: 2px solid #fff;
            padding: 5px 10px;
            outline: none;
            margin-top: 10px;
        }
        .button {
            margin-top: 50px;
            width: 60%;
            height: 30px;
            border-radius: 10px;
            border: 0;
            color: #fff;
            text-align: center;
            line-height: 30px;
            font-size: 15px;
            background-image: linear-gradient(to right, #30cfd0, #330867);
        }
        #sign_up {
            margin-top: 45%;
            margin-left: 60%;
        }
        a {
            color: #b94648;
        }
    </style>
</head>
<body>
<div id="login_box">
    <h2>登陆</h2>
    <form action="login.jsp" method="post" accept-charset="utf-8">
        <div>
            <input type="text" name="nickname" placeholder="请输入QQ号码">
        </div>
        <div class="input_box">
            <input type="password" name="password" placeholder="请输入密码">
        </div>
        <input type="submit" class="button" value="登陆"><br/>
        <a href="register.jsp">前往注册</a><br />
        <a href="index.jsp">返回首页</a>
    </form>
</div>
</body>
<%
    String NickName = request.getParameter("nickname");
    String Password = request.getParameter("password");
    finish: if (NickName != null && Password != null){
        if (NickName.length() < 3 || NickName.length() > 16){
            out.println("<script> alert(\"用户名长度异常！\")</script>");
            break finish;
        }
        if (Password.length() < 3 || Password.length() > 32){
            out.println("<script> alert(\"密码长度异常！\")</script>");
            break finish;
        }
        //数据校验完成
        Map<String , Object> UserInfo = Query.getUserInfo(NickName);
        if (UserInfo == null || UserInfo.get("password") == null){
            out.println("<script> alert(\"账号密码错误！\")</script>");
            break finish;
        }
        String Mysql_Password = (String) UserInfo.get("password");
        String PassowrdEncoder = Md5.doMd5(NickName , Password);
        if (!PassowrdEncoder.equals(Mysql_Password)){
            out.println("<script> alert(\"账号密码错误！2\")</script>");
            break finish;
        }
        //密码正确，设置cookie
        String Mysql_Cookie = main.java.business.Cookie.getCookie();
        if(!Update.updCodeCookie(NickName , Mysql_Cookie)){
            out.println("<script> alert(\"运行时异常！\")</script>");
            break finish;
        }
        Cookie[] Cookies = request.getCookies();
        main.java.business.Cookie.removeCookieAll(Cookies,response);//清除cookie
        Cookie Cookie = new Cookie("cookie" , Mysql_Cookie);
        Cookie QQCookie = new Cookie("qq" , NickName);
        Cookie.setMaxAge(60 * 60 * 24 * FinalAll.COOKIE_OUTTIME_DAY);//设置Cookie过期时间
        QQCookie.setMaxAge(60 * 60 * 24 * FinalAll.COOKIE_OUTTIME_DAY);
        Cookie.setPath("/");
        QQCookie.setPath("/");
        response.addCookie(Cookie);
        response.addCookie(QQCookie);
        //完成cookie处理
        response.sendRedirect("index.jsp");//重定向到index
    }
%>
</html>