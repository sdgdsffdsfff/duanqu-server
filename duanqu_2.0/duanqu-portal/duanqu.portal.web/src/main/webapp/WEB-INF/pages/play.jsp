<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
  <meta charset="utf-8">
  <title>趣拍-手机微视频神器，
	<c:if test="${empty content.description}">
	${user.nickName } 拍了一段有意思的视频，大家快来看吧。
	</c:if>${content.description}</title>
  <meta name="viewport" content="width=device-width; initial-scale=1.0">
  <link href="http://www.qupai.me/css/qupai.css" rel="stylesheet" type="text/css" >
  <link href="http://www.qupai.me/css/video-js.css" rel="stylesheet" type="text/css">
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script>
  $(function(){
  $("#video-js-play").css({height:$("#qupai_video").width()+"px"})
  })
  </script>
  <script src="http://www.qupai.me/js/video.js"></script>
  <script>
    videojs.options.flash.swf = "http://www.qupai.me/images/video-js.swf";
  </script>

  <!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
  </head>

  <body>
  <div id="Wrap">
    <div id="Main">
      <div style=" background:#3a3838">
        <div class="content clearfix" id="top"><span class="downBtn"><a href="https://itunes.apple.com/cn/app/qu-pai-shou-ji-wei-shi-pin/id672243559?mt=8">iPhone版下载</a></span>
          <div  id="Logo" class="clearfix"><a href="/index.html"></a>
            <p><b>趣拍</b>手机微视频神器</p>
          </div>
        </div>
      </div>
      <div class="featherbg featherbgPlay"  >
        <div class="content clearfix">
          <div id="play" class="clearfix">
            <div class="playInner">
              <div class="author"></div>
              <div class="download">
                <p><img src="http://www.qupai.me/images/p1.gif" width="330" height="165"></p>
                <p><img src="http://www.qupai.me/images/weixin.jpg" width="270" height="270"></p>
                <p><img src="http://www.qupai.me/images/p2.gif" width="330" height="46"></p>
                <p><a href="https://itunes.apple.com/cn/app/qu-pai-shou-ji-wei-shi-pin/id672243559?mt=8" target="_blank"><img src="http://www.qupai.me/images/p3.gif" alt="" width="315" height="97"></a></p>
              </div>
              <div id="player">
                <div class="userInfo clearfix">
                  <div><img src="${user.avatar}" ><b>${user.nickName }</b><br>
                    ${signature }</div>
                </div>
                <div id="video-js-play" class="clearfix">
                <c:if test="${empty content.gifUrl}">
                  <video id="qupai_video" class="video-js vjs-default-skin" controls   loop="loop"   preload="auto" width="100%" height="100%"    poster="${content.thumbnailsUrl}"     data-setup="{}">
                    <source src="${content.videoUrlHD}" type='video/mp4' />
                  </video>
                  </c:if>
                  <c:if test="${not empty content.gifUrl}">
                  	<img src="${content.gifUrl}" width="100%"/>
                  </c:if>
                </div>
                <b>${content.description}</b> </div>
            </div>
          </div>
        </div>
      </div>
      <div id="Foot">
        <p>Copyright 2013 qupai.me, All Rights Reserved. 趣拍 版权所有 / 浙B2-20080205 <a href="contact.html">联系我们</a></p>
      </div>
    </div>
  </div>
</body>
</html>
