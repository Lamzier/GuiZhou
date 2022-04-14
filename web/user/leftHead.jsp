<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    int index;
    try {
        index = Integer.parseInt(request.getParameter("index"));
    }catch (Exception e){
        index = 0;
    }
    Map<String , Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
    if (userinfo == null || userinfo.size() <= 0){//没有用户数据
        return;
    }
    int power = (int) userinfo.get("power");
%>
<aside class="lt_aside_nav content mCustomScrollbar">
    <h2><a href="../index.jsp">站点首页</a></h2>
    <ul>
        <li>
            <dl>
                <dt>基本信息</dt>
                <!--当前链接则添加class:active-->
                <dd><a href="<%=request.getContextPath()%>/user/" <%=index == 1 ? "class=\"active\"" : ""%>>我的资料</a></dd>
                <dd><a href="<%=request.getContextPath()%>/user/mylike.jsp" <%=index == 2 ? "class=\"active\"" : ""%>>我的喜欢</a></dd>
            </dl>
        </li>
        <%
            if (power >= 1){//管理员
                out.println("<li>");
                out.println("<dl>");
                out.println("<dt>管理员</dt>");
                if (index == 3){
                    out.println("<dd><a href=\"" + request.getContextPath() + "/admin/musicList.jsp\" class=\"active\">音乐管理</a></dd>");
                }else {
                    out.println("<dd><a href=\"" + request.getContextPath() + "/admin/musicList.jsp\">音乐管理</a></dd>");
                }
                if (index == 4){
                    out.println("<dd><a href=\"" + request.getContextPath() + "/admin/userList.jsp\" class=\"active\">用户管理</a></dd>");
                }else {
                    out.println("<dd><a href=\"" + request.getContextPath() + "/admin/userList.jsp\">用户管理</a></dd>");
                }
                out.println("</dl>");
                out.println("</li>");
            }
        %>
        <li>
            <dl>
                <dt>设置</dt>
                <dd><a href="<%=request.getContextPath()%>/user/setting.jsp" <%=index == 5 ? "class=\"active\"" : ""%>>基础设置</a></dd>
            </dl>
        </li>
        <li>
            <p class="btm_infor">© 归舟 版权所有</p>
        </li>
    </ul>
</aside>