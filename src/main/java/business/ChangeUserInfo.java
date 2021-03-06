package main.java.business;

import main.java.mysql.Query;
import main.java.mysql.Update;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@WebServlet("/user/changeinfo")
public class ChangeUserInfo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        javax.servlet.http.Cookie[] cookies = req.getCookies();
        if (cookies == null){
            out.println("<script> alert(\"cookie异常\";location.href=\"../login.jsp\")</script>");
            return;
        }
        String cookie = "";
        String qq = "";
        for (javax.servlet.http.Cookie cookie1 : cookies){
            if (cookie1.getName().equals("cookie")){
                cookie = cookie1.getValue();
            }else if(cookie1.getName().equals("qq")){
                qq = cookie1.getValue();
            }
        }
        String nickname = req.getParameter("nickname");
        if (nickname.length() < 4 || nickname.length() > 32){
            out.println("<script> alert(\"昵称长度异常\";location.href=\"setting.jsp\")</script>");
            return;
        }
        Map<String , Object> CodeMap = Query.getCode(qq);
        if(CodeMap == null || CodeMap.size() <= 0){//没有用户信息
            out.println("<script> alert(\"用户异常\";location.href=\"../login.jsp\")</script>");
            return;
        }
        String Mysql_Cookie = (String) CodeMap.get("cookie");
        Date Mysql_Date = (Date) CodeMap.get("regDate");
        Date Now_Date = new Date();
        long spanTime = Now_Date.getTime() - Mysql_Date.getTime();
        if (spanTime / 1000 / 60 / 60 / 24 > FinalAll.COOKIE_OUTTIME_DAY){//时间过期当做无登陆处理
            main.java.business.Cookie.removeCookieAll(cookies,resp);//清除cookie
            out.println("<script> alert(\"COOKIE过期\";location.href=\"../login.jsp\")</script>");
            return;
        }
        if (!cookie.equals(Mysql_Cookie)){//cookie不正确，当做无登陆处理
            out.println("<script> alert(\"COOKIE不正确\";location.href=\"../login.jsp\")</script>");
            return;
        }
        //成功获取用户信息
        if (!Update.changeUserInfo(qq , nickname)){//修改昵称
            out.println("<script> alert(\"运行时异常!\");location.href=\"setting.jsp\"</script>");
            return;
        }
        out.println("<script> alert(\"修改成功!\");location.href=\"setting.jsp\"</script>");
//        resp.sendRedirect("setting.jsp");
    }
}
