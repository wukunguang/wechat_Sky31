<%--
  Created by IntelliJ IDEA.
  User: wukunguang
  Date: 15-8-28
  Time: 下午2:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${wallmain.title}</title>
    <script language="JavaScript" src="../../resource/js/jquery-2.1.1.js"></script>
    <link rel="stylesheet" href="../../resource/css/bootstrap.min.css">
    <script language="JavaScript" src="../../resource/js/bootstrap.js"></script>
    <link rel="stylesheet" href="../../resource/css/wallshow.css">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body{
            background-image: url("../../resource/images/wechatwall/bg.jpg");
        }
    </style>

    <script>

        var currentData;        //当前数据对象
        var currentDataSize = 0;//当前数据长度
        var currentPage = 1;    //初始化当前页数

        var cjData;

        $(window).load(function(){

            //数据初始化。
            loadData();
            //显示内容初始化。


            setInterval( timerSetter(),5000);


        })

        function loadData(){

            $.ajax({
                type: "GET",
                url: "/admin/wechatwall/listdata",
                async: false,
                data: {
                    'wid':$("#w_id").attr("class"),
                    'actionCode': 1,
                    'pages': 1
                },
                dataType: "json",
                error: function (data) {
                    alert("error");
                },
                success: function (data) {

                    currentDataSize = data.length;
                    currentData = data;
                    console.error(currentDataSize);

                    appendStr(data,currentPage,currentDataSize);
                  //  console.error(dataMaxSize);

                }
            });

            initTextSize();
        }

        //计时器
        function timerSetter(){

            console.error("timeSeter");
            rf = setInterval(refresh,35800);
            np = setInterval(nextPage,5000);
           // timer = setTimeout(timerSetter(),5000);


        }


        function appendStr(data,currentPage,currentDataSize){
            //列表装入。

            for(var i = (currentPage-1)*3; i<currentDataSize && i<(currentPage*3); i++){
                var str = ' ' ;
                str+='<li onclick="showDetail(this)"  class="lists" style="display: none">';
                str+='<div class="head-img rm"> <img class="rm" style="width: 100%;height: 100%" src="'+data[i].headimgurl+'" ></div>';
                str+='<div class="list-content rm">';
                str+='<span class="nick-name rm">';
                str+=data[i].nickname;
                str+='</span>';
                str+='<span class="text-content rm">';
                str+='<p class="texts">'+data[i].textcontent+'</p>';
                str+='</span></div></li>';
                $("#wallcontentUl").append(str);
                $(".lists").fadeIn(1000);

            }

            //自动使用空信息填充多余空间
            if(3*currentPage>currentDataSize){
                var x = 3*currentPage-currentDataSize;
                //console.error(currentDataSize);
                appendEmpty(x);
            }




        }

        function appendEmpty(i){
            for( i ; i>0; i--){
                var str = ' ' ;
                str+='<li onclick="showDetail(this)" class="lists">';
                str+='<div class="head-img rm"> <img class="rm" style="width: 100%;height: 100%" src="" ></div>';
                str+='<div class="list-content rm">';
                str+='<span class="nick-name rm">';
                str+='</span>';
                str+='<span class="text-content rm">';
                str+='<p></p>';
                str+='</span></div></li>';
                $("#wallcontentUl").append(str);


            }
        }

        function refresh(){
            //刷新

            console.error("refresh");
            currentPage = 1;
            rmLists();

            loadData();
        }

        //下一頁
        function nextPage(){


            if((currentPage)*3 > currentDataSize){
                currentPage = 0;
            }
                rmLists();
                currentPage++;
                console.error("page::"+currentPage);
                appendStr(currentData,currentPage,currentDataSize);
                initTextSize();

        }

        //上一頁
        function prePage(){

            if(currentPage>1){
                rmLists();
                currentPage--;
                appendStr(currentData,currentPage,currentDataSize);
            }
            initTextSize();

        }

        //首頁
        function firstPage(){

            currentPage = 1;
            rmLists();
            appendStr(currentData,currentPage,currentDataSize);

        }


        //末頁
        function endPage(){

            currentPage = (currentDataSize/3);
            rmLists();
            appendStr(currentData,currentPage,currentDataSize);

        }

        function rmLists(){
            //删除当前正在显示的列表页。
            $("#wallcontentUl .lists").remove();

        }

        function initTextSize(){

            var temp ;
                        for(var i=0;i<  $(".texts").length;i++)
                        {
                           temp = $(".texts").eq(i).html();
                            var obj = $(".texts").eq(i);
                            var size = temp.length;
                            console.error(size);
                            if(size<13){
                                $(obj).css("font-size","50px");
                                console.error(size);
                            }
                            else if(size>=13 && size<=41){
                                $(obj).css("font-size","30px");
                                console.error(size);
                            }
                            else if(size>41 && size<90){
                                $(this).css("font-size","20px");
                                //console.error(size);
                            }
                            else{
                                $(obj).css("font-size","20px");
                                $(obj).css("overflow","hidden");

                            }
                        }



         //   console.error(x);

        }

    </script>
    <script>
        //默认情况下不暂停刷新
        var isPauseRefresh = 0;
        // 给每个内容列表子项添加查看详细。
        function showDetail(obj){
            var str = $(obj).html();

            $("#content-detail").fadeIn(500);
            $("#content-detail").append(str);
        }

        function closeDetail(obj){
            $(obj).parents("#content-detail").children(".rm").remove();


            $(obj).parents("#content-detail").fadeOut(500);

        }

        function pauseNext(){

            if(isPauseRefresh==0){
                isPauseRefresh = 1;
                window.clearTimeout(np);
                window.clearTimeout(rf);
            }
            else{
                isPauseRefresh = 0;
                timerSetter();
            }

        }
    </script>

    <script>
        function showQrCodeDetail(){

            $("#qrcode-detail").css("display","block");

        }

        function closeQrCodeDetail(){

            $("#qrcode-detail").css("display","none");

        }
    </script>
    <script>
        var isPause = 1;    //当前状态未抽奖状态
        function choujiang(){
            $.ajax({
                type: "GET",
                url: "/admin/wechatwall/cj",
                data: {
                    'wid':$("#w_id").attr("class"),
                },
                dataType: "json",
                error: function (data) {
                    alert("error");
                },
                success: function (data) {

                    cjData = data;

                }
            });

            $("#cj-detail").css("display","block");


        }

        function closeCjDetail(){
            $("#cj-detail").css("display","none");

        }

        //抽奖
        function cj(){

            if(isPause==1) {
                isPause = 0;
                $("#cj-button").html("点我停止");
                cc = setInterval(scrollCj,10);

            }
            else{
                window.clearTimeout(cc);
                isPause = 1;
                $("#cj-button").html("点我抽奖");

            }

        }
        function scrollCj(){

            x = cjData.length;
            //取随机数
            var noscroll = Math.round(Math.random() * x + 0.4);
            $("#nickname").html(cjData[noscroll].nickname);
            $("#head-img").attr("src",cjData[noscroll].headimgurl);
             // cc = setTimeout(scrollCj, 10);
        }

    </script>
