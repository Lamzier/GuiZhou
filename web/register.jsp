<%@ page import="java.util.Map" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page import="main.java.request.Email" %>
<%@ page import="main.java.mysql.Query" %>
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
    <h2>注册</h2>
    <form action="register.jsp" method="post">
        <div>
            <input type="text" name="nickname" placeholder="请输入用户名">
        </div>
        <div>
            <input type="text" name="qq" placeholder="请输入qq号码">
        </div>
        <div class="input_box">
            <input type="password" name="password" placeholder="请输入密码">
        </div>
        <div class="input_box">
            <input type="password" placeholder="请输入确认密码">
        </div>
        <input type="submit" class="button" value="注册"><br/>
        <a href="login.jsp">返回登陆</a>
    </form>
</div>
</body>
<%
    String NickName = request.getParameter("nickname");
    String QQ = request.getParameter("qq");
    String Password = request.getParameter("password");
    finish: if (NickName != null && QQ != null && Password != null){
        if (NickName.length() < 3 || NickName.length() > 16){
            out.println("<script> alert(\"用户名长度异常\")</script>");
            break finish;
        }
        if (!QQ.matches("[0-9]{5,16}")){
            out.println("<script> alert(\"QQ号码异常\")</script>");
            break finish;
        }
        if (Password.length() < 3 || Password.length() > 32){
            out.println("<script> alert(\"密码长度异常！\")</script>");
            break finish;
        }
        //数据校验完成 , 检查是否被注册
        Map<String , Object> UserInfo = Query.getUserInfo(QQ);
        if (UserInfo != null && UserInfo.size() <= 0){//已经被注册
            out.println("<script> alert(\"QQ已经被注册！！\")</script>");
            break finish;
        }
        //发送验证码
        String Code = main.java.business.Cookie.getCode();

        String Cookie = main.java.business.Cookie.getCookie();
        if (!mysql.QandU.updCode(QQ,Code,Cookie)){
            out.println("<script> alert(\"内部异常！！\")</script>");
            break finish;
        }
        String Title = FinalAll.PROJECT_NAME_CN;
        String Content = "您的验证码是【" + Code + "】";
        String To = QQ + "@qq.com";
        class EmailTemp extends Thread{//建立内部类用于多线程传参处理

            private final String Title;
            private final String Content;
            private final String To;

            public EmailTemp(String Title , String Content , String To){
                this.Title = Title;
                this.Content = Content;
                this.To = To;
            }

            @Override
            public void run() {
                Email.send(Title,Content,To);

            }
        }
        EmailTemp emailTemp = new EmailTemp(Title,Content,To);//初始化参数
        emailTemp.start();//执行线程
        out.println("<script> alert(\"请注意查收QQ邮箱验证码！\")</script>");
        request.getRequestDispatcher("code.jsp").forward(request,response);
    }
%>
</html>