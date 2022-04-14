<%@ page contentType="text/html;charset=UTF-8"%>
<%
    Cookie[] cookies = request.getCookies();
    main.java.business.Cookie.removeCookieAll(cookies , response);
    response.sendRedirect("../index.jsp");//重定向
%>
