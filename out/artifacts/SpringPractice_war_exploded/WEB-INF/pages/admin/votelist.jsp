<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-8-1
  Time: 下午5:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
  <title>三翼微信管理平台V2</title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../resource/css/bootstrap-theme.css">
    <script language="JavaScript" src="../../resource/js/bootstrap.js"></script>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <style>
    td{
      padding: 15px;
      line-height: 18px;
      font-size: 15px;
    }

    th{
      text-indent: 1em;
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

<div class="container">
    <h1> 555</h1>
    <div class="page-header">为您找到${mainVoteBeanList.size()}条数据</div>
    <div class="row">
        <div class="col-md-10">
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>关键字</th>
                        <th>标题</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>状态</th>
                        <th>投票结果</th>
                        <th>操作</th>
                    </tr>

                    </thead>
                    <tbody>
                    ${mainVoteBeanList.size()}

                    <c:forEach var="mainVote" items="${mainVoteBeanList}" varStatus="status">

                        <tr>
                            <td>${mainVote.keyWord}</td>
                            <td>${mainVote.title}</td>
                            <td>${mainVote.starttime}</td>
                            <td>${mainVote.endTime}</td>
                            <td>${mainVote.isFinish()}</td>
                            <td><a href="/admin/voteresult?mid=${mainVote.m_id}">查看结果</a></td>
                            <td><a href="/admin/votedetail?mid=${mainVote.m_id}">详情</a>|<a id="delete${status}" href="/admin/voteDelete?mid=${mainVote.m_id}" onclick="return firm()">刪除</a>|</td>
                        </tr>

                    </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<script>

    function firm(){
        //删除确认。
        if(confirm("你确定不是手滑了吗？╮(╯_╰)╭")){
            return true;
        }

        else{
            return false;
        }
    }


</script>

</body>
</html>
