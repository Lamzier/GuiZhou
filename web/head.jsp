<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!--导航栏!-->
<%
    int index;
    try{
        index = Integer.parseInt(request.getParameter("index"));
    }catch (Exception e){
        index = 0;
    }
    Map<String , Object> UserInfo = null;//用户信息
    //获取Cookie
    Cookie[] cookies = request.getCookies();
    finish: if (request.getCookies() != null){
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
            break finish;
        }
        String Mysql_Cookie = (String) CodeMap.get("cookie");
        Date Mysql_Date = (Date) CodeMap.get("regDate");
        Date Now_Date = new Date();
        long spanTime = Now_Date.getTime() - Mysql_Date.getTime();
        if (spanTime / 1000 / 60 / 60 / 24 > FinalAll.COOKIE_OUTTIME_DAY){//时间过期当做无登陆处理
            main.java.business.Cookie.removeCookieAll(cookies,response);//清除cookie
            break finish;
        }
        if (!cookie.equals(Mysql_Cookie)){//cookie不正确，当做无登陆处理
            main.java.business.Cookie.removeCookieAll(cookies,response);//清除cookie
            break finish;
        }
        //开始获取用户信息
        UserInfo = Query.getUserInfo(qq);//获取用户信息
        if(UserInfo == null || UserInfo.size() <= 0){
            UserInfo = null;
        }//把userinfo写入会话存储
        request.setAttribute("userinfo" , UserInfo);
        //完成用户信息获取
    }
%>
<div class="g-topbar" style="top: 0px;">
    <div class="m-top">
        <div class="wrap f-cb">
            <ul class="m-nav j-tflag">
                <li class="fst">
                    <span>
                        <a href="<%=request.getContextPath() + "/index.jsp"%>" <%=index == 1 ? "class=\"z-slt\"" : ""%>>
                            <em>发现音乐</em>
                            <sub class="cor">&nbsp;</sub>
                        </a>
                    </span>
                </li>
                <li>
                    <span>
                        <a href="<%=request.getContextPath() + "/searchMusic.jsp"%>" <%=index == 2 ? "class=\"z-slt\"" : ""%>>
                            <em>搜索音乐</em>
                            <sub class="cor">&nbsp;</sub>
                        </a>
                    </span>
                </li>
                <li>
                    <span>
                        <a href="<%=request.getContextPath() + "/user/mylike.jsp"%>" <%=index == 3 ? "class=\"z-slt\"" : ""%>>
                            <em>我的音乐</em>
                            <sub class="cor">&nbsp;</sub>
                        </a>
                    </span>
                </li>
            </ul>
            <div class="m-tophead">
                <div class="head">
                    <%
                        if(UserInfo == null){
                            out.println("<a href=\"login.jsp\" style=\"font-size: 12px;color: #FFFFFF;font-weight: bolder;\">");
                            out.println("登陆");
                            out.println("</a>");
                        }else {
                            out.println("<a href=\"" + request.getContextPath() + "/user\" style=\"font-size: 12px;color: #FFFFFF;font-weight: bolder;\">");
                            out.println("<img src=\"http://q2.qlogo.cn/headimg_dl?dst_uin=" + UserInfo.get("qq") + "&spec=100&v=0.06244462880334645\" alt=\"" + UserInfo.get("nickname") + "\">");
                            out.println("</a>");
                        }
                    %>
                </div>
            </div>
            <div class="m-srch">
                <div class="srchbg">
                    <form action="<%=request.getContextPath() + "/searchMusic.jsp"%>" method="get" accept-charset="utf-8">
                        <span class="parent">
                            <input type="text" name="search" class="txt j-flag" value="" style="opacity: 1;" placeholder="音乐/视频/电台/用户">
                        </span>
                        <span>
                            <input type="submit" value="搜索">
                        </span>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