</head>
<body>
    <div class="container">

        <div style="position: fixed;z-index: 999;background-color: #222222;width: 1200px;height: 800px;opacity: 0.9; display: none" id="qrcode-detail">
            <img style="margin-left: 20%" width="700px" height="700px" src="/resource/images/wechatwall/qrcode_big.jpg">
            <span onclick="closeQrCodeDetail()" style="float: right;font-size: 80px;margin-right: 20px;color: white;cursor: pointer;">X</span>
        </div>

        <div style="position: fixed;z-index: 999;background-color: #222222;width: 1200px;height: 800px;opacity: 0.9; display: none" id="cj-detail">
            <span onclick="closeCjDetail()" style="float: right;font-size: 80px;margin-right: 20px;color: white;cursor: pointer;">X</span>

            <img style="margin-left: 40%;margin-top: 7%" id="head-img" width="250px" height="250px" src="/resource/images/wechatwall/bg-alt.png">
            <p  style="font-size: 70px;color: white;text-align: center;margin-top: 50px" id="nickname">???</p>
            <button style="clear: both;margin-left: 45%;margin-top: 0%" onclick="cj()" class="btn btn-primary btn-lg" id="cj-button">点我抽奖</button>
        </div>

        <div id="header">
            <div id="logo" class="left"></div>
            <div id="wallTitle"><p style="font-size: 27px">关注微信公众号"三翼校园/isky31"，编辑#${wall.title}#+你想说的话发送，即可上墙。</p></div>
            <div id="msgCount"></div>
            <div onclick="showQrCodeDetail()" id="code">
                <img class="img-responsive" width="106px" height="106px" src="/resource/images/wechatwall/qrcode_small.jpg"></div>
            <span id="w_id" class="${wall.w_id}"></span>
        </div>

        <div id="content">
            <div id="content-detail">
                <a onclick="closeDetail(this)" style="font-size: 50px;float: right;cursor: pointer">X</a>
            </div>
            <ul id="wallcontentUl">

            </ul>

            <ul id="button-list">
                <li onclick="refresh()"><a  class="btn btn-default">刷新</a></li>
                <li onclick="firstPage()"><a  class="btn btn-default">首页</a></li>
                <li onclick="prePage()"><a  class="btn btn-default">上一页</a></li>
                <li onclick="pauseNext()"><a  class="btn btn-default">暂停</a></li>
                <li  onclick="nextPage()"><a  class="btn btn-default">下一页</a></li>
                <li onclick="endPage()"><a  class="btn btn-default">末页</a></li>
                <li onclick="choujiang()"><a class="btn btn-default">抽奖</a></li>
            </ul>
        </div>
    </div>
</body>
</html>
