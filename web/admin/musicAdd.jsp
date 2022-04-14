<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="main.java.business.FinalAll" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.*" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="main.java.mysql.Update" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="../titile.jsp" />
    <link rel="stylesheet" href="../css/userinfo.css" type="text/css" /><!--引用外部css-->
    <link rel="stylesheet" href="../css/style.css" type="text/css" /><!--引用外部css-->
    <link rel="shortcut icon" href="../images/logo.ico" type="image/x-icon"/>
    <style>
        body,td,th {
            font-family: Helvetica , calibri , "宋体" , Arial , sans-serif , "微软雅黑";
        }
    </style>
</head>
<body>
<jsp:include page="../head.jsp?index=0"/>
<jsp:include page="../user/leftHead.jsp?index=3"/>
<%
    Map<String , Object> userinfo = (Map<String, Object>) request.getAttribute("userinfo");
    if (userinfo == null){
        out.println("<script> alert(\"请先登录！！\")");
        out.println("window.location = \"../login.jsp\"</script>");
        return;
    }
    int power = (int) userinfo.get("power");
    if (power < 1){//不是管理员
        out.println("<script> alert(\"您不是管理员！！\")");
        out.println("window.location = \"../login.jsp\"</script>");
        return;
    }
%>
<section class="rt_wrap content mCustomScrollbar" style="overflow-y: scroll;overflow-x: hidden;">
    <div class="rt_content" style="margin: 30px;">
        <h1>
            <strong style="color:grey;font-size: 32px;">音乐管理</strong>
            <a href="<%=request.getContextPath() + "/admin/musicAdd.jsp"%>" title="添加新的音乐" style="font-size: 20px;font-weight: bold;">添加音乐</a>
        </h1>
        <h1 style="padding: 10px;font-size: 20px;font-weight: bolder;">
           您正在添加音乐，请输入相应音乐信息。
        </h1>
        <form accept-charset="utf-8" action="musicAdd.jsp" enctype="multipart/form-data" method="post">
            <ul class="ulColumn2" style="font-size: 20px;font-weight: bold;">
                <li>
                    <span class="item_name" style="width:120px;">音乐名称：</span>
                    <input type="text" name="name" class="textbox textbox_295" placeholder="音乐名称信息"/>
                </li>
                <li>
                    <span class="item_name" style="width:120px;">音乐类型：</span>
                    <input type="text" name="type" class="textbox textbox_295" placeholder="多个类型用';'分割"/>
                </li>
                <li>
                    <span class="item_name" style="width:120px;">音乐歌手：</span>
                    <input type="text" name="singer" class="textbox textbox_295" placeholder="多个歌手用';'分割"/>
                </li>
                <li>
                    <span class="item_name" style="width:120px;">上传音乐：</span>
                    <label class="uploadImg">
                        <input type="file" name="music"/>
                        <span>上传</span>
                    </label>
                    <span class="tips">仅支持mp3，war格式！</span>
                </li>
                <li>
                    <input type="submit" class="link_btn" value="确认并上传"/>
                </li>
            </ul>
        </form>
    </div>
    <jsp:include page="../bottom.jsp" />
</section>
</body>
<%//处理请求信息
    finish: {

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);//解析器
        servletFileUpload.setHeaderEncoding("utf-8");//解决上传图片如果为中文就是乱码问题
        List<FileItem> items;
        try {
            items = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e) {//没有数据
            break finish;
        }
        String name = null;
        String typeList = null;
        String singerList = null;
        FileItem fileItem = null;
        for (FileItem item : items){
            if(item.isFormField()) {//表单数据
                String key = item.getFieldName();
                String value = new String(item.getString().getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8);//编码转换
                if (key.equalsIgnoreCase("name")) {
                    name = value;
                } else if (key.equalsIgnoreCase("type")) {
                    typeList = value;
                } else if (key.equalsIgnoreCase("singer")) {
                    singerList = value;
                }
            }else {//非表单数据
                fileItem = item;
            }
        }
        //数据校验
        if (name == null && typeList == null && singerList == null && fileItem == null){
            break finish;
        }
        if (name == null || typeList == null || singerList == null || fileItem == null){
            out.println("<script> alert(\"参数缺失！！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        if(name.length() <= 0 || name.length() > 64){
            out.println("<script> alert(\"歌曲名字长度异常！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        if(typeList.length() <= 0 || typeList.length() > 512){
            out.println("<script> alert(\"类型长度异常！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        if(singerList.length() <= 0 || singerList.length() > 512){
            out.println("<script> alert(\"歌手长度异常！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        String[] fileName = fileItem.getName().split("\\." , 2);//上传文件名字
        if(fileName.length != 2 || !FinalAll.MUSIC_SUFFIX.contains(fileName[1].toLowerCase())){
            //后缀不正常
            out.println("<script> alert(\"文件名字异常！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        if(fileItem.getSize() > 104857600){//文件大小 大于100mb
            out.println("<script> alert(\"文件大小不能大于100mb！！\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        String save_name = new Date().getTime() + "." + fileName[1];//定义文件名字
        String loadPath = FinalAll.LOCAL_SAVE_PATH + "upload/music";
        File file = new File(loadPath , save_name);
        if(file.exists()){//文件存在
            if(!file.delete()){
                out.println("<script> alert(\"内部异常！！\")");
                out.println("window.location = \"musicAdd.jsp\"</script>");
                break finish;
            }
        }
        try {
            fileItem.write(file);
        } catch (Exception e) {
//            e.printStackTrace();
            out.println("<script> alert(\"内部异常！！2\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        //成功保存文件，开始写入数据库
        List<String> types = Arrays.asList(typeList.split(";"));
        List<String> singers = Arrays.asList(singerList.split(";"));
        if (Update.addMusic(name , save_name , types , singers) < 0){
            out.println("<script> alert(\"内部异常！！3\")");
            out.println("window.location = \"musicAdd.jsp\"</script>");
            break finish;
        }
        out.println("<script> alert(\"添加成功！\")");
        out.println("window.location = \"musicList.jsp\"</script>");
        break finish;
    }
%>
</html>
