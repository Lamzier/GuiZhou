<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page import="main.java.mysql.Update" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    Map<String , Object> UserInfo;//用户信息
    //获取Cookie
    Cookie[] cookies = request.getCookies();
    if (cookies == null){
        out.println("<script> alert(\"请先登录！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/login.jsp" + "\"</script>");
        return;
    }
    String cookie = "";
    String qq = "";
    for(Cookie co : cookies){
        if(co.getName().equals("cookie")){
            cookie = co.getValue();
        }else if(co.getName().equals("qq")){
            qq = co.getValue();
        }
    }//获取cookie值
    Map<String , Object> CodeMap = Query.getCode(qq);
    if(CodeMap == null || CodeMap.size() <= 0){//没有用户信息
        out.println("<script> alert(\"请先登录！！2\")");
        out.println("window.location = \"" + request.getContextPath() + "/login.jsp" + "\"</script>");
        return;
    }
    String Mysql_Cookie = (String) CodeMap.get("cookie");
    Date Mysql_Date = (Date) CodeMap.get("regDate");
    Date Now_Date = new Date();
    long spanTime = Now_Date.getTime() - Mysql_Date.getTime();
    if (spanTime / 1000 / 60 / 60 / 24 > FinalAll.COOKIE_OUTTIME_DAY){//时间过期当做无登陆处理
        main.java.business.Cookie.removeCookieAll(cookies,response);//清除cookie
        out.println("<script> alert(\"登录已过期！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/login.jsp" + "\"</script>");
        return;
    }
    if (!cookie.equals(Mysql_Cookie)){//cookie不正确，当做无登陆处理
        main.java.business.Cookie.removeCookieAll(cookies,response);//清除cookie
        out.println("<script> alert(\"cookie异常！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/login.jsp" + "\"</script>");
        return;
    }
    UserInfo = Query.getUserInfo(qq);//获取用户信息
    if (UserInfo == null || UserInfo.size() <= 0){
        out.println("<script> alert(\"用户异常！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/login.jsp" + "\"</script>");
        return;
    }
    int power = (int) UserInfo.get("power");
    if (power < 1){
        out.println("<script> alert(\"您不是管理员！！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/index.jsp" + "\"</script>");
        return;
    }
    //权限检查完毕开始校验数据
    int id;
    try {
        id = Integer.parseInt(request.getParameter("id"));
    }catch (Exception e){
        out.println("<script> alert(\"数据异常！！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/admin/userList.jsp" + "\"</script>");
        return;
    }
    if(!Update.deleteUser(id)){
        out.println("<script> alert(\"内部异常！！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/admin/userList.jsp" + "\"</script>");
        return;
    }
    out.println("<script> alert(\"操作成功！！！\")");
    out.println("window.location = \"" + request.getContextPath() + "/admin/userList.jsp" + "\"</script>");
    return;
%>