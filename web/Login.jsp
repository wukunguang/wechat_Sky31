<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-7-15
  Time: 上午11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <title>用戶登錄</title>
    <link rel="stylesheet" href="/resource/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/resource/css/bootstrap.css">
    <script language="JavaScript" src="/resource/js/bootstrap.js"></script>
    <script language="JavaScript" src="/resource/js/jquery-2.1.1.js"></script>
</head>
<body>
    <div class="container-fluid">
        <p class="text-warning"><b>${message}</b></p>
        <div class="login-windows">
            <form action="/index" method="post">
                <div class="form-group" >
                    <label for="username">Your User Name</label>
                    <input  type="text" name="username" class="form-control" style="width: 70%" id="username">
                </div>

                <div class="form-group" >
                    <label for="password">Your Password</label>
                    <input  type="password" name="password" class="form-control" style="width: 70%" id="password">
                </div>

                <div class="login" style="width: 100px">
                    <label><input  class="btn" name="keepSeesion" type="submit" value="log in"></label>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
