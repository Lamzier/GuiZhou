<%@ page import="main.java.business.FinalAll" %>
<%@ page import="java.util.*" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.mysql.Update" %>
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
    String id = request.getParameter("id");
    if (id == null || id.length() <= 0){
        out.println("<script> alert(\"数据异常！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/user/mylike.jsp" + "\"</script>");
        return;
    }
    //完成数据校验
    String likeItems = (String) UserInfo.get("likeItems");
    String[] likeItemArray = likeItems.split(";");
    List<String> likeItemList = Arrays.asList(likeItemArray);
    Set<String> likeItemSet = new HashSet<>(likeItemList);
    likeItemSet.remove(id);//删除元素
    StringBuilder outLikeItme = new StringBuilder();
    for(String item : likeItemSet){//遍历数组
        if (item.matches("[-]?[0-9]+")){//是数组
            outLikeItme.append(item).append(";");
        }
    }
    if (outLikeItme.length() > 0){
        outLikeItme.delete(outLikeItme.length() - 1 ,outLikeItme.length());
    }
    if(!Update.updUserLikeItem(qq,outLikeItme.toString())){
        out.println("<script> alert(\"内部异常！！\")");
        out.println("window.location = \"" + request.getContextPath() + "/user/mylike.jsp" + "\"</script>");
        return;
    }
    out.println("<script> alert(\"操作成功！！\")");
    out.println("window.location = \"" + request.getContextPath() + "/user/mylike.jsp" + "\"</script>");
    return;
%>