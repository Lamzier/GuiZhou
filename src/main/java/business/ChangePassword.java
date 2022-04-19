package main.java.business;

import main.java.mysql.Query;
import main.java.mysql.Update;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@WebServlet("/user/changepassword")
public class ChangePassword  extends HttpServlet {

    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        if (cookies == null){
            out.println("<script> alert(\"cookie异常\";location.href=\"../login.jsp\")</script>");
            return;
        }
        String cookie = "";
        String qq = "";
        for (Cookie cookie1 : cookies){
            if (cookie1.getName().equals("cookie")){
                cookie = cookie1.getValue();
            }else if(cookie1.getName().equals("qq")){
                qq = cookie1.getValue();
            }
        }
        String passowrd = req.getParameter("password");
        if (passowrd.length() < 5 || passowrd.length() > 32){
            out.println("<script> alert(\"密码长度异常\";location.href=\"setting.jsp\")</script>");
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
        String PasswordEncoder = main.java.encoder.Md5.doMd5(qq,passowrd);
        if (!Update.changeUserPassword(qq , PasswordEncoder)){//修改密码
            out.println("<script> alert(\"运行时异常!\");location.href=\"setting.jsp\"</script>");
            return;
        }
        out.println("<script> alert(\"修改成功，请重新登陆!\";location.href=\"../login.jsp\")</script>");
        main.java.business.Cookie.removeCookieAll(cookies,resp);//清除cookie
        resp.sendRedirect("../login.jsp");
    }

    public void destroy() {
    }
}
