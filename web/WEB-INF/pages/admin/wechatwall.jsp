<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-8-27
  Time: 下午2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title></title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../resource/css/bootstrap-theme.css">
    <script language="JavaScript" src="../../resource/js/bootstrap.js"></script>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        th{
            width: 150px;
            text-align: center;
        }

    </style>
</head>
<body>
<nav class="navbar navbar-default  navbar-fixed-top" role="navigation">
    <div class="container">
        <div style="padding-top: 15px" class="navbar-header left-container">三翼微信管理平台V2</div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-collapse">
                <li role="presentation"><a href="#">首页</a> </li>
                <li class="dropdown" role="presentation">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">投票</a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/admin/votecreate">发起投票</a>
                        </li>
                        <li>
                            <a href="/admin/votelist">查看投票</a>
                        </li>
                    </ul>
                </li>
                <li><a href="/admin/wechatWall">微信墙</a> </li>
                <li><a href="#">首页</a> </li>
            </ul>
            <ul style="padding-top: 15px" class="nav navbar-nav  navbar-right">
                <li>
                    <strong>欢迎${bean.user}</strong>
                </li>
            </ul>
        </div>

    </div>
</nav>

<div style="margin-top: 10%" class="container">

    <div class="col-md-8">
        <form action="/admin/wechatWall/create" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="title">活动话题</label>
                <input class="form-control" type="text" placeholder="请输入活动主题" id="title" name="title">
            </div>
            <input type="submit" value="提交">
        </form>
    </div>
    <div class="col-md-10">

            <label for="wall-list">活动列表</label>
            <table style="word-break:break-all; " class=" table table-bordered table-hover" id="wall-list">
                <tr>
                    <th>序号</th>
                    <th>话题</th>
                    <th>操作</th>
                </tr>

                <c:forEach var="wallmain" items="${WallMainList}">
                    <tr>
                        <td style="text-align: center">${wallmain.w_id}</td>
                        <td>${wallmain.title}</td>
                        <td><a target="_blank" href="/admin/wechatWall/show?wid=${wallmain.w_id}">展示界面</a>|<a target="_blank" href="/admin/wechat/wallmanageshow?wid=${wallmain.w_id}">管理界面</a> </td>
                    </tr>
                </c:forEach>

            </table>

    </div>
</div>
</body>
</html>
