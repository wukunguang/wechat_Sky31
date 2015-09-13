<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-8-3
  Time: 下午2:32
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

<div class="container">
    <h1> 555</h1>
    <div class="page-header">标题：${mainVote.title},共有${voteList.size()}条数据</div>
    <div class="row">
        <div class="col-md-10">
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>票数</th>

                    </tr>

                    </thead>
                    <tbody>
                    ${mainVoteBeanList.size()}

                    <c:forEach var="vote" items="${voteList}" varStatus="status">
                        <tr>

                            <td>${status}</td>
                            <td>${vote.votetitle}</td>
                            <td>${vote.voted}</td>

                        </tr>
                    </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</body>
</html>
