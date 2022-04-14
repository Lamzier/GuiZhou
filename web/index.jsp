<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <jsp:include page="titile.jsp" />
    <link rel="shortcut icon" href="images/logo.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" /><!--引用外部css-->
    <style>
      body,td,th {
        font-family: Helvetica , calibri , "宋体" , Arial , sans-serif , "微软雅黑";
      }
    </style>
  </head>
  <body>
  <jsp:include page="head.jsp?index=1" />
  <jsp:include page="foundMusic.jsp" />
  <jsp:include page="bottom.jsp" />
  </body>
</html>
