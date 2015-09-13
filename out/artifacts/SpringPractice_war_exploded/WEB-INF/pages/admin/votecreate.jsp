<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-7-19
  Time: 上午12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
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

<div class="navbar navbar-default  navbar-fixed-top" role="navigation">
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
</div>
<div style="margin-top: 5%" class="container">
    <div class="col-md-12">
        <form role="form" class="form-horizontal" action="/admin/submit" enctype="multipart/form-data" method="post">
            <div class="form-group">
                <label for="title" class="col-md-2">标题</label>
                <div class="col-md-6">
                    <input type="text" class="form-control" id="title" name="title" placeholder="请输入活动标题">
                </div>
            </div>
            <div class="form-group">
                <label for="file-05" class="col-md-2">上传标题图片</label>
                <div class="col-md-6">
                    <img width="50%" height="50%" src="#" id="image-preview05" class="img-rounded" alt="Responsive image">
                    <input type="file"  id="file-05" name="upload-file"  onchange="ss(this)" placeholder="上传文件">
                </div>
            </div>
            <br>
            <br>

            <div class="form-group">
                <label class="col-md-2" for="keyword">关键字</label>
                <div class="col-md-6">
                    <input class="form-control" type="text" id="keyword" name="keyword">
                </div>
            </div>
            <br>
            <br>

            <div class="row">
                <div class="col-md-12">
                    <div class="input-group input-group-md">
                        <span class="input-group-addon">开始时间</span>
                        <input class="form-control" placeholder="年" name="start-years">
                        <span class="input-group-addon">年</span>
                        <input class="form-control" placeholder="月" name="start-month">
                        <span class="input-group-addon">月</span>
                        <input class="form-control" placeholder="日" name="start-day">
                        <span class="input-group-addon">日</span>
                        <select name="start-hour" class="form-control ">
                            <%
                                for (int x = 0; x<=23 ;x++){
                                    out.println("<option value=\""+x+"\">"+x+"</option>");
                                }
                            %>
                        </select>
                        <span class="input-group-addon">时</span>
                        <select name="start-minute" class="form-control">
                            <%
                                for (int x = 0; x<=59 ;x++){
                                    out.println("<option value=\""+x+"\">"+x+"</option>");
                                }
                            %>
                        </select>
                        <span class="input-group-addon">分</span>
                    </div>
                </div>
            </div>

            <br>


            <div class="row">
                <div class="col-md-12">
                    <div class="input-group input-group-md">
                        <span class="input-group-addon">结束时间</span>
                        <input class="form-control" placeholder="年" name="end-years">
                        <span class="input-group-addon">年</span>
                        <input class="form-control" placeholder="月" name="end-month">
                        <span class="input-group-addon">月</span>
                        <input class="form-control" placeholder="日" name="end-day">
                        <span class="input-group-addon">日</span>
                        <select name="end-hour" class="form-control ">
                            <%
                                for (int x = 0; x<=23 ;x++){
                                    out.println("<option value=\""+x+"\">"+x+"</option>");
                                }
                            %>
                        </select>
                        <span class="input-group-addon">时</span>
                        <select name="end-minute" class="form-control">
                            <%
                                for (int x = 0; x<=59 ;x++){
                                    out.println("<option value=\""+x+"\">"+x+"</option>");
                                }
                            %>
                        </select>
                        <span class="input-group-addon">分</span>
                    </div>
                </div>
            </div>
            <br>
            <br>

            <div class="form-group">
                <label for="mvppd" class="col-md-3">每天最多能投几次</label>
                <div class="col-md-3">
                    <select id="mvppd" name="mvppd" class="form-control">
                        <%
                            for (int x=1 ; x<11; x++){
                                out.println("<option value=\""+x+"\" >"+x+"</option>");
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="hovpd" class="col-md-3">每隔几天就可以重新投票</label>
                <div class="col-md-3">
                    <select id="hovpd" name="hovpd" class="form-control">
                        <%
                            for (int x=0 ; x<11; x++){
                                out.println("<option value=\""+x+"\" >"+x+"</option>");
                            }
                        %>
                    </select>
                </div>
                <span id="ps-text" class="text-danger"><b></b></span>
            </div>

            <div class="form-group">
                <label for="mppv" class="col-md-3">每个账号投票上限</label>
                <div class="col-md-3">
                    <input value="0" type="text" id="mppv" name="mppv" class="form-control" placeholder="请输入数字">
                </div>

            </div>

            <div class="form-group">
                <label for="introduction" class="col-md-2">请输入活动介绍</label>
                <textarea class="form-control" name="introduction" placeholder="活动介绍" id="introduction"></textarea>
            </div>
            <hr>
            <hr>



            <div class="form-group">

                <label class="control-label col-sm-2">投票选项</label>
                <div class="col-md-10 col-lg-10">
                    <table id="vote-item-table">
                        <tr>
                            <th width="150px">选项标题</th>
                            <th width="100px">排序</th>
                            <th width="170px">上传图片</th>
                            <th width="200px">文字介绍</th>
                            <th width=""></th>
                        </tr>
                        <tr class="item0">
                            <td><input type="text" class="form-control"  placeholder="输入标题" name="voteBeanList[0].votetitle"></td>
                            <td><input type="text" class="form-control"  placeholder="排序" name="voteBeanList[0].votesort"></td>
                            <td>
                                <img  width="50%" height="50%" src="" alt="image-preview" id="image-preview0" class="img-responsive" >
                                <input type="file" id="file-0" class="tn-default"  name="voteBeanList[0].coverfile"  onchange="ss(this)">
                            </td>

                            <td>
                                <textarea style="width: 200px;height: 50px" name="voteBeanList[0].introduction"></textarea>
                            </td>

                            <td>
                                <input type="button" class="btn btn-info" id="item0"  value="删除" placeholder="刪除" onclick="removeitem(this)">
                                <input type="button" class="btn btn-info"  value="增加" id="add-item" onclick="additem()">

                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-2">
                    <input class="btn btn-primary" type="submit" value="确定,提交">
                </div>

            </div>

        </form>


    </div>
</div>
<script>
    //var obj = document.getElementById("mvppd");
    //弹出提示使用
    var temp = 1;


    $("#mvppd").change(function(){
        temp = $("#mvppd").val();
        var text = "PS:0为每个账号只能投"+temp+"票";
        var obj = document.getElementById("ps-text");
        obj.innerHTML = text ;

    })

</script>

<script>
    var lenth =1;



    $addstr = '<input type="button" class="btn btn-info " id="add-item" value="增加" name="vote-title" id="add-item" onclick="additem()">'

    function additem(){
        $str = '       <tr class="item'+lenth+'">'+
                '                <td><input type="text" class="form-control"  placeholder="输入标题" name="voteBeanList['+lenth+'].votetitle"></td>'+
                '                <td><input type="text" class="form-control"  placeholder="排序" name="voteBeanList['+lenth+'].votesort"></td>'+
                '                <td> '+
                '        <img width="50%" height="50%" src="" alt="image-preview" id="image-preview'+lenth+'" class="img-responsive">'+
                '        <input type="file" id="file-'+lenth+'" class="tn-default"  name="voteBeanList['+lenth+'].coverfile" onchange= "ss(this)"></td>'+
                '                <td>'+
                '<textarea style="width: 200px;height: 50px" name="voteBeanList['+lenth+'].introduction"></textarea>'+
                        '</td>'+

                '                <td>'+

                '                  <input type="button" class="btn btn-info" id="item'+lenth+'"  value="删除" placeholder="刪除" onclick="removeitem(this)">'+
                '                  <input type="button" class="btn btn-info " id="add-item" value="增加" id="add-item" onclick="additem()">'+
                '                </td>'+
                '              </tr>';
        removebtn();
        $("#vote-item-table").append($str);
        lenth++;
    }


    function removebtn(){
        $("#add-item").remove();
    }

    function addbtn(){
        console.error("增加按鈕");
        $("#vote-item-table tr td:last").append($addstr);
    }



    function removeitem(obj){

        var c = $("#vote-item-table tr").length;

        console.error(c);

        if(c<=2 ){
            alert("投票选项不能少于一条")
        }
        else{
            var v= '.'+$(obj).attr("id");
            console.error(v);
            $(v).remove();
            removebtn();
            addbtn();
        }



    }





</script>

<script>
    //图片上传预览


    var num = 0;
    ofr = new FileReader();
    rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;



    ofr.onload = function (oFREvent) {
        document.getElementById("image-preview"+num).src = oFREvent.target.result;
    };

    function ss(obj) {

        console.error("hehe2");

        num = $(obj).attr("id").split("-")[1];

        if (obj.files.length === 0) {
            return;
        }
        var oFile = obj.files[0];
        if (!rFilter.test(oFile.type)) {
            alert("You must select a valid image file!");
            return;
        }
        ofr.readAsDataURL(oFile);
    }


</script>



</body>
</html>
