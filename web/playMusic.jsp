<%@ page import="java.util.Map" %>
<%@ page import="main.java.mysql.Query" %>
<%@ page import="main.java.mysql.Update" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    if (request.getParameter("id") == null){
        return;
    }
    String idStr = request.getParameter("id");//获取音乐id
    int id;
    try{
        id = Integer.parseInt(idStr);
    }catch (Exception e){
        return;
    }
    Map<String , Object> musicInfo = Query.getMusicInfo(id);
    if (musicInfo == null || musicInfo.size() <= 0) return;
    //执行热度加一
    Update.updMusicHeat(id);//音乐热度+1
    response.sendRedirect(request.getContextPath() + "/upload/music/" + musicInfo.get("pathString"));//重定向
%>
