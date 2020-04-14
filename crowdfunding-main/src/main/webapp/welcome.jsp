<%--
  Created by IntelliJ IDEA.
  User: iamhere
  Date: 2020/4/6
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 欢迎页面，来到众筹系统 -->
    <!-- 服务器启动后，会直接跳转到welcome.jsp
            下面标签表明接下来要跳转到index这个映射，那么如何去找这个映射，需要去web.xml
            找到核心控制器DispatcherServlet，它会帮我们找到requestMapping，从而找到index映射。
            当前index映射在crowdfunding-main下的DispatcherController中
     -->
    <jsp:forward page="/index"></jsp:forward>
</body>
</html>
