<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta content="width=device-width, initial-scale=1, maximum-scale=1" name="viewport">
<title>趣拍</title>
<style  type="text/css">
*{ padding:0; margin:0}
a{ color:#999; text-decoration:none;}
body{margin:0;width:100%;overflow-x: hidden; line-height:20px; font-size:12px}
.left{margin:0 auto;background-color: #FEFCF8;}
.c{clear:both;}
li{ list-style:none}
ul{ padding:0; padding:0}

.cLeft{ border:1px solid #e2e2e2; background:#f7f7f7}
.head{ background:#191919; border-top:8px solid #e24141; padding:10px}

.r{ float:right}
.userInfo{ border-bottom:1px solid #e2e2e2; padding:10px}

.userHeadImg{ float:left;  width: 30%;-webkit-border-radius: 5px;border-radius: 5px;}
.userInfoinner{ float:left; width:60%; margin-left:10px}
.userInfoinner p{ color:#e24141; font-size:18px;}
.b{ font-weight:700}
.userInfoinner span{color:#797979; font-size:12px;}
.content{ padding:10px; font-family:"微软雅黑"; }
.GroupInfo{ padding:10px; color:#6a6a6a; font-size:12px}
.GroupInfo span{ color:#e24141; }
.comment{ margin-bottom:40px; color:#545454}
.comment h3{ border-top:1px solid #e2e2e2; border-bottom:1px solid #e2e2e2; font-size:16px; color:#6a6a6a; padding:8px 0 8px 10px; margin-bottom:10px}
.comment li{ clear:both; margin-bottom:10px; padding:8px 0 8px 12px; background:#fff; margin:8px 0; font-size:12px}
.comment li img{ float:left; margin-right:10px; width: 15%;-webkit-border-radius: 5px;border-radius: 5px;}
.comment li span{ color:#e24141}
#foot{ border-top:1px solid #ddd; padding:15px; text-align:center; color:#999}
#foot a{ margin:0 15px;}
/*jplayer自定义皮肤*/
 #playOut{border:1px solid #dcdcdc;-webkit-border-radius: 5px;border-radius: 5px;}
#play{ border:1px solid #fff; background:url(images/play_bg.jpg) repeat-x; height:55px; padding:4px 6px 4px 4px;-webkit-border-radius: 5px;border-radius: 5px; font-size:12px;}
#jp-play{ float:left; margin-right:15px;  width:18%; text-align:center; padding-left:15px}
#play_controls{ float:left; width:65%; padding-top:7px;}
#play_controls p{ margin-bottom:3px}
 .jp-title{ color:#666}
 
 
 
 
 

</style>


 
</head>

<body>
<div class="head">
<div  class="r"><a href="###" class="a1"><img src="${pageContext.request.contextPath }/images/iosdownload.png"  width="90"/></a></div>
<a href="/"><img src="${pageContext.request.contextPath }/images/waplogo.png" alt="" width="160"  class="logo" /></a></div>

<div class="cLeft">
<div class="userInfo">
 
<img src="${user.avatar}"  class="userHeadImg">
 <div class="userInfoinner">
<p>${user.nickName }</p>
<!--
<div style="margin-top:25px"><span>兴趣()</span>&nbsp;&nbsp;&nbsp;<span>关注()</span><span>&nbsp;&nbsp;&nbsp;粉丝()</span></div>
-->
</div>
<div class="c"></div>
</div>

<div>
<div  class="content">
<video width="100%" controls="controls" autoplay="autoplay" loop="loop">
  <source src="${content.videoUrlHD}" type="video/mp4">
Your browser does not support the video tag.
</video>
<p>${content.description}</p>
</div>
<!--
<div class="GroupInfo">来自: <span>《短趣》</span>兴趣组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;标签：<span>旅游</span></div>


<div class="comment">
<h3>评论<span style="font-size:14px">（${contentForm.commentNum }）</span></h3>
<ul>
<li><img src=""><span>王海华</span> <br>
阿苏多发索道费6
<div class="c"></div></li>
</ul>
</div>
-->
</div>
</div>
<div id="foot">
<a href="index.html">首页</a> | <a href="cooperate.html">合作伙伴</a> | <a href="contact.html">联系我们</a></div>
</body>
</html>