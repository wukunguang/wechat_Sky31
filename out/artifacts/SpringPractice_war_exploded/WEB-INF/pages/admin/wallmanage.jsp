<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>${wallmain.title}</title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
    <script language="JavaScript" src="../../resource/js/bootstrap.js"></script>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        .hidden-input {
            opacity: 0;
            position: absolute;
            z-index: -1;
        }

        input[type=checkbox]+span {
            /* your style goes here */
            display: inline-block;
            height: 40px;
            width: 40px;
            border-radius: 8px;
            border: 2px solid #000000;
        }

        /* active style goes here */
        input[type=checkbox]:checked+span {
            background-color: #3fff41;
        }
    </style>

    <script>

        var pages = 1;

        var model = 2;

        var currentData;

        function initDataAllshow(){

            pages =1;

            $.ajax({
                        type:"GET",
                        url:"/admin/wechatwall/listdata",
                        data:{
                            'wid':$("#num").html(),
                            'actionCode':2,
                            'pages':pages
                        },
                        dataType: "json",
                        error:function(data){

                        },
                        success:function(data){

                            currentData = data;
                            for(var i = (pages-1)*10; i<(pages*10) && i<data.length;i++){

                                var str = null;
                                var checked = " ";
                                var state = data[i].isshow;

                                if(state == 1){
                                    checked = "checked";
                                }



                                str+='<tr class="lists">';
                                str+='<td style="text-align: center"><img style="margin-left: 25%" width="50%" height="50%" src="'+data[i].headimgurl+' alt="image-preview" id="image-preview" class="img-responsive">';
                                str+=       '<p style="font-size: 10px" id="text">'+data[i].nickname+'</p>';
                                str+=        '</td>';
                                str+=        '<td>'+data[i].textcontent+'</td>';
                                str+=        '<td><label><input style="display: none" onclick="setCheckbox(this)" type="checkbox" class="checkbox" id="select-'+data[i].c_id+'"'+checked+'><span class="your style about checkbox"></span></label></td>';
                                $("#wall-list").append(str);



                            }

                          //  obj.addClass("active");

                        }
                    }

            )
        }

        function showLists(actionCode,obj){
        //actionCode 0 为显示为通过消息。1为显示已通过消息。2为显示所有消息。

            model = actionCode;

            $(".active").removeClass("active");

            $(".lists").remove();
            $.ajax({
                        type:"GET",
                        url:"/admin/wechatwall/listdata",
                        data:{
                            'wid':$("#num").html(),
                            'actionCode':actionCode,
                            'pages':pages
                        },
                        dataType: "json",
                        error:function(data){

                        },
                        success:function(data){

                            for(var i = (pages-1)*10; i<(pages*10) && i<data.length;i++){

                                var str = null;
                                var checked = " ";
                                var state = data[i].isshow;

                                if(state == 1){
                                    checked = "checked";
                                }

                                str+='<tr class="lists">';
                                str+='<td style="text-align: center"><img style="margin-left: 25%" width="50%" height="50%" src="'+data[i].headimgurl+' " id="image-preview" class="img-responsive">';
                                str+=       '<p style="font-size: 10px" id="text">'+data[i].nickname+'</p>';
                                str+=        '</td>';
                                str+=        '<td>'+data[i].textcontent+'</td>';
                                str+=        '<td><label><input style="display: none" onclick="setCheckbox(this)" type="checkbox" class="checkbox" id="select-'+data[i].c_id+'"'+checked+'><span class="your style about checkbox"></span></label></td>';
                                $("#wall-list").append(str);
                            }

                            $(obj).addClass("active");

                        }
                    }

            )
        }
    </script>
    <script>


        $(window).load(function(){
            initDataAllshow();
            ss = setInterval(setNum,15000);

        })


        //选择checkbox时候触发的Ajax方法。传入参数: obj为当前对象,i 为状态，0为取消通过，1为通过。c_id为内容在数据库中的Id.
        //
        function checkboxAjax(obj,i,c_id){
            $.ajax({
                type:"GET",
                url:"/admin/wechatwall/changeShow",
                data:{
                    'cid':c_id,
                    'ishow':i
                },
                dataType: "json",
                success :function(date){
                    if(date[0].states=="true"){
                        obj.css("display","none");
                    }
                }
            })
        }

    </script>

    <script>
        //本方法是点击checkbox来决定是否通过。
        function setCheckbox(obj){
            //点击打钩时候，返回值是true，即表示通过。
            if($(obj).prop('checked')){
                var temp = $(obj).attr("id").split("-")[1];
                checkboxAjax(obj,1,temp)
            }
            //否则就返回false,取消通过的状态
            else{
                var temp = $(obj).attr("id").split("-")[1];
                checkboxAjax(obj,0,temp)
            }

        }

    </script>

    <script>

        var dataMaxSize = 0;
        //上一页执行。
        function prepage(){
            if(pages>1){
                var obj;
                switch (model){
                    case 2 :obj = $("#showAll");
                        break;
                    case 1: obj = $("#showPass");
                        break;
                    case 0: obj = $("#showNoPass");
                        break;
                }

                showLists(model,obj);
                pages--;    //当前页数减一
            }

        }

        //下一页执行:
        function nextpage(){
           var sss = getMaxSize();
            console.error(sss);
            if(pages*10< sss){
                var obj;
                switch (model){
                    case 2 :obj = $("#showAll");
                        break;
                    case 1: obj = $("#showPass");
                        break;
                    case 0:
                    {
                        obj = $("#showNoPass");
                        $("#newest-msg").html(0);
                    }
                        break;
                }

                showLists(model,obj);
                pages++;
            }

        }

        //返回当前Ajax数据最大长度。
        function getMaxSize() {

            $.ajax({
                type: "GET",
                url: "/admin/wechatwall/listdata",
                async: false,
                data: {
                    'wid': $("#num").html(),
                    'actionCode': 2,
                    'pages': pages
                },
                dataType: "json",
                success: function (data) {
                    dataMaxSize = data.length;
                    console.error(dataMaxSize);

                },
                error: function (data) {
                    alert("error");
                }

            });
            return dataMaxSize;

        }

        function setNum(){

            var ll = getMaxSize();


            var lastTotalMsg = $("#total").html();
            var lastMsg = $("#newest-msg").html();

            $("#newest-msg").html(parseInt(ll)-parseInt(lastTotalMsg));
            $("#total").html(ll);


        }
    </script>

