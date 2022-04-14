<%@ page import="java.util.Map" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="java.util.Date" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page import="main.java.mysql.Update" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.encoder.Md5" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<%
    String QQ = request.getParameter("qq");
    String NickName = request.getParameter("nickname");
    String Password = request.getParameter("password");
    String Code = request.getParameter("code");
    finish :if (QQ == null || NickName == null || Password == null){
        out.println("<script> alert(\"参数异常！！\")");
        out.println("window.location = \"register.jsp\"</script>");
    }else {
        if (!QQ.matches("[0-9]{5,16}")){
            out.println("<script> alert(\"QQ号码异常！！\")");
            out.println("window.location = \"register.jsp\"</script>");
            break finish;
        }
        if (NickName.length() < 3 || NickName.length() > 16){
            out.println("<script> alert(\"用户名长度异常！！\")");
            out.println("window.location = \"register.jsp\"</script>");
            break finish;
        }
        if (Password.length() < 3 || Password.length() > 32){
            out.println("<script> alert(\"密码长度异常！！\")");
            out.println("window.location = \"register.jsp\"</script>");
            break finish;
        }
        //数据校验成功
        if (Code == null || Code.length() <= 0){
            break finish;
        }
        //还有Code进行注册
        Map<String , Object> CodeMap = Query.getCode(QQ);
        if (CodeMap == null || CodeMap.get("verCode") == null || CodeMap.get("regDate") == null){
            out.println("<script> alert(\"运行时异常！\")</script>");
            break finish;
        }
        String Mysql_Code = (String) CodeMap.get("verCode");
        Date CodeReg = (Date) CodeMap.get("regDate");
        Date NowDate = new Date();
        long SpanTime = Math.abs(NowDate.getTime() - CodeReg.getTime());//毫秒，abs不能删，排除系统时间差
        if (SpanTime / 1000 / 60 > FinalAll.CODE_OUTTIME_MIN){//过期了
            out.println("<script> alert(\"验证码过期了！\")</script>");
            break finish;
        }
        if (!Code.equals(Mysql_Code)){
            out.println("<script> alert(\"验证码不正确！\")</script>");
            break finish;
        }
        //验证码正确，开始注册
        String PasswordEncoder = main.java.encoder.Md5.doMd5(QQ,Password);
        if (!Update.registerUser(NickName,QQ,PasswordEncoder)){
            out.println("<script> alert(\"运行时异常！2\")</script>");
            break finish;
        }
        //注册成功
        int UserCount = Query.getCount("UserInfo");
        if (UserCount == 1){//只有一个用户
            Update.updUserPower(QQ , 1);
        }
        out.println("<script> alert(\"注册成功！请前往登陆!\")");
        out.println("window.location = \"login.jsp\"</script>");
    }
%>
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
    <h2>验证码</h2>
    <form action="code.jsp" method="post">
        <%
            out.println("<input type=\"hidden\" name=\"qq\" value=\"" + QQ + "\" + qq + \"\">");
            out.println("<input type=\"hidden\" name=\"nickname\" value=\"" + NickName + "\" + qq + \"\">");
            out.println("<input type=\"hidden\" name=\"password\" value=\"" + Password + "\" + qq + \"\">");
        %>
        <div>
            <input type="text" name="code" placeholder="请输入验证码">
        </div>
        <input type="submit" class="button" value="确认"><br/>
    </form>
</div>
</body>
</html>