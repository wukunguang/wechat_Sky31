<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-8-10
  Time: 下午9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html>
<head>
    <title>${votelist.title}</title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
    <script language="JavaScript" src="../../resource/js/bootstrap.js"></script>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>


    <header>
        <div  class="container">
            <div class="col-md-10">
                <h2>${votelist.title}</h2>
                <p>投票时间：${votelist.starttime}-${votelist.endTime}</p>
                <img src="${votelist.coverURL}" class="img-responsive" alt="Responsive image">
                <p>每个账号总投票上限为${votelist.mppv}票,每次能连续投${votelist.mvppd}票, 每${votelist.hovpd}天会刷新一次。</p>
                <p class="text-danger">当前状态:${votelist.isFinish()}</p>
                <p class="bg-info">${votelist.introduction}</p>
            </div>
        </div>
    </header>

    <div class="row">
        <c:forEach var="vote" items="${votelist.voteBeanList}" varStatus="statu">
            <div class="col-md-4" style="box-shadow: 5px 5px 2px #888888;background-color: #d4d4d4; margin-top: 20px;padding: 20px">

                <img class="img-responsive" src="${vote.votecover}" width="300" height="150" >
                <h3>${vote.votetitle}</h3>

                <a  onclick="showDetail(this)" class="btn btn-info">点我查看完整介绍</a>

                <p style="display: none">${vote.introduction}</p>

                <p>当前票数： ${vote.voted}</p>
                <a href="/votelist/vote?vid=${vote.v_id}" class="btn btn-info"> 为Ta投一票</a>
            </div>

        </c:forEach>
    </div>
    <footer>
        <h3 class="text-info">© 2015 湘潭大学三翼工作室</h3>
    </footer>
<script>
    function showDetail(obj){

        $(obj).next().slideToggle(function(){
            if($(obj).next().is(':hidden')){
                obj.innerHTML = "点我查看完整介绍";

            }

            else{

                obj.innerHTML = "点我收起介绍";
            }
        });
    }

</script>
</body>



</html>