</head>
<body>
<div class="navbar navbar-default  navbar-fixed-top" role="navigation">
    <div class="container">
        <div style="padding-top: 15px" class="navbar-header left-container">三翼微信管理平台V2</div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-collapse">
                <li role="presentation"><a href="/admin/index">首页</a> </li>
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
    <div class="row">
        <p>活动主题<b id="num">${md.w_id}</b>.${md.title}</p>

        <div class="col-md-10">

            <ul class="nav nav-pills" role="tablist">
                <li id="showAll" role="presentation" class="active" onclick="showLists(2,this)"><a href="">所有消息 <span id="total" class="badge">0</span></a></li>
                <li id="showNoPass" role="presentation" onclick="showLists(0,this)"><a href="#">待通过<span class="badge" id="newest-msg">0</span></a></li>
                <li id="showPass" role="presentation" onclick="showLists(1,this)"><a href="#">已通过<span class="badge">0</span></a></li>
            </ul>

            <span ><a onclick="prepage()" style="font-size: 25px;">上一页&nbsp; |</a></span>
            <span ><a onclick="nextpage()" style="font-size: 25px;"> &nbsp;下一页</a></span>

            <table style="word-break:break-all; " class=" table table-bordered table-hover" id="wall-list">
                <tr>
                    <th style="width: 100px">头像&amp;昵称</th>
                    <th>内容</th>
                    <th width="200px">状态</th>
                </tr>
                <tr>
                    <td style="text-align: center"><img style="margin-left: 25%" width="50%" height="50%" src="http://www.sky31.com/statics/images/sky31/logo.png" alt="image-preview" id="image-preview'+lenth+'" class="img-responsive"><br>
                        <p style="font-size: 10px" id="text">哈哈哈哈哈</p>
                    </td>
                    <td>12212121212121</td>
                    <td>卖萌专用</td>
                </tr>
            </table>

        </div>



    </div>
</div>
</body>
</html>